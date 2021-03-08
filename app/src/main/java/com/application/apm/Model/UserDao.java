package com.application.apm.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.application.apm.View.AddPaymentDialog;

import java.util.List;


@Dao
public interface UserDao {

        @Query("SELECT * FROM Users")
        List<User> getUsers();

        @Query("SELECT * FROM Users WHERE SecondName LIKE:secondName")
        List<User> getUserBySecondName(String secondName);

        @Insert
        Long insertUser(User user);

        @Query("SELECT * FROM Users WHERE ID LIKE:id")
        User getUserById(String id);

        @Update()
        void updateUser(User user);
}

