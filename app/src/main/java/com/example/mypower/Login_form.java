package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login_form extends AppCompatActivity {

    TextView txcrateaccount;
    EditText name, passo;
    Button login;
    MeterRepository meterRepository;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);

        sessionManager = new SessionManager(this);

        // ✅ Clear any corrupt/stuck session
        if (sessionManager.isLoggedIn()) {
            if (sessionManager.getUsername() != null && !sessionManager.getUsername().isEmpty()) {
                goToMain();
                return;
            } else {
                // ✅ Corrupt session - clear it and stay on login
                sessionManager.logout();
            }
        }

        txcrateaccount = findViewById(R.id.crtaccoun);
        name = findViewById(R.id.logname);
        passo = findViewById(R.id.logpassord);
        login = findViewById(R.id.btnlog);

        meterRepository = new MeterRepository(this);

        login.setOnClickListener(v -> {
            String fname = name.getText().toString().trim();
            String password = passo.getText().toString().trim();

            android.util.Log.d("LOGIN", "Button clicked");
            android.util.Log.d("LOGIN", "Username: " + fname);

            if (fname.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!NetworkClient.isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fname.length() < 3) {
                name.setError("Username must be at least 3 characters");
                name.requestFocus();
                return;
            }

            if (password.length() < 6) {
                passo.setError("Password must be at least 6 characters");
                passo.requestFocus();
                return;
            }

            login.setEnabled(false);

            meterRepository.loginUser(fname, password, new MeterRepository.RepositoryCallback<Member>() {

                @Override
                public void onSuccess(Member data) {
                    android.util.Log.d("LOGIN", "onSuccess: " + data.getUsername());
                    runOnUiThread(() -> {
                        login.setEnabled(true);
                        sessionManager.createSession(data.getUsername());
                        Toast.makeText(Login_form.this,
                                "Welcome " + data.getUsername() + "!",
                                Toast.LENGTH_SHORT).show();
                        goToMain();
                    });
                }

                @Override
                public void onError(String error) {
                    android.util.Log.e("LOGIN", "onError: " + error);
                    runOnUiThread(() -> {
                        login.setEnabled(true);

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
                    });
                }
            });
        });

        txcrateaccount.setOnClickListener(v -> {
            Intent intent1 = new Intent(Login_form.this, Signup_form.class);
            startActivity(intent1);
        });
    }

    private void goToMain() {
        Intent intent = new Intent(Login_form.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}