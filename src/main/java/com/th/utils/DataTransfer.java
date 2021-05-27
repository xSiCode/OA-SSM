package com.th.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @Enum : ConstantValue
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/13 17:23
 * @Version :1.0
 */
public class DataTransfer {

    public static String getDateTimeAsString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDateTime parseStringToDate(String dateStr) {
        if( dateStr==null ||  dateStr.isEmpty()){
            return null;
        }

        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateStr, df);
        } catch (Exception e) {
            System.out.println("时间格式不对，转换错误。");
            e.printStackTrace();
            return null;
        }

    }

    public static LocalDate Str2YYYYmmDD(String dateStr) {
        if( dateStr==null ||  dateStr.isEmpty()){
            return null;
        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, df);
        } catch (Exception e) {
            System.out.println("时间格式不对，转换错误。");
            e.printStackTrace();
            return null;
        }

    }


}
