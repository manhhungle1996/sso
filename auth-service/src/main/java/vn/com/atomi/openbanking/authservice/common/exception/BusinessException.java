package vn.com.atomi.openbanking.authservice.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.com.atomi.openbanking.authservice.utils.BusinessEnums;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessException extends Exception{
    static final long serialVersionUID = -7034897190745766939L;
    private int code;
    private String clientMessageId;
    private String transactionId;
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(BusinessEnums enums) {
        super(enums.getMessage());
        this.code = enums.getCode();
    }

    public BusinessException(int code, String message, String clientMessageId, String transactionId) {
        super(message);
        this.code = code;
        this.clientMessageId = clientMessageId;
        this.transactionId = transactionId;
    }
    public int getCode() {
        return code;
    }

    public String getClientMessageId() {
        return clientMessageId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
