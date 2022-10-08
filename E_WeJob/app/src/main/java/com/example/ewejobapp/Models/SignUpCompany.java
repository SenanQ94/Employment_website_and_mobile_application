package com.example.ewejobapp.Models;

public class SignUpCompany {

    private String userId;
    private String name;
    private String phone;
    private String username;
    private String password;

    public SignUpCompany(String userId, String name, String phone, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public SignUpCompany() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
