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

public class Tokenpage extends AppCompatActivity {

    Button ok, clr,b1bac,btnmessage;
    EditText meter, num, unit, cash, mnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tokenpage);


        ok = findViewById(R.id.btnk);
        b1bac = findViewById(R.id.btnbac);
        clr = findViewById(R.id.btncl);
        meter =findViewById(R.id.num);
        num = findViewById(R.id.amount);
        unit =findViewById(R.id.csh);
        cash = findViewById(R.id.tkn);
        mnu =findViewById(R.id.mtr);
        btnmessage=findViewById(R.id.btnmess);

        ok.setOnClickListener(v -> {

            String getmoney = num.getText().toString().trim();
            String getmeter = meter.getText().toString().trim();

            if (getmoney.isEmpty()) {
                Toast.makeText(this, "Fill all spaces", Toast.LENGTH_SHORT).show();
                return;
            }
            if (getmeter .length()!=11){
                Toast.makeText(this, "Meter number must be 11 digits ", Toast.LENGTH_SHORT).show();
                return;
            }

            double mio = Double.parseDouble(getmoney);

            if (mio < 30) {
                Toast.makeText(this, "Minimum amount is 30", Toast.LENGTH_SHORT).show();
            } else {
                double token = mio / 20;

                cash.setText(token + " units");
                unit.setText("ksh: " + getmoney);
                mnu.setText(getmeter);
            }

        });
        b1bac.setOnClickListener(v -> {
            Intent myintent=new Intent(Tokenpage.this,MainActivity.class);
            startActivity(myintent);
        });
        //exports tokenpage methods  
        btnmessage.setOnClickListener(v -> {
            Intent intent = new Intent(Tokenpage.this, Message.class);

            intent.putExtra("meter_key", mnu.getText().toString());
            intent.putExtra("token_key", cash.getText().toString());
            intent.putExtra("amount_key", unit.getText().toString());

            startActivity(intent);
        });

        clr.setOnClickListener(v -> {
            mnu.setText("");
            unit.setText("");
            cash.setText("");
        });



    }
}