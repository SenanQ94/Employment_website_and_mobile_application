package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ewejobapp.API.APIs;
import com.example.ewejobapp.API.URLs;
import com.example.ewejobapp.Adapters.JobOffersAdapter;
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

public class JobsForCandidateActivity extends AppCompatActivity {

    private RadioButton by_years,by_level;
    private RecyclerView recycler;
    private List<JobOfferObject> list;
    private JobOffersAdapter adapter;
    private int selected_sort = -1;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_for_candidate);

        by_years = findViewById(R.id.by_years);
        by_level = findViewById(R.id.by_level);
        recycler = findViewById(R.id.list);
        list = new ArrayList<>();
        adapter = new JobOffersAdapter(this,list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);

        by_years.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    by_years.setTextColor(getResources().getColor(R.color.white));
                    selected_sort = 0;
                    getJobs(0);
                }else {
                    by_years.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        by_level.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    by_level.setTextColor(getResources().getColor(R.color.white));
                    selected_sort = 1;
                    getJobs(1);
                }else {
                    by_level.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        by_years.setChecked(true);
    }

    private void getJobs(int sort){
        final ProgressDialog dialog = new ProgressDialog(JobsForCandidateActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.getJobsForCandidate(sort,sharedPreferences.getInt("id",0));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                list.clear();
                adapter.notifyDataSetChanged();
                if (BaseFunctions.isResSuccessful(JobsForCandidateActivity.this,response)){
                    if (BaseFunctions.processStatus(JobsForCandidateActivity.this,response)){
                        String j = new Gson().toJson(response.body().getData());
                        JobOfferObject[] success = new Gson().fromJson(j,JobOfferObject[].class);
                        if (success.length>0){
                            for (JobOfferObject o : success){
                                list.add(o);
                                adapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(JobsForCandidateActivity.this, "No jobs", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(JobsForCandidateActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
