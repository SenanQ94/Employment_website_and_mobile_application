package com.example.ewejobapp.Models;

import com.google.gson.annotations.SerializedName;

public class DiplomaObject {
    @SerializedName("__type") private String __type = "";
    @SerializedName("d_field") private String d_field = "";
    @SerializedName("d_title") private String d_title = "";

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getD_field() {
        return d_field;
    }

    public void setD_field(String d_field) {
        this.d_field = d_field;
    }

    public String getD_title() {
        return d_title;
    }

    public void setD_title(String d_title) {
        this.d_title = d_title;
    }
}
