package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.ProgressBar;

public class View_list extends AppCompatActivity {
    Button btnview, btnback;
    ListView listVi;
    //ProgressBar progressBar;
    MeterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        btnview = findViewById(R.id.btnlis);
        btnback = findViewById(R.id.btnbck);
        listVi = findViewById(R.id.ls1);
       // progressBar = findViewById(R.id.progressBar);

        repository = new MeterRepository(this);

        btnview.setOnClickListener(v -> loadAllMeters());
        btnback.setOnClickListener(v -> {
            startActivity(new Intent(View_list.this, MainActivity.class));
        });
    }

    private void loadAllMeters() {
        if (!NetworkClient.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }



        repository.getAllMeters(new MeterRepository.RepositoryCallback<List<Meter>>() {
            @Override
            public void onSuccess(List<Meter> data) {

                if (data == null || data.isEmpty()) {
                    Toast.makeText(View_list.this, "No meters found", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> meterList = new ArrayList<>();
                for (Meter meter : data) {
                    meterList.add(
                            "Meter: " + meter.getMeter_number() + "\n" +
                                    "Balance: " + meter.getToken_units() + " units\n" +
                                    "Delivered: " + meter.getDelivered_units() + " units\n" +
                                    "Updated: " + meter.getUpdated_at()
                    );
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        View_list.this,
                        android.R.layout.simple_list_item_1,
                        meterList
                );
                listVi.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(View_list.this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
