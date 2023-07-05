package vn.com.atomi.openbanking.authservice.services.impl;

import org.apache.tomcat.util.buf.StringUtils;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.atomi.openbanking.authen.KeycloakService;
import vn.com.atomi.openbanking.authen.dto.CibaAuthResponse;
import vn.com.atomi.openbanking.authservice.common.annotations.UseLogging;
import vn.com.atomi.openbanking.authservice.common.dto.Link;
import vn.com.atomi.openbanking.authservice.common.dto.Meta;
import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;
import vn.com.atomi.openbanking.authservice.dto.AccessConsent;
import vn.com.atomi.openbanking.authservice.dto.AccessConsentDetail;
import vn.com.atomi.openbanking.authservice.entities.Consent;
import vn.com.atomi.openbanking.authservice.repositories.ConsentRepository;
import vn.com.atomi.openbanking.authservice.services.ConsentService;
import vn.com.atomi.openbanking.authservice.utils.BusinessEnums;
import vn.com.atomi.openbanking.authservice.utils.Constants;
import vn.com.atomi.openbanking.authservice.utils.TimeStampUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ConsentServiceImpl implements ConsentService {
    @Autowired
    ConsentRepository consentRepository;
    @Autowired
    KeycloakService keycloakService;
    @Override
    @UseLogging
    @Transactional
    public AccessConsent create(AccessConsent accessConsent, String xFapiInteractionId) {
        UserRepresentation user = keycloakService.getUserByUsername(xFapiInteractionId);
        if(user == null){
            user = new UserRepresentation();
            user.setEmail(xFapiInteractionId + "@atomi.com.vn");
            user.setUsername(xFapiInteractionId);
            user.setEnabled(true);
            CredentialRepresentation password = new CredentialRepresentation();
            password.setType(CredentialRepresentation.PASSWORD);
            password.setValue("123456aA@");
            user.setCredentials(Arrays.asList(password));
            keycloakService.createNewUser(user);
        }
        String permissions = StringUtils.join(accessConsent.getData().getPermissions(), ' ');
        String consentId = UUID.randomUUID().toString();
        CibaAuthResponse cibaAuthResponse = keycloakService.makeCibaAuthRequest(xFapiInteractionId, permissions, consentId);
        Consent consent = new Consent();
        consent.setConsentId(consentId);
        consent.setData(permissions);
        consent.setStatus(Constants.ConsentStatus.AWAITING_AUTHORISATION);
        consent.setCustomerId(xFapiInteractionId);
        consent.setAuthReqId(cibaAuthResponse.getAuthReqId());
        consent.setExpirationDateTime(TimeStampUtils.stringToTimestamp(accessConsent.getData().getExpirationDateTime()));
        consent.setTransactionFromDateTime(TimeStampUtils.stringToTimestamp(accessConsent.getData().getTransactionFromDateTime()));
        consent.setTransactionToDateTime(TimeStampUtils.stringToTimestamp(accessConsent.getData().getTransactionToDateTime()));

        consent = consentRepository.save(consent);
        AccessConsentDetail data = accessConsent.getData();
        data.setConsentId(consentId);
        data.setStatus(Constants.ConsentStatus.AWAITING_AUTHORISATION);
        data.setCreationDateTime(consent.getCreatedAt().toString());
        accessConsent.setData(data);
        return accessConsent;
    }

    @Override
    @Transactional(readOnly = true)
    public AccessConsent findByConsentId(String consentId) throws BusinessException {
        Consent consent = consentRepository.finByConsentId(consentId).orElse(null);
        if(consent == null || Constants.ConsentStatus.REMOVED.equals(consent.getStatus())){
            throw new BusinessException(BusinessEnums.CONSENT_NOT_FOUND);
        }
        AccessConsentDetail data = new AccessConsentDetail();
        AccessConsent rs = new AccessConsent();
        data.setConsentId(consent.getConsentId());
        data.setStatusUpdateDateTime(consent.getUpdatedAt().toString());
        data.setPermissions(List.of(consent.getData().split(" ")));
        data.setExpirationDateTime(consent.getExpirationDateTime().toString());
        data.setTransactionFromDateTime(consent.getTransactionFromDateTime().toString());
        data.setTransactionToDateTime(consent.getTransactionToDateTime().toString());
        data.setCreationDateTime(consent.getCreatedAt().toString());
        data.setStatus(consent.getStatus());
        rs.setData(data);
        rs.setMeta(new Meta());
        rs.setLinks(new Link(consentId));
        if (Constants.ConsentStatus.REJECTED.equals(consent.getStatus())
        || Constants.ConsentStatus.REVOKED.equals(consent.getStatus())){
            return rs;
        }
        // Lay thong tin cua keycloak
        try{
            AccessTokenResponse obj = keycloakService.getTokenFromCibaRequest(consent.getAuthReqId());
            System.out.println(obj);
        }catch (Exception exception){

        }
        return rs;
    }

    @Override
    public int updateStatus(String status, String consentId) {
        return consentRepository.updateStatus(status, consentId);
    }
}
