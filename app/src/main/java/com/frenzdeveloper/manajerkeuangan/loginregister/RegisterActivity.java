package com.frenzdeveloper.manajerkeuangan.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.R;
import com.frenzdeveloper.manajerkeuangan.apiclient.ApiClient;
import com.frenzdeveloper.manajerkeuangan.loginregister.registermodel.RegisterRequest;
import com.frenzdeveloper.manajerkeuangan.loginregister.registermodel.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etPasswordConfirm;
    private Button btnRegis;
    private TextView tvToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        load();
        setActionbar();

        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pass = etPassword.getText().toString();
                String PassConfirm = etPasswordConfirm.getText().toString();
                if (PassConfirm.equals(Pass)){
                    regis();
                }else {
                    etPasswordConfirm.setError("Password confirmation not match!");
                    etPasswordConfirm.requestFocus();
                }
            }
        });
    }

    void regis(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(etName.getText().toString());
        registerRequest.setEmail(etEmail.getText().toString());
        registerRequest.setPassword(etPassword.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().userRegister(registerRequest);

        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse.getName_error() != null){
                        etName.setError(registerResponse.getName_error().get(0));
                        etName.requestFocus();
                        //Toast.makeText(RegisterActivity.this, registerResponse.getEmail().get(0)+"", Toast.LENGTH_SHORT).show();
                    }
                    if (registerResponse.getEmail_error() != null){
                        etEmail.setError(registerResponse.getEmail_error().get(0));
                        etEmail.requestFocus();
                        //Toast.makeText(RegisterActivity.this, registerResponse.getEmail().get(0)+"", Toast.LENGTH_SHORT).show();
                    }
                    if (registerResponse.getPassword_error() != null){
                        etPassword.setError(registerResponse.getPassword_error().get(0));
                        etPassword.requestFocus();
                        //Toast.makeText(RegisterActivity.this, registerResponse.getPassword().get(0)+"", Toast.LENGTH_SHORT).show();
                    }

                    // redirect ke halaman login
                    if (registerResponse.isSuccess()){
                        Toast.makeText(RegisterActivity.this, "Berhasil buat akun, anda akan diarahkan ke halaman login...", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                        }, 1200);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Throwable, "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void load(){
        etName = findViewById(R.id.etNameRegis);
        etEmail = findViewById(R.id.etEmailRegis);
        etPassword = findViewById(R.id.etPasswordRegis);
        etPasswordConfirm = findViewById(R.id.PasswordConfirmation);
        btnRegis = findViewById(R.id.btnRegis);
        tvToLogin = findViewById(R.id.tvToLogin);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    void setActionbar(){
        Toolbar custom_actionbar = (Toolbar) findViewById(R.id.custom_toolbar_regis);
        setSupportActionBar(custom_actionbar);
        setTitle("Buat akun");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}