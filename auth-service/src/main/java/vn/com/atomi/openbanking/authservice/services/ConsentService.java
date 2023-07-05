package vn.com.atomi.openbanking.authservice.services;

import vn.com.atomi.openbanking.authservice.common.exception.BusinessException;
import vn.com.atomi.openbanking.authservice.dto.AccessConsent;

public interface ConsentService {
    AccessConsent create(AccessConsent accessConsent, String xFapiInteractionId);
    AccessConsent findByConsentId(String consentId) throws BusinessException;
    int updateStatus(String status, String consentId);
}
