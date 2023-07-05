package vn.com.atomi.openbanking.authservice.dto.extauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExtAuthDTO {
    @JsonProperty(value = "scope")
    String scope;
    @JsonProperty(value = "binding_message")
    String bindingMessage;
    @JsonProperty(value = "login_hint")
    String loginHint;
    @JsonProperty(value = "is_consent_required")
    String isConsentRequired;
}
