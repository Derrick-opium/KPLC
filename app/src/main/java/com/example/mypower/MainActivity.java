package com.example.mypower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    TextView meter,unit,viw;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.1.126:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api =retrofit.create(ApiService.class);

        api.getHello().enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response){
                if (response.isSuccessful() && response.body() !=null){
                    String msg=response.body().getMessage();
                    System.out.println(msg);
                }
            }
            @Override
            public void onFailure(Call<MessageResponse>call,Throwable t){
                t.printStackTrace();
            }
        });
        //viewPager2=findViewById(R.id.main);
        //viewPager2.setAdapter(new Viewpage(this));
        txtoken=findViewById(R.id.token);
        meter=findViewById(R.id.txtdepo);
        unit=findViewById(R.id.txtshare);
        viw=findViewById(R.id.txtview);
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