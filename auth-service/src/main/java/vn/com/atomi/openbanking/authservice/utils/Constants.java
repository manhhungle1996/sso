package vn.com.atomi.openbanking.authservice.utils;

import java.util.HashMap;

public class Constants {
    public static final String IS_NOT_NULL = "is not null";
    public static final String REQUEST_MAPPING_PREFIX = "/api";
    public static final String TIMEZONE = "Asia/Ho_Chi_Minh";
    public static final String HTTP_DATE_FORMAT = "dd MMM yyyy HH:mm:ss z";
    public static final String BOOKED_TIME_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String AVAILABLE_DATE_FORMAT = "yyy-MM-dd'T'HH:mm:ssXXX";
    public static final String STATEMENT_TIME_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static class ParametersFieldName {
        public static final String FROM_BOOKING_DATE_TIME = "fromBookingDateTime";
        public static final String TO_BOOKING_DATE_TIME = "toBookingDateTime";
        public static final String FROM_STATEMENT_DATE_TIME = "fromStatementDateTime";
        public static final String TO_STATEMENT_DATE_TIME = "toStatementDateTime";
    }
    public static class ConsentStatus{
        public static final String AWAITING_AUTHORISATION = "AwaitingAuthorisation";
        public static final String REJECTED = "Rejected";
        public static final String AUTHORISED = "Authorised";
        public static final String REVOKED = "Revoked";
        public static final String REMOVED = "Removed";

    }
    public static final HashMap<String, String> CIBA_CONSENT_MAPPING = new HashMap<String, String>(){{
        put("SUCCEED", "AUTHORISED");
        put("UNAUTHORIZED", "AWAITING_AUTHORISATION");
        put("CANCELLED", "REVOKED");
    }};
}