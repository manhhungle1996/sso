package vn.com.atomi.openbanking.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vn.com.atomi.openbanking.authservice.common.dto.Link;
import vn.com.atomi.openbanking.authservice.common.dto.Meta;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessConsent {
    @JsonProperty(value = "Data")
    AccessConsentDetail data;
    @JsonProperty(value = "Risks")
    Risk risks;
    @JsonProperty(value = "Links", access = JsonProperty.Access.READ_ONLY)
    Link links;
    @JsonProperty(value = "Meta", access = JsonProperty.Access.READ_ONLY)
    Meta meta;
}
