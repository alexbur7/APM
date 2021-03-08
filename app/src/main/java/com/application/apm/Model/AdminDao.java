package com.application.apm.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AdminDao {

    @Query("SELECT * FROM Admins WHERE login LIKE:log AND password LIKE:pass")
    Admin getAdmin(String log, String pass);

    @Insert()
    void insertAdmin(Admin admin);
}
