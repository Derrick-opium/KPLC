package com.example.mypower;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_list extends AppCompatActivity {

    Button btnview, btnback;
    ListView listVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view);

        btnview = findViewById(R.id.btnlis);
        btnback = findViewById(R.id.btnbck);
        listVi = findViewById(R.id.ls1);

        // Button to fetch message from server
        btnview.setOnClickListener(v -> fetchMessage());

        // Button to go back to MainActivity
        btnback.setOnClickListener(v -> {
            Intent myIntent = new Intent(View_list.this, MainActivity.class);
            startActivity(myIntent);
        });
    }

    private void fetchMessage() {
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.126:3000/") // replace with your server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);

        // Call server
        api.getMessage().enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extract value from JSON { "message": "value" }
                    String value = response.body().getMessage();

                    // Display value in ListView
                    List<String> list = new ArrayList<>();
                    list.add(value);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            View_list.this,
                            android.R.layout.simple_list_item_1,
                            list
                    );
                    listVi.setAdapter(adapter);

                } else {
                    Toast.makeText(View_list.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(View_list.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
        /*

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),((v, insets) -> {
            Insets systemBars=insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
            return insets;
        }));

        btnview.setOnClickListener(v -> loadUsers());

    }

    private void loadUsers(){
        Cursor cursor=help.getAllusrers();

        if (cursor.getCount() ==0){
            Toast.makeText(this, "no user found", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }



        ArrayList<String> userlist=new ArrayList<>();
        while (cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String meter=cursor.getString(cursor.getColumnIndexOrThrow("meter"));
            String Unit=cursor.getString(cursor.getColumnIndexOrThrow("unit"));
            String Deliver=cursor.getString(cursor.getColumnIndexOrThrow("deliver"));

            int balanc=help.getBalance(Unit);
            userlist.add(
                    "ID: " + id +
                            "\nMeter: " + meter +
                            "\nUnits: " + Unit +
                            "\nDelivered: " + Deliver
            );
        }
        cursor.close();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,userlist);
        listVi.setAdapter(adapter);

         */



