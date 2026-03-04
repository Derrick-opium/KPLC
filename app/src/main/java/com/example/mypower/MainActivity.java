package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
   // ViewPager2 viewPager2;
    TextView txtoken,txtmessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //viewPager2=findViewById(R.id.main);
        //viewPager2.setAdapter(new Viewpage(this));
        txtoken=findViewById(R.id.token);
        txtmessage=findViewById(R.id.message);

        txtoken.setOnClickListener(v -> {
            Intent myintent=new Intent(MainActivity.this,Tokenpage.class);
            startActivity(myintent);
        });
        txtmessage.setOnClickListener(v -> {
            Intent intent2=new Intent(MainActivity.this,Message.class);
            startActivity(intent2);
        });
    }


}