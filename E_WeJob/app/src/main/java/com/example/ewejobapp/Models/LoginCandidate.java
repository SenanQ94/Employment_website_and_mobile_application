package com.example.ewejobapp.Models;

import com.google.gson.annotations.SerializedName;

public class LoginCandidate {

    @SerializedName("p_id") private int p_id = 0;
    @SerializedName("full_name") private String full_name = "";
    @SerializedName("phone") private String phone = "";
    @SerializedName("edu_field") private String edu_field = "";
    @SerializedName("exp_lev") private String exp_lev = "";
    @SerializedName("exp_years") private int exp_years = 0;

    public LoginCandidate() {
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEdu_field() {
        return edu_field;
    }

    public void setEdu_field(String edu_field) {
        this.edu_field = edu_field;
    }

    public String getExp_lev() {
        return exp_lev;
    }

    public void setExp_lev(String exp_lev) {
        this.exp_lev = exp_lev;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getExp_years() {
        return exp_years;
    }

    public void setExp_years(int exp_years) {
        this.exp_years = exp_years;
    }
}
