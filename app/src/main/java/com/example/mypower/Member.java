package com.example.mypower;

import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("id")  // ← add this so Gson maps id from server
    private int id;

    @SerializedName("full_name")
    private String Username;

    @SerializedName("password")
    private String password;

    public Member(String users, String pass) {
        this.Username = users;
        this.password = pass;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return Username; }
    public void setUsername(String username) { this.Username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String retrieveUserInfo() {
        return "Username: " + Username + ", Password: " + password;
    }
}