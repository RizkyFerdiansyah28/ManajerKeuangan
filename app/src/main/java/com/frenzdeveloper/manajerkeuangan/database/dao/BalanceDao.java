package com.frenzdeveloper.manajerkeuangan.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.frenzdeveloper.manajerkeuangan.database.entity.Balance;

import java.util.List;

@Dao
public interface BalanceDao {
    @Query("SELECT * FROM Balance")
    List<Balance> getAll();

    @Query("SELECT balance FROM Balance WHERE user_id = :user_id")
    int getOne(int user_id);

    @Query("UPDATE Balance SET balance = :balance WHERE user_id = :user_id")
    int update(int user_id, double balance);

    @Query("INSERT INTO Balance (user_id,balance) VALUES (:user_id,:balance)")
    Long insertAll(int user_id, double balance);

    @Query("DELETE FROM Balance WHERE user_id = :user_id")
    void delete(int user_id);

}
