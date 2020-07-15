package com.mythmayor.basicproject.database.converter;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by mythmayor on 2020/7/15.
 */
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
