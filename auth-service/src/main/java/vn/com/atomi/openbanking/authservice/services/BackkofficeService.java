package vn.com.atomi.openbanking.authservice.services;

import org.keycloak.representations.AccessTokenResponse;
import vn.com.atomi.openbanking.authen.dto.CibaCallBackRequest;
import vn.com.atomi.openbanking.authservice.common.dto.BaseResponse;
import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;

public interface BackkofficeService {
    AccessTokenResponse getTokenByConsentId(String consentId) throws BusinessException;
    BaseResponse updateStatusConsent(String consentId, CibaCallBackRequest cibaCallBackRequest) throws BusinessException;
}
