package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ewejobapp.API.APIs;
import com.example.ewejobapp.API.URLs;
import com.example.ewejobapp.Models.BaseResponse;
import com.example.ewejobapp.Models.LoginCandidate;
import com.example.ewejobapp.Models.LoginCompany;
import com.example.ewejobapp.R;
import com.example.ewejobapp.Utils.BaseFunctions;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    private RadioButton company,candi,admin;
    private Button login;
    private TextView register;

    private int selected_type = -1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        company = findViewById(R.id.company);
        candi = findViewById(R.id.candi);
        admin = findViewById(R.id.admin);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selected_type = 5;
                }
            }
        });

        candi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selected_type = 0;
                }
            }
        });

        admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selected_type = 1;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "You must write your user name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "You must write your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selected_type == -1){
                    Toast.makeText(LoginActivity.this, "You must select account type", Toast.LENGTH_SHORT).show();
                    return;
                }

                //If admin
                if (selected_type == 1){
                    if(!username.getText().toString().equals("admin") || password.getText().toString().equals("admin")){
                        Toast.makeText(LoginActivity.this, "You must input the admin username and password correctly", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putBoolean("logged",true);
                    editor.putString("type","admin");
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                    finish();
                }
                //Else
                else {
                    callLoginAPI();
                }
            }
        });
    }

    private void callLoginAPI(){
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.login(String.valueOf(selected_type),username.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(LoginActivity.this,response)){
                    if (BaseFunctions.processStatus(LoginActivity.this,response)){
                        String j = new Gson().toJson(response.body().getData());
                        //Candidate
                        if (selected_type == 0){
                            LoginCandidate success = new Gson().fromJson(j,LoginCandidate.class);
                            editor.putBoolean("logged",true);
                            editor.putString("type","candi");
                            editor.putInt("id",success.getP_id());
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this,CandidateActivity.class));
                            finish();
                        }
                        //Company
                        else {
                            LoginCompany success = new Gson().fromJson(j,LoginCompany.class);
                            editor.putBoolean("logged",true);
                            editor.putString("type","company");
                            editor.putInt("id",success.getUserId());
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this,CompanyActivity.class));
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
