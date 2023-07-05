package vn.com.atomi.openbanking.authservice.utils;

import vn.com.atomi.openbanking.authservice.common.exception.IncorrectParameterException;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ValidatorUtils {
    public static void validateNull(Object val, String errorMessage) throws IncorrectParameterException {
        if (val == null) {
            throw new IncorrectParameterException(errorMessage);
        }
    }
    public static void validateString(String val, String errorMessage) throws IncorrectParameterException {
        if (val == null || val.trim().isEmpty()) {
            throw new IncorrectParameterException(errorMessage);
        }
    }
    public static void validateIPAddress(String val, String errorMessage) throws IncorrectParameterException {
        validateString(val, errorMessage);
        try {
            Object res = InetAddress.getByName(val);
             if(res instanceof Inet4Address || res instanceof Inet6Address){
                 return;
             };
            throw new IncorrectParameterException(errorMessage);
        } catch (final UnknownHostException exception) {
            throw new IncorrectParameterException(errorMessage);
        }
    }

}
