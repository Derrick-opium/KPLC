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

public class Login_form extends AppCompatActivity {
    TextView txcrateaccount;
    EditText name,passo;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_form);

        txcrateaccount=findViewById(R.id.crtaccoun);
        name=findViewById(R.id.logname);
        passo=findViewById(R.id.logpassord);
        login=findViewById(R.id.btnlog);


        login.setOnClickListener(v -> {
            String fname=name.getText().toString().trim();
            String password=passo.getText().toString().trim();

            if (fname.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent myintent=new Intent(Login_form.this,MainActivity.class);
                startActivity(myintent);
            }
        });
        txcrateaccount.setOnClickListener(v -> {
            Intent intent1=new Intent(Login_form.this,Signup_form.class);
            startActivity(intent1);
        });

    }
}