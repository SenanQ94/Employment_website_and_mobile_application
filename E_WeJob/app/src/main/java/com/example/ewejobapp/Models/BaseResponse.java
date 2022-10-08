package com.example.ewejobapp.Models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("code") private int code = 0;
    @SerializedName("data") private Object data = null;
    @SerializedName("message") private String message = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
