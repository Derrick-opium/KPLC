package com.example.mypower;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Message extends AppCompatActivity {


    EditText phone,mess;
    Button btnsend;
    private String phonenumber,message;

    private ActivityResultLauncher<String> permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
        if (isGranted){
            sendsms();

        }else {
            Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
        }
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);


        phone=findViewById(R.id.ephone);
        mess=findViewById(R.id.emessage);
        btnsend=findViewById(R.id.send);

        Intent intent = getIntent();//imports methods from tokenpage

        String meter = intent.getStringExtra("meter_key");
        String token = intent.getStringExtra("token_key");
        String amount = intent.getStringExtra("amount_key");

        if (meter == null) meter = "";
        if (token == null) token = "";
        if (amount == null) amount = "";

        String fullMessage =
                "Meter: " + meter + "\n" +
                        "Amount: " + amount + "\n" +
                        "Units: " + token;

        mess.setText(fullMessage);

        btnsend.setOnClickListener(v -> {
            phonenumber=phone.getText().toString().trim();
            message=mess.getText().toString().trim();

            if (phonenumber.isEmpty()){
                Toast.makeText(this, "please enter phone", Toast.LENGTH_SHORT).show();
                return;
            }
            checkpermission();

        });
    }

    private void sendsms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, message, null, null);
            Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Failed to send sms", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void checkpermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
            sendsms();
        }else {
            permissionLauncher.launch(Manifest.permission.SEND_SMS);
        }


    }

}