package vn.com.atomi.openbanking.authservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vn.com.atomi.openbanking.authservice.utils.Constants;

import java.util.List;

@Data
public class AccessConsentDetail {
    @JsonProperty(value = "ConsentId", access = JsonProperty.Access.READ_ONLY)
    String consentId;
    @JsonProperty(value = "Status", access = JsonProperty.Access.READ_ONLY)
    String status;
    @JsonProperty(value = "StatusUpdateDateTime", access = JsonProperty.Access.READ_ONLY)
    String statusUpdateDateTime;
    @JsonProperty(value = "CreationDateTime", access = JsonProperty.Access.READ_ONLY)
    String creationDateTime;
    @JsonProperty(value = "Permissions")
    List<String> permissions;
    @JsonProperty(value = "ExpirationDateTime")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.AVAILABLE_DATE_FORMAT, timezone= Constants.TIMEZONE)
    String expirationDateTime;
    @JsonProperty(value = "TransactionFromDateTime")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.AVAILABLE_DATE_FORMAT, timezone= Constants.TIMEZONE)
    String transactionFromDateTime;
    @JsonProperty(value = "TransactionToDateTime")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=Constants.AVAILABLE_DATE_FORMAT, timezone= Constants.TIMEZONE)
    String transactionToDateTime;
}
