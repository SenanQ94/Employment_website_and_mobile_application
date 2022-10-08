package com.example.ewejobapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ewejobapp.API.APIs;
import com.example.ewejobapp.API.URLs;
import com.example.ewejobapp.Adapters.CandidatesAdapter;
import com.example.ewejobapp.Models.BaseResponse;
import com.example.ewejobapp.Models.LoginCandidate;
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

public class JobCandidatesActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<LoginCandidate> list;
    private CandidatesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_candidates);

        recycler = findViewById(R.id.list);
        list = new ArrayList<>();
        adapter = new CandidatesAdapter(this,list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        getCandidates();
    }

    private void getCandidates(){
        final ProgressDialog dialog = new ProgressDialog(JobCandidatesActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(URLs.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIs api = retrofit.create(APIs.class);
        Call<BaseResponse> call = api.getJobCandis(getIntent().getIntExtra("id",0));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                dialog.cancel();
                if (BaseFunctions.isResSuccessful(JobCandidatesActivity.this,response)){
                    if (BaseFunctions.processStatus(JobCandidatesActivity.this,response)){
                        String j = new Gson().toJson(response.body().getData());
                        LoginCandidate[] success = new Gson().fromJson(j,LoginCandidate[].class);
                        if (success.length > 0){
                            for (LoginCandidate l : success){
                                list.add(l);
                                adapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(JobCandidatesActivity.this, "No candidates", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }
}
