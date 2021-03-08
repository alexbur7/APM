package com.application.apm.Model;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PaymentDao {

    @Insert
    Long insertPayment(Payment payment);

    @Update
    void updatePayment(Payment payment);

    @Query("SELECT * FROM Payments WHERE ID LIKE:id")
    List<Payment> getPaymentById(String id);
}
