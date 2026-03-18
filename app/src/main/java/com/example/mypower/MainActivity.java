package com.example.mypower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
   // ViewPager2 viewPager2;
    TextView txtoken,txtmessage;
    DrawerLayout drawerLayout;
    ImageButton buttontoggle;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //viewPager2=findViewById(R.id.main);
        //viewPager2.setAdapter(new Viewpage(this));
        txtoken=findViewById(R.id.token);
        buttontoggle=findViewById(R.id.btntoggle);
        drawerLayout=findViewById(R.id.drwablelayou);
        txtmessage=findViewById(R.id.message);

        txtoken.setOnClickListener(v -> {
            Intent myintent=new Intent(MainActivity.this,Tokenpage.class);
            startActivity(myintent);
        });
        txtmessage.setOnClickListener(v -> {
            Intent intent2=new Intent(MainActivity.this,Message.class);
            startActivity(intent2);
        });

        buttontoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
    }


}