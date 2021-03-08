package com.application.apm.Model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,Payment.class,Admin.class}, version = 1)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao getModelDao();

    public abstract PaymentDao getPaymentDao();

    public abstract AdminDao getAdminDao();
}
