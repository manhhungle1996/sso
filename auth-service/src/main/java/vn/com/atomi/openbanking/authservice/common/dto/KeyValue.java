package vn.com.atomi.openbanking.authservice.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeyValue {
    @JsonProperty(value = "Key")
    String key;
    @JsonProperty(value = "Value")
    String value;
}
