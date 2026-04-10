package com.example.mypower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    FrameLayout txtoken, txtmessage, meter, unit, viw,stmtr;
    DrawerLayout drawerLayout;
    ImageButton buttontoggle;
    TextView tvUsername;
    SessionManager sessionManager;
    NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sessionManager = new SessionManager(this);

        // ✅ Session check - redirect to login if not logged in
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, Login_form.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // ✅ Find all views FIRST
        txtoken = findViewById(R.id.token);
        stmtr=findViewById(R.id.mtrset);
        meter = findViewById(R.id.txtdepo);
        unit = findViewById(R.id.txtshares);
        viw = findViewById(R.id.txtview);
        buttontoggle = findViewById(R.id.btntoggle);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drwablelayou);
        txtmessage = findViewById(R.id.messages);

        // ✅ Set username in drawer header
        String username = sessionManager.getUsername();
        View headerView = navigationView.getHeaderView(0);
        tvUsername = headerView.findViewById(R.id.tvUsername);
        if (tvUsername != null) {
            tvUsername.setText(username != null && !username.isEmpty() ? username : "Guest");
        }

        // ✅ Back press handler - AFTER findViewById
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Exit App")
                            .setMessage("Are you sure you want to exit?")
                            .setPositiveButton("Yes", (dialog, which) -> finish())
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });

        // ✅ Click listeners
        txtoken.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, Tokenpage.class);
            startActivity(myintent);
        });

        txtmessage.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, Message.class);
            startActivity(intent2);
        });

        meter.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, Deposit_token.class);
            startActivity(myintent);
        });

        stmtr.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, Set_Meter.class);
            startActivity(myintent);
        });

        unit.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, Sharetoken_unit.class);
            startActivity(myintent);
        });

        viw.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, View_list.class);
            startActivity(myintent);
        });

        buttontoggle.setOnClickListener(v -> drawerLayout.open());

        android.util.Log.d("DRAWER", "NavigationView found: " + (navigationView != null));
        android.util.Log.d("DRAWER", "DrawerLayout found: " + (drawerLayout != null));

        // ✅ Navigation drawer item clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            android.util.Log.d("DRAWER", "Item clicked: " + item.getTitle());

            if (id == R.id.logout) {
                android.util.Log.d("DRAWER", "Logout matched!");
                signOutUser();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // ✅ Sign out method
    private void signOutUser() {
        android.util.Log.d("LOGOUT", "signOutUser() called");

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    android.util.Log.d("LOGOUT", "Yes clicked");
                    sessionManager.logout();
                    android.util.Log.d("LOGOUT", "isLoggedIn after logout: " + sessionManager.isLoggedIn());
                    Intent intent = new Intent(MainActivity.this, Login_form.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}