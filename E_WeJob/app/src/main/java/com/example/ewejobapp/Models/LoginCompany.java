package com.example.ewejobapp.Models;

import com.google.gson.annotations.SerializedName;

public class LoginCompany {

    @SerializedName("c_id") private int userId = 0;
    @SerializedName("c_name") private String name = "";
    @SerializedName("c_phone") private String phone = "";
    @SerializedName("__type") private String __type = "";
    @SerializedName("password") private String password = "";
    @SerializedName("username") private String username = "";


    public LoginCompany() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

