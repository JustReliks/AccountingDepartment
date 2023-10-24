package ru.cource.accounting.utils;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateFormatUtils {

    public static String formatTimeStamp(Timestamp timestamp, String format) {
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Timestamp formatToTimeStamp(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return new Timestamp(formatter.parse(date).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
