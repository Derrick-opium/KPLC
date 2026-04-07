package com.example.mypower;

import com.google.gson.annotations.SerializedName;

public class Member {

    private int id;

    @SerializedName("full_name")
    private String Username;

    @SerializedName("password")
    private String password;

    public Member(String users, String pass) {
        this.Username = users;
        this.password = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }

    public String retrieveUserInfo() {
        return "Username: " + Username + ", Password: " + password;
    }
}