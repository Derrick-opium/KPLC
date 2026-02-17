package com.example.mypower;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button ok,clr;
    EditText meter,num,unit,cash,mnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ok=findViewById(R.id.btnk);
        clr=findViewById(R.id.btncl);
        meter=findViewById(R.id.num);
        num=findViewById(R.id.amount);
        unit=findViewById(R.id.csh);
        cash=findViewById(R.id.tkn);
        mnu=findViewById(R.id.mtr);

        ok.setOnClickListener(v -> {
            String getmoney=num.getText().toString().trim();
            String getmeter=meter.getText().toString().trim();
            if (getmoney.isBlank()){
                Toast.makeText(this, "fill in the spaces", Toast.LENGTH_SHORT).show();
                return;
            }
            double mio=Integer.parseInt(getmoney);
            if (mio <30){
                Toast.makeText(this, "min amount is 30", Toast.LENGTH_SHORT).show();
            }
            else {
                double token=mio/20;
                cash.setText(token +":units");
                unit.setText("ksh:"+getmoney);
                mnu.setText(getmeter);

            }
        });
        clr.setOnClickListener(v -> {
            mnu.setText("");
            unit.setText("");
            cash.setText("");

        });
    }
}