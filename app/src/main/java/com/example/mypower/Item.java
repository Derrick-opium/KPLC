package com.example.mypower;

public class Item {

    private String name;   // Must match JSON field from server
    private int value;     // optional, remove if your JSON has only name

    // Default constructor (needed for Retrofit/Gson)
    public Item() {}

    // Getters (Gson uses these to map JSON)
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
