package vn.com.atomi.openbanking.authservice.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Link {
    @JsonProperty(value = "Self", access = JsonProperty.Access.READ_ONLY)
    String self;

    public Link(String self) {
        this.self = self;
    }
}
