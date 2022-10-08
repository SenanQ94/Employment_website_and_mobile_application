package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ewejobapp.API.APIs;
import com.example.ewejobapp.API.URLs;
import com.example.ewejobapp.Adapters.JobOffersBycomAdapter;
import com.example.ewejobapp.Models.BaseResponse;
import com.example.ewejobapp.Models.JobOfferObject;
import com.example.ewejobapp.R;
import com.example.ewejobapp.Utils.BaseFunctions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<JobOfferObject> list;
    private JobOffersBycomAdapter adapter;
    private Button add;
    private RelativeLayout logout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compnay);

        add = findViewById(R.id.add_job);
        logout = findViewById(R.id.logout);
        recycler = findViewById(R.id.list);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        list = new ArrayList<>();
        adapter = new JobOffersBycomAdapter(this,list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompanyActivity.this,AddJobOfferActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("logged",false);
                editor.putString("type","");
                editor.putInt("id",0);
                editor.commit();
                startActivity(new Intent(CompanyActivity.this,SplashActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getJobs();
    }

    private void getJobs(){
        final ProgressDialog dialog = new ProgressDialog(CompanyActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.getCompanyJobs(sharedPreferences.getInt("id",0));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                list.clear();
                adapter.notifyDataSetChanged();
                if (BaseFunctions.isResSuccessful(CompanyActivity.this,response)){
                    if (BaseFunctions.processStatus(CompanyActivity.this,response)){
                        String j = new Gson().toJson(response.body().getData());
                        JobOfferObject[] success = new Gson().fromJson(j,JobOfferObject[].class);
                        if (success.length>0){
                            for (JobOfferObject o : success){
                                list.add(o);
                                adapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(CompanyActivity.this, "No jobs", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(CompanyActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
