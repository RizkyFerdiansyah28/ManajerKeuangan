package com.frenzdeveloper.manajerkeuangan.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity()
public class Balance {
    @PrimaryKey
    public int id;

    public int user_id;

    public double balance;
}
