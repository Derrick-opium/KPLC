package com.example.mypower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
public class MainActivity extends AppCompatActivity {
   // ViewPager2 viewPager2;
    FrameLayout txtoken,txtmessage,meter,unit,viw;
    //TextView ;
    DrawerLayout drawerLayout;
    ImageButton buttontoggle;
    //TextView ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //viewPager2=findViewById(R.id.main);
        //viewPager2.setAdapter(new Viewpage(this));
        txtoken=findViewById(R.id.token);
        meter=findViewById(R.id.txtdepo);
        unit=findViewById(R.id.txtshares);
        viw=findViewById(R.id.txtview);
        buttontoggle=findViewById(R.id.btntoggle);
        drawerLayout=findViewById(R.id.drwablelayou);
        txtmessage=findViewById(R.id.messages);

        txtoken.setOnClickListener(v -> {
            Intent myintent=new Intent(MainActivity.this,Tokenpage.class);
            startActivity(myintent);
        });
        txtmessage.setOnClickListener(v -> {
            Intent intent2=new Intent(MainActivity.this,Message.class);
            startActivity(intent2);
        });
        meter.setOnClickListener(v -> {
            Intent myintent=new Intent(MainActivity.this,Deposit_token.class);
            startActivity(myintent);
        });
        unit.setOnClickListener(v -> {
            Intent myintent=new Intent(MainActivity.this,Sharetoken_unit.class);
            startActivity(myintent);
        });
        viw.setOnClickListener(v -> {
            Intent myintent=new Intent(MainActivity.this,View_list.class);
            startActivity(myintent);
        });

        buttontoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
    }



}