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
   // ProgressBar progressBar;
    MeterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_token);

        mtr = findViewById(R.id.depositmtrnum);
        unit = findViewById(R.id.tknunit);
        btndepo = findViewById(R.id.btndepo);
        btnback = findViewById(R.id.btnbac);
        //progressBar = findViewById(R.id.progressBar);

        repository = new MeterRepository(this);

        btndepo.setOnClickListener(v -> {
            String meter = mtr.getText().toString().trim();
            String unitsStr = unit.getText().toString().trim();

            if (validateInput(meter, unitsStr)) {
                int units = Integer.parseInt(unitsStr);
                //double amount = units * 20; // 20 KSH per unit

               // progressBar.setVisibility(View.VISIBLE);

                // Check network first
                if (NetworkClient.isNetworkAvailable(this)) {
                    repository.addTokens(meter, units, new MeterRepository.RepositoryCallback<Meter>() {
                        @Override
                        public void onSuccess(Meter data) {
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(Deposit_token.this,
                                    "Tokens added successfully!\nNew balance: " +
                                            data.getToken_units() + " units",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onError(String error) {
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(Deposit_token.this,
                                    "Error: " + error, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnback.setOnClickListener(v -> {
            startActivity(new Intent(Deposit_token.this, MainActivity.class));
        });
    }

    private boolean validateInput(String meter, String unitsStr) {
        if (meter.isEmpty() || unitsStr.isEmpty()) {
            Toast.makeText(this, "Fill all spaces", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (meter.length() != 11) {
            Toast.makeText(this, "Meter number must be 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int units = Integer.parseInt(unitsStr);
            if (units <= 0) {
                Toast.makeText(this, "Units must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (units > 10000) {
                Toast.makeText(this, "Maximum 10,000 units per transaction", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid units format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
