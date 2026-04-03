package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Loading extends AppCompatActivity {

    TextView tvText,tvPercent;

    private ProgressBar progressBar;
    private  int progressiveStatus=0;

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);
        tvText=findViewById(R.id.tvLoadingText);
        tvPercent=findViewById(R.id.tvPercent);

        progressBar=findViewById(R.id.progets);

        int[] progress = {0};
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                progress[0] += 5;
                progressBar.setProgress(progress[0]);
                tvPercent.setText("  " + progress[0] + "%");

                if (progress[0] < 50){
                    tvText.setText("Loading assets...");
                }
                else if (progress[0] < 90){
                    tvText.setText("Almost there...");
                }
                else {
                    tvText.setText("Done!");
                }

                if (progress[0] < 100) {
                    handler.postDelayed(this, 3000); // repeat,delay
                } else {

                    Intent intent = new Intent(Loading.this, Signup_form.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        };
        handler.post(runnable);

    }

}