package com.application.apm.Model;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Long fromDate(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(Long time){
        return new Date(time);
    }


}
