package com.frenzdeveloper.manajerkeuangan.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    public int user_id;

    @ColumnInfo(name = "name")
    public String fullname;

    public String email;
}
