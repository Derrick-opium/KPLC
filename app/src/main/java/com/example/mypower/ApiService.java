package com.example.mypower;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {

    // Get all meters
    @GET("alldata")
    Call<ApiResponse<List<Meter>>> getAllMeters();

    // Get meter by number
    @GET("meters/{meter_number}")
    Call<ApiResponse<Meter>> getMeter(@Path("meter_number") String meterNumber);

    // Register new meter
    @POST("deposit")
    Call<ApiResponse<Meter>> registerMeter(@Body Meter meter);

    // Set meter number
    @POST("set_meter")
    Call<ApiResponse<Recievemtr>> setMeter(@Body Recievemtr body);

    // Register user
    @POST("register")
    Call<ApiResponse<Member>> registerUser(@Body Member useres);

    // Login user
    @POST("login")
    Call<ApiResponse<Member>> loginuser(@Body Member member);

    // Add tokens
    @POST("add_tokens")
    Call<ApiResponse<Meter>> addTokens(@Body TokenRequest request);

    // Transfer tokens
    @POST("transfer")
    Call<ApiResponse<Meter>> transferTokens(@Body TransferRequest request);

    // Get meter balance
    @GET("meters/{meter_number}/balance")
    Call<ApiResponse<BalanceResponnse>> getBalance(@Path("meter_number") String meterNumber);

    // Update delivered units
    @PUT("meters/{meter_number}/deliver")
    Call<ApiResponse<Meter>> updateDelivered(
            @Path("meter_number") String meterNumber,
            @Query("units") int units
    );

    // Hello endpoint
    @GET("hello")
    Call<MessageResponse> getHello();

    // Get items
    @GET("items")
    Call<MessageResponse> getMessage();
}