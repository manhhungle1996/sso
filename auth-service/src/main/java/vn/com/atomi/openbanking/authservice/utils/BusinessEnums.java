package vn.com.atomi.openbanking.authservice.utils;

import org.apache.http.HttpStatus;
import vn.com.atomi.openbanking.authservice.config.I18n;

public enum BusinessEnums {
    CONSENT_NOT_FOUND(HttpStatus.SC_BAD_REQUEST, "consent.not.found"),
    CIBA_CALLBACK_NOT_FOUND(HttpStatus.SC_BAD_REQUEST, "consent.not.found"),
    INTERNAL_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR, "msg.internal.server")

    ;

    private int code;
    private String message;

    BusinessEnums(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return I18n.getMessage(message);
    }
}
