package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Loading extends AppCompatActivity {

    private ProgressBar progressBar;
    private  int progressiveStatus=0;

    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);


        progressBar=findViewById(R.id.progets);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressiveStatus<200){
                    progressiveStatus+=2;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setProgress(progressiveStatus);
                        }
                    });
                    try {
                        Thread.sleep(300);//delay
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
                Intent intent=new Intent(Loading.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                finish();
            }
        }).start();

    }
}