package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Set_Meter extends AppCompatActivity {

    TextInputEditText etMeterNumber;
    MaterialButton btnSetMeter;
    MeterRepository repository;
    SessionManager sessionManager;
    int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_meter);

        etMeterNumber  = findViewById(R.id.etMeterNumber);
        btnSetMeter    = findViewById(R.id.btnSetMeter);
        repository     = new MeterRepository(this);
        sessionManager = new SessionManager(this);

        // Get logged in member id
        memberId = sessionManager.getMemberId();

        btnSetMeter.setOnClickListener(v -> {
            String meter = etMeterNumber.getText().toString().trim();

            if (meter.isEmpty()) {
                etMeterNumber.setError("Enter meter number");
                etMeterNumber.requestFocus();
                return;
            }
            if (meter.length() != 11) {
                etMeterNumber.setError("Meter number must be 11 digits");
                etMeterNumber.requestFocus();
                return;
            }
            if (memberId == 0) {
                Toast.makeText(this, "Session expired, please login again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!NetworkClient.isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet connection",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            btnSetMeter.setEnabled(false);
            setMeterNumber(meter);
        });
    }

    private void setMeterNumber(String meterNumber) {
        repository.setMeter(memberId, meterNumber, new MeterRepository.RepositoryCallback<String>() {
            @Override
            public void onSuccess(String data) {
                runOnUiThread(() -> {
                    // Save meter number to session
                    sessionManager.saveMeterNumber(meterNumber);

                    Toast.makeText(Set_Meter.this,
                            "✅ Meter number set successfully!",
                            Toast.LENGTH_LONG).show();

                    // Go to MainActivity
                    Intent intent = new Intent(Set_Meter.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    btnSetMeter.setEnabled(true);
                    Toast.makeText(Set_Meter.this,
                            "❌ " + error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}