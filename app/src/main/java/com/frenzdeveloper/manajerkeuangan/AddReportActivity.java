package com.frenzdeveloper.manajerkeuangan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.database.AppDatabase;
import com.frenzdeveloper.manajerkeuangan.database.entity.Report;
import com.frenzdeveloper.manajerkeuangan.menufragment.Home;
import com.frenzdeveloper.manajerkeuangan.sp_agent.SPAgent;

import java.util.Calendar;

public class AddReportActivity extends AppCompatActivity {

    private AppCompatSpinner spin;
    private Button btnSimpan;
    private EditText etIncome, etOutcome, etDate, etNote;
    private AppDatabase database;
    private Handler handle = new Handler();
    private int d, m, y, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        load();

        etDate.setOnClickListener(v -> {
            try {
                final Calendar cal = Calendar.getInstance();
                d = cal.get(Calendar.DAY_OF_MONTH);
                m = cal.get(Calendar.MONTH);
                y = cal.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(dayOfMonth +"-"+(month + 1)+"-"+year);
                    }
                }, y, m, d);
                dpd.show();
            }catch (Exception e){
                Toast.makeText(this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        database = AppDatabase.getInstance(getApplicationContext());

        btnSimpan.setOnClickListener(v -> {

            double bal;
            double outcome = 0;
            double income = 0;
            String note = "No data";
            String date = etDate.getText().toString();

            // untuk mengurangi balance ketika ditambah pengeluaran
            if (!etOutcome.getText().toString().equals("")){
                bal = database.balanceDao().getOne(user_id);
                outcome = Double.parseDouble(etOutcome.getText().toString());
                database.balanceDao().update(user_id, bal - outcome);
            }

            // untuk menambah balance ketika ditambah pemasukan
            if (!etIncome.getText().toString().equals("")){
                bal = database.balanceDao().getOne(user_id);
                income = Double.parseDouble(etIncome.getText().toString());
                database.balanceDao().update(user_id, bal + income);
                //Toast.makeText(this, bal+income+"", Toast.LENGTH_SHORT).show();
            }

            if (!etNote.getText().toString().equals("")){
                note = etNote.getText().toString();
            }

            // masukkan ke table report
            try {
                database.reportDao().insertAll(user_id, income, outcome, date, note);
                Toast.makeText(this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            }catch (Exception e){
                //dev
                //Toast.makeText(this, e.getMessage()+"", Toast.LENGTH_SHORT).show();

                //production
                Toast.makeText(this, "Gagal, silahkan coba lagi!", Toast.LENGTH_SHORT).show();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 300);
            };
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    void setActionBar(String title){
        Toolbar custom_actionbar = (Toolbar) findViewById(R.id.custom_actionbar);
        setSupportActionBar(custom_actionbar);
        if (!title.equals("")){
            setTitle("Tambah "+title);
        }
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void load(){
        etIncome = findViewById(R.id.etIncome);
        etOutcome = findViewById(R.id.etOutcome);

        user_id = SPAgent.getIdLoggedIn(getApplicationContext());

        if (getIntent().getExtras().getBoolean("income")){
            setActionBar("Income");
            etOutcome.setVisibility(View.GONE);
        }else if (getIntent().getExtras().getBoolean("outcome")){
            setActionBar("Outcome");
            etIncome.setVisibility(View.GONE);
        }
        btnSimpan = findViewById(R.id.btnSimpan);
        etDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);
    }
}