package com.example.connectapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServer {
    @GET("/path")
    Call<User> getUser();
}
