package com.example.ewejobapp.Models;

import org.json.JSONObject;

public class APIResponse {

    private final int code;
    private final String message;
    private final JSONObject data;

    public APIResponse(int code, String message, JSONObject data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getData() {
        return data;
    }

}
