package vn.com.atomi.openbanking.authservice.services.impl;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.atomi.openbanking.authen.KeycloakService;
import vn.com.atomi.openbanking.authen.dto.CibaCallBackRequest;
import vn.com.atomi.openbanking.authservice.common.dto.BaseResponse;
import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;
import vn.com.atomi.openbanking.authservice.entities.Consent;
import vn.com.atomi.openbanking.authservice.entities.PrivateToken;
import vn.com.atomi.openbanking.authservice.repositories.ConsentRepository;
import vn.com.atomi.openbanking.authservice.repositories.PrivateTokenRepository;
import vn.com.atomi.openbanking.authservice.services.BackkofficeService;
import vn.com.atomi.openbanking.authservice.utils.BusinessEnums;

import static vn.com.atomi.openbanking.authservice.utils.Constants.CIBA_CONSENT_MAPPING;

@Service
public class BackofficeImpl implements BackkofficeService {
    @Autowired
    ConsentRepository consentRepository;
    @Autowired
    PrivateTokenRepository privateTokenRepository;
    @Autowired
    KeycloakService keycloakService;

    @Override
    public AccessTokenResponse getTokenByConsentId(String consentId) throws BusinessException {
        Consent consent = consentRepository.finByConsentId(consentId).orElse(null);
        if (consent == null) throw new BusinessException(BusinessEnums.CONSENT_NOT_FOUND);
        return keycloakService.getTokenFromCibaRequest(consent.getAuthReqId());
    }

    @Override
    @Transactional
    public BaseResponse updateStatusConsent(String consentId, CibaCallBackRequest cibaCallBackRequest) throws BusinessException {
        BaseResponse baseResponse = new BaseResponse<>();
        String status = CIBA_CONSENT_MAPPING.get(cibaCallBackRequest.getStatus());
        if (status == null) throw new BusinessException(BusinessEnums.CIBA_CALLBACK_NOT_FOUND);
        consentRepository.updateStatus(status, consentId);
        PrivateToken privateToken = privateTokenRepository.finByConsentId(consentId).orElse(null);
        if (privateToken == null) throw new BusinessException(BusinessEnums.CONSENT_NOT_FOUND);
        if(!keycloakService.updateCibaAuthRequest(privateToken.getAuthorization(), cibaCallBackRequest)){
             throw new BusinessException(BusinessEnums.INTERNAL_ERROR);
        };
        return baseResponse;
    }
}
