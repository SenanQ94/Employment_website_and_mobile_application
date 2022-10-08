package com.example.ewejobapp.Utils;

import android.content.Context;
import android.widget.Toast;

import com.example.ewejobapp.Models.BaseResponse;

import retrofit2.Response;

public class BaseFunctions {
    public static boolean isResSuccessful(Context context,Response<BaseResponse> response){
        if (response.isSuccessful()){
            return true;
        }else {
            Toast.makeText(context, "Error in server, please retry", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean processStatus(Context context,Response<BaseResponse> response){
        if (response.body().getCode() == 1){
            return true;
        }else {
            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
