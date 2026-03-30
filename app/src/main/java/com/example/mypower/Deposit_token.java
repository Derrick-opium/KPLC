package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Deposit_token extends AppCompatActivity {
    EditText mtr,unit;
    Button btndepo,btnback;
    Dbhelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deposit_token);
        mtr=findViewById(R.id.depositmtrnum);
        unit=findViewById(R.id.tknunit);
        btndepo=findViewById(R.id.btndepo);
        btnback=findViewById(R.id.btnbac);


        helper=new Dbhelper(Deposit_token.this);

        btndepo.setOnClickListener(v -> {
            String meter=mtr.getText().toString().trim();
            String token=unit.getText().toString().trim();

            if (meter.isEmpty() || token.isEmpty()){
                Toast.makeText(this, "fill all spaces", Toast.LENGTH_SHORT).show();
                return;
            }

            if (meter .length()!=11){
                Toast.makeText(this, "Meter number must be 11 digits ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (helper.checkuser(meter)){
                boolean updated = helper.updateMeterBalance(token, meter);
                if (updated) {
                    Toast.makeText(this, "Token updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Meter not found", Toast.LENGTH_SHORT).show();
                }

                return;
            }

            boolean inserted=helper.insertdata(meter,token);
            if (inserted){
                Toast.makeText(getApplicationContext(), "user registered sucessfull", Toast.LENGTH_SHORT).show();
                Intent myintent=new Intent(Deposit_token.this,Sharetoken_unit.class);
                startActivity(myintent);
                finish();
            }else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }

        });

        btnback.setOnClickListener(v -> {
            Intent intent=new Intent(Deposit_token.this,MainActivity.class);
            startActivity(intent);
        });


    }
}