package com.frenzdeveloper.manajerkeuangan.apiclient;

import com.frenzdeveloper.manajerkeuangan.loginregister.loginmodel.LoginRequest;
import com.frenzdeveloper.manajerkeuangan.loginregister.loginmodel.LoginResponse;
import com.frenzdeveloper.manajerkeuangan.loginregister.registermodel.RegisterRequest;
import com.frenzdeveloper.manajerkeuangan.loginregister.registermodel.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    @Headers("Accept: application/json")
    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @Headers("Accept: application/json")
    @POST("register")
    Call<RegisterResponse> userRegister(@Body RegisterRequest registerRequest);
}
