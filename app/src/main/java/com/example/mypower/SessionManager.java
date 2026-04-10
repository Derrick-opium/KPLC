package com.example.mypower;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME     = "user_session";
    private static final String KEY_LOGGED    = "isLoggedIn";
    private static final String KEY_USERNAME  = "username";
    private static final String KEY_MEMBER_ID = "member_id";
    private static final String KEY_METER     = "meter_number"; // ← add this

    public SessionManager(Context context) {
        this.context = context;
        prefs  = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createSession(String username, int memberId) {
        editor.putBoolean(KEY_LOGGED, true);
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_MEMBER_ID, memberId);
        editor.apply();
    }

    // ← add this
    public void saveMeterNumber(String meterNumber) {
        editor.putString(KEY_METER, meterNumber);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED, false);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public int getMemberId() {
        return prefs.getInt(KEY_MEMBER_ID, 0);
    }

    // ← add this
    public String getMeterNumber() {
        return prefs.getString(KEY_METER, null);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}