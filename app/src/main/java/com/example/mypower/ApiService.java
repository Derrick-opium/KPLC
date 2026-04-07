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

    // Fix: was "getuser" → must be "register" to match server
    @POST("register")
    Call<ApiResponse<Member>> registerUser(@Body Member useres);
    //loginusers

    @POST("login")
    Call<ApiResponse<Member>> loginuser(@Body Member member);

    // Add tokens to meter
    @POST("meters/add-token")
    Call<ApiResponse<Meter>> addTokens(@Body TokenRequest request);

    // Transfer tokens between meters — Fix: remove leading /api/ (BASE_URL already has /api/)
    @POST("meters/transfer")
    Call<ApiResponse<Meter>> transferTokens(@Body TransferRequest request);

    // Get meter balance — Fix: remove leading /api/
    @GET("meters/{meter_number}/balance")
    Call<ApiResponse<BalanceResponnse>> getBalance(@Path("meter_number") String meterNumber);

    // Update delivered units — Fix: remove leading /api/
    @PUT("meters/{meter_number}/deliver")
    Call<ApiResponse<Meter>> updateDelivered(
            @Path("meter_number") String meterNumber,
            @Query("units") int units
    );

    // Hello endpoint — Fix: remove leading /api/
    @GET("hello")
    Call<MessageResponse> getHello();

    // Get items endpoint — Fix: remove leading /api/
    @GET("items")
    Call<MessageResponse> getMessage();
}