package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ewejobapp.API.APIs;
import com.example.ewejobapp.API.URLs;
import com.example.ewejobapp.Models.BaseResponse;
import com.example.ewejobapp.Models.DiplomaObject;
import com.example.ewejobapp.R;
import com.example.ewejobapp.Utils.BaseFunctions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText full_name,user_name,phone,password,years;
    private RadioButton candi,company;
    private RelativeLayout field_layout;
    private Button register;
    private Spinner field_spinner;
    private ArrayAdapter<String> field_adapter;
    private List<DiplomaObject> list;
    private List<String> list_string;

    private String selected_type = "";
    private String selected_field = "";
    private String selected_level = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        full_name = findViewById(R.id.fullname);
        user_name = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        years = findViewById(R.id.years);
        candi = findViewById(R.id.candidate);
        company = findViewById(R.id.company);
        register = findViewById(R.id.register);
        field_layout = findViewById(R.id.field_layout);
        field_spinner = findViewById(R.id.field);
        init_spinner();

        candi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    candi.setTextColor(getResources().getColor(R.color.white));
                    selected_type = "0";
                    field_layout.setVisibility(View.VISIBLE);
                    years.setVisibility(View.VISIBLE);
                    selected_field = "";
                    selected_level = "";
                    field_spinner.setSelection(0);
                    years.setText("");
                }else {
                    candi.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    company.setTextColor(getResources().getColor(R.color.white));
                    selected_type = "5";
                    field_layout.setVisibility(View.GONE);
                    years.setVisibility(View.GONE);
                }else {
                    company.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        company.setChecked(true);

        field_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DiplomaObject d = list.get(position);
                selected_field = d.getD_field();
                selected_level = d.getD_title();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (full_name.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "You must write your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user_name.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "You must write your user name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.getText().toString().length()!=10){
                    Toast.makeText(RegisterActivity.this, "Mobile number must be 10 digits only", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length()<8){
                    Toast.makeText(RegisterActivity.this, "Password must be 8 characters at least", Toast.LENGTH_SHORT).show();
                }

                if (selected_type.equals("")){
                    Toast.makeText(RegisterActivity.this, "You must select account type", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Candidate
                if (selected_type.equals("0")){
                    if (selected_field.equals("")){
                        Toast.makeText(RegisterActivity.this, "You must select your education field", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (years.getText().toString().equals("") || years.getText().toString().equals("0")){
                        Toast.makeText(RegisterActivity.this, "You must select your experience years", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    registerCandi();
                }
                //Company
                else if (selected_type.equals("5")){
                    registerCompany();
                }
            }
        });
    }

    private void init_spinner(){
        list = new ArrayList<>();
        list_string = new ArrayList<>();
        field_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list_string);
        field_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        field_spinner.setAdapter(field_adapter);
        getDiplomas();
    }

    private void getDiplomas(){
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.getDeplomas();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(RegisterActivity.this,response)){
                    if (BaseFunctions.processStatus(RegisterActivity.this,response)){
                        String j = new Gson().toJson(response.body().getData());
                        DiplomaObject[] success = new Gson().fromJson(j,DiplomaObject[].class);
                        if (success.length > 0){
                            for (DiplomaObject d : success){
                                list.add(d);
                                list_string.add(d.getD_field()+"-"+d.getD_title());
                                field_adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
            }
        });
    }

    private void registerCandi(){
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.registerCandi(user_name.getText().toString(),
                password.getText().toString(),
                full_name.getText().toString(),
                phone.getText().toString(),
                years.getText().toString(),
                selected_level,
                selected_field);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(RegisterActivity.this,response)){
                    if (BaseFunctions.isResSuccessful(RegisterActivity.this,response)){
                        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(RegisterActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerCompany(){
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.registerCompany(
                full_name.getText().toString(),
                phone.getText().toString(),
                user_name.getText().toString(),
                password.getText().toString());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(RegisterActivity.this,response)){
                    if (BaseFunctions.isResSuccessful(RegisterActivity.this,response)){
                        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(RegisterActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
