package com.example.ewejobapp.Models;

public class SignUpCandidate {

    private String userId;
    private String fullName;
    private String phone;
    private String username;
    private String password;
    private String expField;
    private String expLevel;
    private String expYears;

    public SignUpCandidate(String userId, String fullName, String phone, String username, String password, String expField, String expLevel, String expYears) {
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.expField = expField;
        this.expLevel = expLevel;
        this.expYears = expYears;
    }

    public SignUpCandidate() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getExpField() {
        return expField;
    }

    public void setExpField(String expField) {
        this.expField = expField;
    }

    public String getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(String expLevel) {
        this.expLevel = expLevel;
    }

    public String getExpYears() {
        return expYears;
    }

    public void setExpYears(String expYears) {
        this.expYears = expYears;
    }
}
