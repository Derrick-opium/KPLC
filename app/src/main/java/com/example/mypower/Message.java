package com.example.mypower;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Message extends AppCompatActivity {

    EditText phone, mess;
    Button btnsend;
    TextView tvCharCount, tvStatus, chipToken, chipPayment, chipBalance, chipMaintenance;

    private String phonenumber, message;

    // ✅ Permission launcher
    private ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    sendsms();
                } else {
                    Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);

        // ✅ Find all views
        phone = findViewById(R.id.ephone);
        mess = findViewById(R.id.emessage);
        btnsend = findViewById(R.id.send);
        tvCharCount = findViewById(R.id.tvCharCount);
        tvStatus = findViewById(R.id.tvStatus);
        chipToken = findViewById(R.id.chipToken);
        chipPayment = findViewById(R.id.chipPayment);
        chipBalance = findViewById(R.id.chipBalance);
        chipMaintenance = findViewById(R.id.chipMaintenance);

        // ✅ Get data from intent (from Tokenpage)
        Intent intent = getIntent();
        String meter = intent.getStringExtra("meter_key");
        String token = intent.getStringExtra("token_key");
        String amount = intent.getStringExtra("amount_key");

        if (meter == null) meter = "";
        if (token == null) token = "";
        if (amount == null) amount = "";

        String fullMessage =
                "MeterModel: " + meter + "\n" +
                        "Amount: " + amount + "\n" +
                        "Units: " + token;

        mess.setText(fullMessage);

        // ✅ Character counter
        mess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();
                tvCharCount.setText(len + " / 160");
                if (len > 140) {
                    tvCharCount.setTextColor(android.graphics.Color.parseColor("#E24B4A"));
                } else {
                    tvCharCount.setTextColor(android.graphics.Color.parseColor("#94A3B8"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // ✅ Quick message chips
        chipToken.setOnClickListener(v -> mess.setText("Your token is ready."));
        chipPayment.setOnClickListener(v -> mess.setText("Payment received. Thank you!"));
        chipBalance.setOnClickListener(v -> mess.setText("Low balance alert. Please recharge."));
        chipMaintenance.setOnClickListener(v -> mess.setText("System maintenance scheduled tonight."));

        // ✅ Send button
        btnsend.setOnClickListener(v -> {
            phonenumber = phone.getText().toString().trim();
            message = mess.getText().toString().trim();

            if (phonenumber.isEmpty()) {
                phone.setError("Please enter a phone number");
                phone.requestFocus();
                return;
            }

            if (message.isEmpty()) {
                mess.setError("Please enter a message");
                mess.requestFocus();
                return;
            }

            checkpermission();
        });

        // ✅ Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    // ✅ Send SMS
    private void sendsms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, message, null, null);
            showStatus("Message sent successfully!", "#E1F5EE", "#0F6E56");
            Toast.makeText(this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();

            // ✅ Clear phone field after sending
            phone.setText("");

        } catch (Exception e) {
            showStatus("Failed to send SMS. Try again.", "#FCEBEB", "#A32D2D");
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // ✅ Check SMS permission
    private void checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            sendsms();
        } else {
            permissionLauncher.launch(Manifest.permission.SEND_SMS);
        }
    }

    // ✅ Show status message
    private void showStatus(String msg, String bgColor, String textColor) {
        tvStatus.setText(msg);
        tvStatus.setBackgroundColor(android.graphics.Color.parseColor(bgColor));
        tvStatus.setTextColor(android.graphics.Color.parseColor(textColor));
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.postDelayed(() -> tvStatus.setVisibility(View.GONE), 3000);
    }
}