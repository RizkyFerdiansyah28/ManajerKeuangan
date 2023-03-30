package com.frenzdeveloper.manajerkeuangan.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.frenzdeveloper.manajerkeuangan.database.dao.BalanceDao;
import com.frenzdeveloper.manajerkeuangan.database.dao.ReportDao;
import com.frenzdeveloper.manajerkeuangan.database.dao.UserDao;
import com.frenzdeveloper.manajerkeuangan.database.entity.Balance;
import com.frenzdeveloper.manajerkeuangan.database.entity.Report;
import com.frenzdeveloper.manajerkeuangan.database.entity.User;

@Database(entities = {User.class, Report.class, Balance.class}, version = 22)
public abstract class AppDatabase extends RoomDatabase {
    private static  AppDatabase Instance;
    private final MutableLiveData<Boolean> IsDatabaseCreated = new MutableLiveData<>();

    @VisibleForTesting
    public static final String DATABASE_NAME = "frenzy";

    public abstract UserDao userDao();
    public abstract ReportDao reportDao();
    public abstract BalanceDao balanceDao();

    void setDatabaseCreated(){
        IsDatabaseCreated.postValue(true);
    }

    private void updateDatabaseCreated(final Context context){
        if (context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }

    public static AppDatabase buildDatabase(final Context context){
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppDatabase database = AppDatabase.getInstance(context);
                        database.setDatabaseCreated();
                    }
                }).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static AppDatabase getInstance(final Context context){
        if (Instance == null){
            synchronized (AppDatabase.class){
                if (Instance == null){
                    Instance = buildDatabase(context);
                    Instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return Instance;
    }
}
