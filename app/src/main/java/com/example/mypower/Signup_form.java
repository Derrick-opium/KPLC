package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup_form extends AppCompatActivity {
    TextView txtlogin;
    EditText name,password;
    Button btnsingup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_form);

        txtlogin=findViewById(R.id.log);
        name=findViewById(R.id.edname);
        password=findViewById(R.id.edpassword);
        btnsingup=findViewById(R.id.btnsign);


        btnsingup.setOnClickListener(v -> {
            String snname=name.getText().toString().trim();
            String sspass=password.getText().toString().trim();

            if (snname.isEmpty() || sspass.isEmpty()){
                Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent myinten=new Intent(Signup_form.this,Login_form.class);
                startActivity(myinten);
            }
        });

        txtlogin.setOnClickListener(v -> {
            Intent intex=new Intent(Signup_form.this,Login_form.class);
            startActivity(intex);
        });

    }
}