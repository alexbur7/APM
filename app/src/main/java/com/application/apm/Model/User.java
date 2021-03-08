package com.application.apm.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;


@Entity(tableName = "Users")
public class User implements ModelAble {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "ID")
    private String id;
    @ColumnInfo(name = "Age")
    private int age;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "Date")
    private Date date;
    @ColumnInfo(name = "Name")
    private String name;
    @ColumnInfo(name = "SecondName")
    private String SecondName;


    public User(){}
    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
