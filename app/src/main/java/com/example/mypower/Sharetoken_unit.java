package com.example.mypower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Sharetoken_unit extends AppCompatActivity {

    TextInputEditText sourceMeterInput, unitsInput, receiverMeterInput, balanceDisplay;
    MaterialButton btnShare, btnBack;
    ProgressBar progressBar;
    MeterRepository repository;
    int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharetoken_unit);

        sourceMeterInput  = findViewById(R.id.mtrnum);
        unitsInput        = findViewById(R.id.tknunit);
        receiverMeterInput = findViewById(R.id.delivery);
        balanceDisplay    = findViewById(R.id.bala);
        btnShare          = findViewById(R.id.btnshare);
        btnBack           = findViewById(R.id.btnbac);
        progressBar       = findViewById(R.id.progressBar);

        repository = new MeterRepository(this);

        // Get saved session data
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        memberId = prefs.getInt("member_id", -1);
        String savedMeter = prefs.getString("meter_number", "");

        // Pre-fill source meter if saved
        if (!savedMeter.isEmpty()) {
            sourceMeterInput.setText(savedMeter);
        }

        if (memberId == -1) {
            Toast.makeText(this, "Session expired. Please login again.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login_form.class));
            finish();
            return;
        }

        // Load and display current balance
        loadBalance(savedMeter);

        btnShare.setOnClickListener(v -> {
            String sourceMeter   = sourceMeterInput.getText().toString().trim();
            String receiverMeter = receiverMeterInput.getText().toString().trim();
            String unitStr       = unitsInput.getText().toString().trim();

            if (!validateInput(sourceMeter, receiverMeter, unitStr)) return;

            if (!NetworkClient.isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            double unitsToTransfer = Double.parseDouble(unitStr);

            progressBar.setVisibility(View.VISIBLE);
            btnShare.setEnabled(false);

            repository.transferTokens(memberId, receiverMeter, unitsToTransfer,
                    new MeterRepository.RepositoryCallback<Meter>() {
                        @Override
                        public void onSuccess(Meter data) {
                            runOnUiThread(() -> {
                                progressBar.setVisibility(View.GONE);
                                btnShare.setEnabled(true);

                                // Update balance display with sender's new balance
                                balanceDisplay.setText(String.valueOf(data.getSenderNewBalance()));

                                Toast.makeText(Sharetoken_unit.this,
                                        "Transfer successful!\n" +
                                                "Units sent: " + data.getUnitsTransferred() + " TKN\n" +
                                                "New balance: " + data.getSenderNewBalance() + " TKN",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> {
                                progressBar.setVisibility(View.GONE);
                                btnShare.setEnabled(true);
                                Toast.makeText(Sharetoken_unit.this,
                                        "Transfer failed: " + error,
                                        Toast.LENGTH_LONG).show();
                            });
                        }
                    });
        });

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void loadBalance(String meterNumber) {
        if (meterNumber.isEmpty()) return;
        repository.getBalance(meterNumber, new MeterRepository.RepositoryCallback<Integer>() {
            @Override
            public void onSuccess(Integer balance) {
                runOnUiThread(() ->
                        balanceDisplay.setText(String.valueOf(balance)));
            }
            @Override
            public void onError(String error) {
                // silently fail — balance display just stays empty
            }
        });
    }

    private boolean validateInput(String sourceMeter, String receiverMeter, String unitStr) {
        if (sourceMeter.isEmpty() || receiverMeter.isEmpty() || unitStr.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sourceMeter.length() != 11) {
            Toast.makeText(this, "Source meter must be 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (receiverMeter.length() != 11) {
            Toast.makeText(this, "Receiver meter must be 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (sourceMeter.equals(receiverMeter)) {
            Toast.makeText(this, "Cannot transfer to same meter", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double units = Double.parseDouble(unitStr);
            if (units <= 0) {
                Toast.makeText(this, "Units must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid units format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}