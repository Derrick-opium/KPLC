package com.example.mypower;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    // ✅ Define constants once
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // ✅ Single createSession - username only (no email)
    public void createSession(String username) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // ✅ Single getUsername
    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    // Clear session on logout
    public void logout() {
        editor.clear();
        editor.apply();
    }
}