package com.frenzdeveloper.manajerkeuangan.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.frenzdeveloper.manajerkeuangan.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("INSERT INTO User (user_id, name, email) VALUES (:user_id, :fullname, :email)")
    long insert(int user_id, String fullname, String email);

    @Query("SELECT * FROM User WHERE user_id = :id")
    User getUserById(int id);

    @Delete
    void delete(User user);

}
