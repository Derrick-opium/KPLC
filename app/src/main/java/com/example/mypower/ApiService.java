package com.example.mypower;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/items") // your actual endpoint
    Call<MessageResponse> getMessage();

    @GET("api/hello") // endpoint returning { "message": "value" }
    Call<MessageResponse> getHello();

}