package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Sharetoken_unit extends AppCompatActivity {
    EditText meter,token,deliver,bala;
    Button btnshare,btnback;
    Dbhelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sharetoken_unit);
        meter=findViewById(R.id.mtrnum);
        token=findViewById(R.id.tknunit);
        deliver=findViewById(R.id.delivery);
        btnshare=findViewById(R.id.btnshare);
        btnback=findViewById(R.id.btnbac);

        dbhelper=new Dbhelper(Sharetoken_unit.this);


        btnshare.setOnClickListener(v -> {
            String metr=meter.getText().toString().trim();
            String unit=token.getText().toString().trim();
            String tuma=deliver.getText().toString().trim();

            if (metr.isEmpty() || unit.isEmpty()){
                Toast.makeText(this, "fill all spaces", Toast.LENGTH_SHORT).show();
            }

            else {

                boolean inert=dbhelper.checkUserPass(metr);
                if (inert){

                    Toast.makeText(this, "Meter succesfull", Toast.LENGTH_SHORT).show();
                    Intent inten=new Intent(Sharetoken_unit.this, View_list.class);
                    startActivity(inten);
                }
                else {
                    Toast.makeText(this, "error loading meter num", Toast.LENGTH_SHORT).show();
                }
                if (metr .length()!=11){
                    Toast.makeText(this, "Meter number must be 11 digits ", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean send=dbhelper.updateDeliver(tuma,metr);
                if (send){
                    Toast.makeText(this, "saved sucessfull", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(this, "error saving", Toast.LENGTH_SHORT).show();
                }
            }


        });

        btnback.setOnClickListener(v -> {
            Intent myintent=new Intent(Sharetoken_unit.this,MainActivity.class);
            startActivity(myintent);
        });


    }
}