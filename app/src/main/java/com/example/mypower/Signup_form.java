package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Signup_form extends AppCompatActivity {

    TextView txtlogin;
    EditText name, password;
    Button btnsingup;
    MeterRepository signin;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_form);

        txtlogin = findViewById(R.id.log);
        name = findViewById(R.id.edname);
        password = findViewById(R.id.edpassword);
        btnsingup = findViewById(R.id.btnsign);

        signin = new MeterRepository(this);
        sessionManager = new SessionManager(this);



        // If already logged in, skip login screen
        if (sessionManager.isLoggedIn()) {
            goToMain();
            return;
        }

        btnsingup.setOnClickListener(v -> {
            String snname = name.getText().toString().trim();
            String sspass = password.getText().toString().trim();


            if (snname.isEmpty() || sspass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (NetworkClient.isNetworkAvailable(this)) {
                signin.registerUser(snname, sspass, new MeterRepository.RepositoryCallback<Member>() {

                    @Override
                    public void onSuccess(Member data) {

                        Toast.makeText(Signup_form.this,
                                "User registered successfully!",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Signup_form.this, Login_form.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(Signup_form.this,
                                "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        txtlogin.setOnClickListener(v -> {
            Intent intex = new Intent(Signup_form.this, Login_form.class);
            startActivity(intex);
        });
    }
    private void goToMain() {
        Intent intent = new Intent(Signup_form.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}