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

    // Add tokens to meter
    @POST("meters/add-token")
    Call<ApiResponse<Meter>> addTokens(@Body TokenRequest request);

    // Transfer tokens between meters
    @POST("api/meters/transfer")
    Call<ApiResponse<Meter>> transferTokens(@Body TransferRequest request);

    // Get meter balance
    @GET("api/meters/{meter_number}/balance")
    Call<ApiResponse<BalanceResponnse>> getBalance(@Path("meter_number") String meterNumber);

    // Update delivered units
    @PUT("api/meters/{meter_number}/deliver")
    Call<ApiResponse<Meter>> updateDelivered(
            @Path("meter_number") String meterNumber,
            @Query("units") int units
    );

    // Hello endpoint (for testing)
    @GET("api/hello")
    Call<MessageResponse> getHello();

    // Get message endpoint
    @GET("api/items")
    Call<MessageResponse> getMessage();
}
