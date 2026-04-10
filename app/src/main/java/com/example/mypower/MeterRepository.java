package com.example.mypower;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeterRepository {
    private ApiService apiService;
    private Dbhelper localDbHelper;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MeterRepository(Context context) {
        this.apiService = NetworkClient.getClient().create(ApiService.class);
        this.localDbHelper = new Dbhelper(context);
        isLoading.setValue(false);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Register new meter
    public void registerMeter(String meterNumber, int initialUnits,
                              final RepositoryCallback<Meter> callback) {
        isLoading.setValue(true);

        Meter meter = new Meter(meterNumber, initialUnits);

        apiService.registerMeter(meter).enqueue(new Callback<ApiResponse<Meter>>() {
            @Override
            public void onResponse(Call<ApiResponse<Meter>> call,
                                   Response<ApiResponse<Meter>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Meter> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        // Save to local SQLite as backup
                        localDbHelper.insertdata(meterNumber, String.valueOf(initialUnits));
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        errorMessage.setValue(apiResponse.getMessage());
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    errorMessage.setValue("Server error");
                    callback.onError("Server error");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Meter>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue(t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }

    // Add tokens to meter
    public void addTokens(int units, int memberId,
                          final RepositoryCallback<Meter> callback) {
        isLoading.setValue(true);

        TokenRequest request = new TokenRequest(units, memberId);

        apiService.addTokens(request).enqueue(new Callback<ApiResponse<Meter>>() {
            @Override
            public void onResponse(Call<ApiResponse<Meter>> call,
                                   Response<ApiResponse<Meter>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Meter> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        // Get meter number and balance from server response
                        String meterNumber = apiResponse.getData().getMeter_number();
                        double newBalance     = apiResponse.getData().getToken_units();

                        // Update local SQLite with correct values
                        localDbHelper.updateMeterBalance(
                                String.valueOf(newBalance), meterNumber);

                        callback.onSuccess(apiResponse.getData());
                    } else {
                        errorMessage.setValue(apiResponse.getMessage());
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Failed to add tokens");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Meter>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue(t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }



    public void setMeter(int memberId, String meterNumber,
                         final RepositoryCallback<String> callback) {
        isLoading.setValue(true);

        Recievemtr body = new Recievemtr(memberId, meterNumber);

        apiService.setMeter(body).enqueue(new Callback<ApiResponse<Recievemtr>>() {
            @Override
            public void onResponse(Call<ApiResponse<Recievemtr>> call,
                                   Response<ApiResponse<Recievemtr>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Recievemtr> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.onSuccess(meterNumber);
                    } else {
                        errorMessage.setValue(apiResponse.getMessage());
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Failed to set meter number");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Recievemtr>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue(t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }


    public void registerUser(String user, String password, final RepositoryCallback<Member> callback) {
        isLoading.setValue(true);
        Member Useres = new Member(user, password);

        apiService.registerUser(Useres).enqueue(new Callback<ApiResponse<Member>>() {

            @Override
            public void onResponse(Call<ApiResponse<Member>> call, Response<ApiResponse<Member>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Member> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        // Save to local db
                        localDbHelper.insertdata(user, password);
                        callback.onSuccess(apiResponse.getData());

                    } else {
                        // Server returned success=false with a message
                        Log.e("REGISTER_ERROR", "API error: " + apiResponse.getMessage());
                        errorMessage.setValue(apiResponse.getMessage());
                        callback.onError(apiResponse.getMessage());
                    }

                } else {

                    String errorBody = "Server error: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                            Log.e("REGISTER_ERROR", "Error body: " + errorBody);
                        }
                    } catch (Exception e) {
                        Log.e("REGISTER_ERROR", "Could not read error body: " + e.getMessage());
                    }
                    errorMessage.setValue(errorBody);
                    callback.onError(errorBody);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Member>> call, Throwable t) {
                isLoading.setValue(false);
                String failMsg = t.getMessage() != null ? t.getMessage() : "Network failure, check your connection";
                Log.e("REGISTER_ERROR", "onFailure: " + failMsg);
                errorMessage.setValue(failMsg);
                callback.onError(failMsg);
            }
        });
    }
    //login user method

    public void loginUser(String full_name, String password, final RepositoryCallback<Member> callback) {

        // ✅ Use postValue() instead of setValue() — works on any thread
        isLoading.postValue(true);

        Member loginMember = new Member(full_name, password);

        apiService.loginuser(loginMember).enqueue(new Callback<ApiResponse<Member>>() {

            @Override
            public void onResponse(Call<ApiResponse<Member>> call, Response<ApiResponse<Member>> response) {
                isLoading.postValue(false); // ✅ postValue not setValue

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Member> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        // ✅ Run DB insert on background thread so it doesn't block callback
                        new Thread(() -> {
                            localDbHelper.insertdata(full_name, password);
                        }).start();

                        Log.d("LOGIN", "✅ Login successful: " + full_name);
                        callback.onSuccess(apiResponse.getData());

                    } else {
                        Log.e("LOGIN_ERROR", "Login failed: " + apiResponse.getMessage());
                        errorMessage.postValue(apiResponse.getMessage()); // ✅ postValue
                        callback.onError(apiResponse.getMessage());
                    }

                } else {
                    String errorBody = "Server error: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                            Log.e("LOGIN_ERROR", "Error body: " + errorBody);
                        }
                    } catch (Exception e) {
                        Log.e("LOGIN_ERROR", "Could not read error body: " + e.getMessage());
                    }
                    errorMessage.postValue(errorBody); // ✅ postValue
                    callback.onError(errorBody);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Member>> call, Throwable t) {
                isLoading.postValue(false); // ✅ postValue
                String failMsg = t.getMessage() != null ? t.getMessage() : "Network failure, check your connection";
                Log.e("LOGIN_ERROR", "onFailure: " + failMsg);
                errorMessage.postValue(failMsg); // ✅ postValue
                callback.onError(failMsg);
            }
        });
    }

    // Get all meters
    public void getAllMeters(final RepositoryCallback<List<Meter>> callback) {
        isLoading.setValue(true);

        apiService.getAllMeters().enqueue(new Callback<ApiResponse<List<Meter>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Meter>>> call,
                                   Response<ApiResponse<List<Meter>>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Meter>> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Failed to load meters");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Meter>>> call, Throwable t) {
                isLoading.setValue(false);
                callback.onError(t.getMessage());
            }
        });
    }


    // Get meter balance
    public void getBalance(String meterNumber, final RepositoryCallback<Integer> callback) {
        isLoading.setValue(true);

        apiService.getBalance(meterNumber).enqueue(new Callback<ApiResponse<BalanceResponnse>>() {
            @Override
            public void onResponse(Call<ApiResponse<BalanceResponnse>> call,
                                   Response<ApiResponse<BalanceResponnse>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<BalanceResponnse> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData().getBalance());
                    } else {
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Failed to get balance");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BalanceResponnse>> call, Throwable t) {
                isLoading.setValue(false);
                callback.onError(t.getMessage());
            }
        });
    }

    // Transfer tokens
    public void transferTokens(int memberId, String receiverMeter, double units,
                               final RepositoryCallback<Meter> callback) {
        isLoading.setValue(true);

        TransferRequest request = new TransferRequest(memberId, receiverMeter, units);

        apiService.transferTokens(request).enqueue(new Callback<ApiResponse<Meter>>() {
            @Override
            public void onResponse(Call<ApiResponse<Meter>> call,
                                   Response<ApiResponse<Meter>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Meter> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        errorMessage.setValue(apiResponse.getMessage());
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    errorMessage.setValue("Transfer failed");
                    callback.onError("Transfer failed");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Meter>> call, Throwable t) {
                isLoading.setValue(false);
                String msg = t.getMessage() != null ? t.getMessage() : "Unknown error";
                errorMessage.setValue(msg);
                callback.onError(msg);
            }
        });
    }

    // Update delivered units
    public void updateDelivered(String meterNumber, int units,
                                final RepositoryCallback<Meter> callback) {
        isLoading.setValue(true);

        apiService.updateDelivered(meterNumber, units).enqueue(new Callback<ApiResponse<Meter>>() {
            @Override
            public void onResponse(Call<ApiResponse<Meter>> call,
                                   Response<ApiResponse<Meter>> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Meter> apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        // Update local SQLite
                        localDbHelper.updateDeliver(meterNumber, String.valueOf(units));
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    callback.onError("Failed to update delivered units");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Meter>> call, Throwable t) {
                isLoading.setValue(false);
                callback.onError(t.getMessage());
            }
        });
    }

    public interface RepositoryCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }
}

