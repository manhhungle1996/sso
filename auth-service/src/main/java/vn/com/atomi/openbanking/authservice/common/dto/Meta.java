package vn.com.atomi.openbanking.authservice.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meta {
    @JsonProperty(value = "TotalPages", access = JsonProperty.Access.READ_ONLY)
    Long totalPages = 1L;
}
