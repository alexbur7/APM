package com.application.apm.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Payments")
public class Payment implements ModelAble {
    @PrimaryKey(autoGenerate = true)
    private int _id;
    @ColumnInfo(name = "ID")
    private String id;
    @ColumnInfo(name = "Sum")
    private int sum;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "Date")
    private Date mDate;

    public Payment(String id, int sum) {
        this.id=id;
        this.sum=sum;
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        try {
            this.mDate=sdf.parse(new Date().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
