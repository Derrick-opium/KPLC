package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Deposit_token extends AppCompatActivity {

    EditText mtr, unit;
    Button btndepo, btnback;
    MeterRepository repository;
    SessionManager sessionManager;
    int memberId;
    String meterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_token);

        mtr      = findViewById(R.id.depositmtrnum);
        unit     = findViewById(R.id.tknunit);
        btndepo  = findViewById(R.id.btndepo);
        btnback  = findViewById(R.id.btnbac);

        repository     = new MeterRepository(this);
        sessionManager = new SessionManager(this);

        // Get session data
        memberId    = sessionManager.getMemberId();
        meterNumber = sessionManager.getMeterNumber();

        // Auto fill meter number - read only
        if (meterNumber != null && !meterNumber.isEmpty()) {
            mtr.setText(meterNumber);
            mtr.setFocusable(false);
            mtr.setFocusableInTouchMode(false);
            mtr.setClickable(false);
            mtr.setCursorVisible(false);
        } else {
            // No meter set - redirect to Set Meter screen
            Toast.makeText(this,
                    "Please set your meter number first",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Set_Meter.class));
            finish();
            return;
        }

        btndepo.setOnClickListener(v -> {
            String unitsStr = unit.getText().toString().trim();

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

            if (validateInput(unitsStr)) {
                int units = Integer.parseInt(unitsStr);
                btndepo.setEnabled(false);

                repository.addTokens(units, memberId,
                        new MeterRepository.RepositoryCallback<Meter>() {
                            @Override
                            public void onSuccess(Meter data) {
                                runOnUiThread(() -> {
                                    btndepo.setEnabled(true);
                                    Toast.makeText(Deposit_token.this,
                                            "✅ Tokens added!\nNew balance: " +
                                                    data.getToken_units() + " units",
                                            Toast.LENGTH_LONG).show();
                                    unit.setText("");
                                    finish();
                                });
                            }

                            @Override
                            public void onError(String error) {
                                runOnUiThread(() -> {
                                    btndepo.setEnabled(true);
                                    Toast.makeText(Deposit_token.this,
                                            "❌ " + error,
                                            Toast.LENGTH_LONG).show();
                                });
                            }
                        });
            }
        });

        btnback.setOnClickListener(v -> finish());
    }

    private boolean validateInput(String unitsStr) {
        if (unitsStr.isEmpty()) {
            unit.setError("Enter token units");
            unit.requestFocus();
            return false;
        }
        try {
            int units = Integer.parseInt(unitsStr);
            if (units <= 0) {
                unit.setError("Units must be greater than 0");
                unit.requestFocus();
                return false;
            }
            if (units > 10000) {
                unit.setError("Maximum 10,000 units per transaction");
                unit.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            unit.setError("Invalid units format");
            unit.requestFocus();
            return false;
        }
        return true;
    }
}