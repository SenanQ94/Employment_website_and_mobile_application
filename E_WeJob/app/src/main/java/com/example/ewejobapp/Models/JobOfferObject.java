package com.example.ewejobapp.Models;

import com.google.gson.annotations.SerializedName;

public class JobOfferObject {
    @SerializedName("jobId") private int id = 0;
    @SerializedName("__type") private String __type = "";
    @SerializedName("comName") private String comName = "";
    @SerializedName("field") private String field = "";
    @SerializedName("level") private String level = "";
    @SerializedName("salary") private float salary = 0f;
    @SerializedName("title") private String title  = "";
    @SerializedName("years") private int years = 0;

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
