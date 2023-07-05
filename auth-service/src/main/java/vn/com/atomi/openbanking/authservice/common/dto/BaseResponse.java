package vn.com.atomi.openbanking.authservice.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String timestamp;

    private final String clientMessageId;

    private final String transactionId;

    private int code;

    private String message;

    private T data;

    public BaseResponse() {
        this.code = 200;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.message = "Successful!";
        this.clientMessageId = null;
        this.transactionId = null;
    }

    public BaseResponse(String clientMessageId, String transactionId) {
        this.code = 200;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.message = "Successful!";
        this.clientMessageId = clientMessageId;
        this.transactionId = transactionId;
    }

    public BaseResponse<T> success(T data) {
        this.data = data;
        return this;
    }

    public BaseResponse<T> error(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public BaseResponse<T> error(int code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    public void setData(T data) {
        this.data = data;
    }

}