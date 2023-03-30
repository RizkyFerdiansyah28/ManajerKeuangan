package com.frenzdeveloper.manajerkeuangan.loginregister.registermodel;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RegisterResponse {
    protected boolean success;
    protected String message;

    @SerializedName("name")
    protected ArrayList<String> name_error;
    @SerializedName("email")
    protected ArrayList<String> email_error;
    @SerializedName("password")
    protected ArrayList<String> password_error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getName_error() {
        return name_error;
    }

    public void setName_error(ArrayList<String> name_error) {
        this.name_error = name_error;
    }

    public ArrayList<String> getEmail_error() {
        return email_error;
    }

    public void setEmail_error(ArrayList<String> email_error) {
        this.email_error = email_error;
    }

    public ArrayList<String> getPassword_error() {
        return password_error;
    }

    public void setPassword_error(ArrayList<String> password_error) {
        this.password_error = password_error;
    }
}
