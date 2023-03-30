package com.frenzdeveloper.manajerkeuangan.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.frenzdeveloper.manajerkeuangan.database.entity.Report;

import java.util.List;

@Dao
public interface ReportDao {
    @Query("SELECT * FROM Report")
    List<Report> getAll();

    @Query("SELECT * FROM Report WHERE user_id = :user_id ORDER BY id DESC")
    List<Report> getAllReportById(int user_id);

    @Query("SELECT * FROM Report WHERE user_id = :user_id AND income > 0 ORDER BY id DESC")
    List<Report> getIncomeById(int user_id);

    @Query("SELECT * FROM Report WHERE user_id = :user_id AND outcome > 0 ORDER BY id DESC")
    List<Report> getOutcomeById(int user_id);

    @Query("INSERT INTO Report (user_id, income, outcome, date, note) VALUES (:user_id, :income, :outcome, :date, :note)")
    void insertAll(int user_id, double income, double outcome, String date, String note);

    @Query("DELETE FROM Report WHERE user_id = :user_id")
    int deleteAllReportByUserId(int user_id);

    @Query("DELETE FROM Report WHERE id = :id")
    int deleteReportById(int id);

}
