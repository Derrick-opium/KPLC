package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
public class Sharetoken_unit extends AppCompatActivity {
    EditText meter, token, deliver;
    Button btnshare, btnback;
    //ProgressBar progressBar;
    MeterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharetoken_unit);

        meter = findViewById(R.id.mtrnum);
        token = findViewById(R.id.tknunit);
        deliver = findViewById(R.id.delivery);
        btnshare = findViewById(R.id.btnshare);
        btnback = findViewById(R.id.btnbac);
        //progressBar = findViewById(R.id.progressBar);

        repository = new MeterRepository(this);

        btnshare.setOnClickListener(v -> {
            String metr = meter.getText().toString().trim();
            String unitStr = token.getText().toString().trim();
            String tuma = deliver.getText().toString().trim();

            if (validateInput(metr, unitStr, tuma)) {
                int unitsToTransfer = Integer.parseInt(unitStr);

                //progressBar.setVisibility(View.VISIBLE);

                if (NetworkClient.isNetworkAvailable(this)) {
                    // Transfer tokens between meters
                    repository.transferTokens(metr, tuma, unitsToTransfer,
                            new MeterRepository.RepositoryCallback<Meter>() {
                                @Override
                                public void onSuccess(Meter data) {
                                   // progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Sharetoken_unit.this,
                                            "Transfer successful!\n" + unitsToTransfer +
                                                    " units transferred to meter: " + tuma,
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                @Override
                                public void onError(String error) {
                                    //progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Sharetoken_unit.this,
                                            "Transfer failed: " + error, Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                   // progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnback.setOnClickListener(v -> {
            startActivity(new Intent(Sharetoken_unit.this, MainActivity.class));
        });
    }

    private boolean validateInput(String metr, String unitStr, String tuma) {
        if (metr.isEmpty() || unitStr.isEmpty() || tuma.isEmpty()) {
            Toast.makeText(this, "Fill all spaces", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (metr.length() != 11) {
            Toast.makeText(this, "Source meter must be 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tuma.length() != 11) {
            Toast.makeText(this, "Destination meter must be 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (metr.equals(tuma)) {
            Toast.makeText(this, "Cannot transfer to same meter", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int units = Integer.parseInt(unitStr);
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
