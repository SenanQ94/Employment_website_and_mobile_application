package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AddJobOfferActivity extends AppCompatActivity {

    private EditText title,salary,years;
    private Spinner field_spinner;
    private ArrayAdapter<String> field_adapter;
    private List<DiplomaObject> list;
    private List<String> list_string;
    private Button add;

    private String selected_field = "";
    private String selected_level = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_offer);

        title = findViewById(R.id.title);
        salary = findViewById(R.id.salary);
        years = findViewById(R.id.years);
        field_spinner = findViewById(R.id.field);
        add = findViewById(R.id.add);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);

        init_spinner();

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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().equals("")){
                    Toast.makeText(AddJobOfferActivity.this, "You must write the title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (salary.getText().toString().equals("") || salary.getText().toString().equals("0")){
                    Toast.makeText(AddJobOfferActivity.this, "You must write the salary", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selected_field.equals("")){
                    Toast.makeText(AddJobOfferActivity.this, "You must pick the field", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (years.getText().toString().equals("") || years.getText().toString().equals("0")){
                    Toast.makeText(AddJobOfferActivity.this, "You must write the years", Toast.LENGTH_SHORT).show();
                    return;
                }

                callAddAPI();
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
        final ProgressDialog dialog = new ProgressDialog(AddJobOfferActivity.this);
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
                if (BaseFunctions.isResSuccessful(AddJobOfferActivity.this,response)){
                    if (BaseFunctions.processStatus(AddJobOfferActivity.this,response)){
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

    private void callAddAPI(){
        final ProgressDialog dialog = new ProgressDialog(AddJobOfferActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Log.i("jfhg", "callAddAPI: "+sharedPreferences.getInt("id",0));
        Call<BaseResponse> call = api.addJob(
                sharedPreferences.getInt("id",0),
                salary.getText().toString(),
                title.getText().toString(),
                selected_level,
                selected_field,
                Integer.valueOf(years.getText().toString()));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(AddJobOfferActivity.this,response)){
                    if (BaseFunctions.processStatus(AddJobOfferActivity.this,response)){
                        Toast.makeText(AddJobOfferActivity.this, "Created successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(AddJobOfferActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
