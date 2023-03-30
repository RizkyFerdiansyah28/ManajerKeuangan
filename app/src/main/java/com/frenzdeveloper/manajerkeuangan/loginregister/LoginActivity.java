package com.frenzdeveloper.manajerkeuangan.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.MainActivity;
import com.frenzdeveloper.manajerkeuangan.R;
import com.frenzdeveloper.manajerkeuangan.apiclient.ApiClient;
import com.frenzdeveloper.manajerkeuangan.database.AppDatabase;
import com.frenzdeveloper.manajerkeuangan.loginregister.loginmodel.LoginRequest;
import com.frenzdeveloper.manajerkeuangan.loginregister.loginmodel.LoginResponse;
import com.frenzdeveloper.manajerkeuangan.sp_agent.SPAgent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private TextView tvToRegis;
    private Button btnLogin;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        load();
        setActionbar();

        database = AppDatabase.getInstance(LoginActivity.this);

        tvToRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(etEmail.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();

                    //start eror validasi
                    if (loginResponse.getEmail() != null){
                        etEmail.setError(loginResponse.getEmail().get(0));
                        etEmail.requestFocus();
                        //Toast.makeText(MainActivity.this, loginResponse.getEmail().get(0), Toast.LENGTH_SHORT).show();
                    }
                    if (loginResponse.getPassword() != null){
                        etPassword.setError(loginResponse.getPassword().get(0));
                        etPassword.requestFocus();
                        return;
                        //Toast.makeText(MainActivity.this, loginResponse.getPassword().get(0), Toast.LENGTH_SHORT).show();
                    }
                    //end eror validasi

                    if (loginResponse.isStatus()){
                        Toast.makeText(LoginActivity.this, "Login sukses, selamat datang "+loginResponse.getUser_name(), Toast.LENGTH_SHORT).show();

                        // kode mengalihkan user ke dashboard
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SPAgent.setIdLoggedIn(getBaseContext(), loginResponse.getId());
                                    SPAgent.setUsernameLoggedIn(getBaseContext(), loginResponse.getUser_name());
                                    SPAgent.setIsLoggedIn(getBaseContext(), true);

                                    // tambah user yg login ke database lokal
                                    try {
                                        if (database.userDao().insert(loginResponse.getId(), loginResponse.getUser_name(), loginResponse.getUser_email()) != 0){
                                            finish();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        }
                                        // bersihkan data user guest
                                        if (database.balanceDao().getOne(1) != 0){
                                            try {
                                                database.balanceDao().delete(1);
                                                System.out.println("Sukses hapus data guest");
                                            }catch (Exception e){
                                                Log.e("DelGuestDataException", e.getLocalizedMessage());
                                            }
                                        }
                                        if (database.reportDao().getAllReportById(1).size() != 0){
                                            try {
                                                database.reportDao().deleteAllReportByUserId(1);
                                                System.out.println("Sukses hapus data guest");
                                            }catch (Exception e){
                                                Log.e("DelGuestDataException", e.getLocalizedMessage());
                                            }
                                        }
                                    }catch (Exception e){
                                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                                    }

                                    //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }catch (Exception e){
                                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 1300);
                    }else {
                        etEmail.setError("Password/email salah!");
                        etEmail.requestFocus();
                        etPassword.setError("Password/email salah!");
                        etPassword.requestFocus();
                        return;
                    }

                    //Toast.makeText(MainActivity.this, "Login Sukses, selamat datang "+loginResponse.getName(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Terjadi kesalahan, silahkan coba lagi!" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void load(){
        etEmail = findViewById(R.id.etEmailRegis);
        etPassword = findViewById(R.id.etPasswordRegis);
        btnLogin = findViewById(R.id.btnRegis);
        tvToRegis = findViewById(R.id.tvToLogin);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    void setActionbar(){
        Toolbar custom_actionbar = (Toolbar) findViewById(R.id.custom_toolbar_login);
        setSupportActionBar(custom_actionbar);
        setTitle("Masuk ke akun");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}