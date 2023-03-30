package com.frenzdeveloper.manajerkeuangan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.R;
import com.frenzdeveloper.manajerkeuangan.database.AppDatabase;
import com.frenzdeveloper.manajerkeuangan.database.entity.Balance;
import com.frenzdeveloper.manajerkeuangan.database.entity.Report;
import com.frenzdeveloper.manajerkeuangan.sp_agent.SPAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBalance extends AppCompatActivity {

    EditText etBalance;
    Button btnAddBalance;
    AppDatabase database;
    Report laporan = new Report();
    List<String> list = new ArrayList<>();
    Handler handle = new Handler();
    public int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        load();
        setActionBar();
        database = AppDatabase.getInstance(getApplicationContext());

        //Toast.makeText(this, database.balanceDao().getOne(user_id)+"", Toast.LENGTH_SHORT).show();

        if (database.balanceDao().getOne(user_id) == 0){
            btnAddBalance.setOnClickListener(new View.OnClickListener() {
                double balanceTotal = 0.0;
                final int id_in = getUser_id();
                @Override
                public void onClick(View v) {
                    if (etBalance.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Isi Bagian Saldo", Toast.LENGTH_SHORT).show();
                    }else {
                        balanceTotal = Double.parseDouble(etBalance.getText().toString());
                        // Toast.makeText(AddBalance.this, balanceTotal+"", Toast.LENGTH_SHORT).show();
                        try {
                            // Toast.makeText(AddBalance.this, ""+id_in + balanceTotal+" tambah baru", Toast.LENGTH_SHORT).show();
                            Long query = database.balanceDao().insertAll(id_in, balanceTotal);
                            if (query > 0){
                                Toast.makeText(AddBalance.this, "Berhasil ditambahkan ", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddBalance.this, "salaaaaah", Toast.LENGTH_SHORT).show();
                            }

                            handle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 300);
                        } catch (Exception e) {
                            //dev
                            Toast.makeText(AddBalance.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                            // production
//                            Toast.makeText(AddBalance.this, "Gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();
//                            handle.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            }, 300);
                        }
                    }
                }
            });
        }else{
            etBalance.setText(database.balanceDao().getOne(user_id)+"");
            btnAddBalance.setOnClickListener(new View.OnClickListener() {
                double balanceTotal = 0.0;
                final int id_up = getUser_id();
                @Override
                public void onClick(View v) {
                    if (etBalance.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Isi Bagian Saldo", Toast.LENGTH_SHORT).show();
                    }else{
                        balanceTotal = Double.parseDouble(etBalance.getText().toString());
                        try {
                            database.balanceDao().update(user_id, balanceTotal);
                            Toast.makeText(AddBalance.this, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                            handle.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 300);
                        } catch (Exception e) {
                            // dev
                            Toast.makeText(AddBalance.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();

                            // production
//                            Toast.makeText(AddBalance.this, "Gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show();
//                            handle.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    finish();
//                                }
//                            }, 300);
                        }
                    }

                }
            });
        }



    }

    int getUser_id(){
        return SPAgent.getIdLoggedIn(getApplicationContext());
    }

    void load(){
        etBalance = findViewById(R.id.etBalance);
        btnAddBalance = findViewById(R.id.btnAddBalance);
        user_id = SPAgent.getIdLoggedIn(getApplicationContext());
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    void setActionBar(){
        Toolbar custom_actionbar = (Toolbar) findViewById(R.id.custom_actionbar);
        setSupportActionBar(custom_actionbar);
        setTitle("Tambah Saldo");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}