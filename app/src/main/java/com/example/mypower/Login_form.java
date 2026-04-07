package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login_form extends AppCompatActivity {

    TextView txcrateaccount;

    EditText name, passo;
    Button login;
    MeterRepository meterRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);

        txcrateaccount = findViewById(R.id.crtaccoun);
        name = findViewById(R.id.logname);
        passo = findViewById(R.id.logpassord);
        login = findViewById(R.id.btnlog);

        meterRepository = new MeterRepository(this);

        login.setOnClickListener(v -> {
            String fname = name.getText().toString().trim();
            String password = passo.getText().toString().trim();


            if (fname.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check network
            if (!NetworkClient.isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            // Disable button to prevent double click
            login.setEnabled(false);
            // progressBar.setVisibility(View.VISIBLE);

            meterRepository.loginUser(fname, password, new MeterRepository.RepositoryCallback<Member>() {

                @Override
                public void onSuccess(Member data) {
                    // progressBar.setVisibility(View.GONE);
                    login.setEnabled(true);

                    Toast.makeText(Login_form.this,
                            "Welcome " + fname + "!",
                            Toast.LENGTH_SHORT).show();

                    // Navigate to MainActivity and clear back stack
                    Intent intent = new Intent(Login_form.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String error) {

                    login.setEnabled(true);

                    // Show specific error messages
                    if (error.contains("User not found")) {
                        Toast.makeText(Login_form.this,
                                "Account not found. Please sign up first.",
                                Toast.LENGTH_LONG).show();
                    } else if (error.contains("Incorrect password")) {
                        Toast.makeText(Login_form.this,
                                "Incorrect password. Please try again.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login_form.this,
                                "Login failed: " + error,
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        });

        txcrateaccount.setOnClickListener(v -> {
            Intent intent1 = new Intent(Login_form.this, Signup_form.class);
            startActivity(intent1);
        });
    }
}