package vn.com.atomi.openbanking.authservice.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtils {
    public static Timestamp stringToTimestamp(String data, String dateFormatString){
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new java.sql.Timestamp(parsedDate.getTime());
    }
    public static Timestamp stringToTimestamp(String data){
        return stringToTimestamp(data, Constants.AVAILABLE_DATE_FORMAT);
    }
}
