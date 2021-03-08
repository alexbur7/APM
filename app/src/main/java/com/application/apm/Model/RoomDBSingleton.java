package com.application.apm.Model;

import android.content.Context;

import androidx.room.Room;

public class RoomDBSingleton {
    private UserDataBase base;
    private Context mContext;
    private static RoomDBSingleton sRoomDBSingleton;

    private RoomDBSingleton(Context context){
        this.mContext=context;
        base=Room.databaseBuilder(mContext, UserDataBase.class, "DataBase.db")
                .allowMainThreadQueries()
                .build();
    }

    public static RoomDBSingleton getInstance(Context context){
        if (sRoomDBSingleton==null){
            sRoomDBSingleton=new RoomDBSingleton(context);
        }
        return sRoomDBSingleton;
    }

    public UserDao getUserDao(){
        return base.getModelDao();
    }

    public PaymentDao getPaymentDao(){return base.getPaymentDao();}

    public AdminDao getAdminDao(){return base.getAdminDao();}
}
