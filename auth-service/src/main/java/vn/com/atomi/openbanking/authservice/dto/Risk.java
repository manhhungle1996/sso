package vn.com.atomi.openbanking.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.com.atomi.openbanking.authservice.common.dto.KeyValue;

import java.util.List;

public class Risk {
    @JsonProperty(value = "Data")
    List<KeyValue> data;
}
