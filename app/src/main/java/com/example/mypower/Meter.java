package com.example.mypower;

public class Meter {
    private int id;
    private String meter_number;
    private int token_units;
    private int delivered_units;
    private String created_at;
    private String updated_at;

    public Meter() {

    }

    public Meter(String meter_number, int token_units) {
        this.meter_number = meter_number;
        this.token_units = token_units;
        this.delivered_units = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMeter_number() {
        return meter_number;
    }
    public void setMeter_number(String meter_number) { this.meter_number = meter_number; }

    public int getToken_units() {
        return token_units;
    }
    public void setToken_units(int token_units) { this.token_units = token_units; }

    public int getDelivered_units() { return delivered_units; }
    public void setDelivered_units(int delivered_units) { this.delivered_units = delivered_units; }

    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
