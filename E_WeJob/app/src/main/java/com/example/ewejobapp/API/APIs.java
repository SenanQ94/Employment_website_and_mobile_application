package com.example.ewejobapp.API;

import com.example.ewejobapp.Models.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {


    @GET("SignUpCandidate/")
    Call<BaseResponse> registerCandi(
            @Query("username") String username,
            @Query("password") String password,
            @Query("fullName") String fullName,
            @Query("phone") String phone,
            @Query("expYears") String expYears,
            @Query("expLevel") String expLevel,
            @Query("expField") String expField);

    @GET("SignUpCompany/")
    Call<BaseResponse> registerCompany(
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("login/")
    Call<BaseResponse> login(
            @Query("userType") String userType,
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("getDiploma")
    Call<BaseResponse> getDeplomas();

    @GET("getAllJobs/")
    Call<BaseResponse> getJobsOffers(
            @Query("q") int q
    );

    @GET("getspecJobs/")
    Call<BaseResponse> getJobsForCandidate(
            @Query("q") int q,
            @Query("userId") int userId
    );

    @GET("addDiploma/")
    Call<BaseResponse> addDiploma(
            @Query("title") String title,
            @Query("field") String field);

    @GET("getComCandidate/")
    Call<BaseResponse> getCompanyJobs(
            @Query("comId") int company_id
    );

    @GET("addJob/")
    Call<BaseResponse> addJob(
            @Query("userId") int userId,
            @Query("salary") String salary,
            @Query("title") String title,
            @Query("reqLevel") String reqLevel,
            @Query("reqEdu") String reqEdu,
            @Query("expYears") int expYears
    );

    @GET("getspecCandidate/")
    Call<BaseResponse> getJobCandis(
            @Query("jobId") int jobId
    );
}
