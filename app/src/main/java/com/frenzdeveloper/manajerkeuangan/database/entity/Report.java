package com.frenzdeveloper.manajerkeuangan.database.entity;

import androidx.annotation.StringDef;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//@Entity(foreignKeys ={@ForeignKey(entity = User.class,parentColumns = "uid",childColumns = "user_id")})
@Entity()
public class Report {
    @PrimaryKey
    public int id;

    public int user_id;

    public double income = 0.0;

    public double outcome = 0.0;

    public String date;

    public String note;

}
