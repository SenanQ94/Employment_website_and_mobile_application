package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ewejobapp.API.APIs;
import com.example.ewejobapp.API.URLs;
import com.example.ewejobapp.Models.BaseResponse;
import com.example.ewejobapp.R;
import com.example.ewejobapp.Utils.BaseFunctions;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActivity extends AppCompatActivity {

    private RelativeLayout logout;
    private EditText title,field;
    private Button add;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logout = findViewById(R.id.logout);
        title = findViewById(R.id.title);
        field = findViewById(R.id.field);
        add = findViewById(R.id.add);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("logged",false);
                editor.putString("type","");
                editor.putInt("id",0);
                editor.commit();
                startActivity(new Intent(AdminActivity.this,SplashActivity.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().equals("")){
                    Toast.makeText(AdminActivity.this, "You must write the title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (field.getText().toString().equals("")){
                    Toast.makeText(AdminActivity.this, "You must write the field", Toast.LENGTH_SHORT).show();
                    return;
                }

                callAddDiploma();
            }
        });
    }

    private void callAddDiploma(){
        final ProgressDialog dialog = new ProgressDialog(AdminActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.addDiploma(title.getText().toString(),field.getText().toString());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(AdminActivity.this,response)){
                    if (BaseFunctions.processStatus(AdminActivity.this,response)){
                        Toast.makeText(AdminActivity.this, "Created successfully", Toast.LENGTH_SHORT).show();
                        title.setText("");
                        field.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(AdminActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
