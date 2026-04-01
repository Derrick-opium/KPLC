package com.example.mypower;

public class TokenRequest {
    private String meter_number;
    private int units;
    private double amount;

    public TokenRequest(String meter_number, int units) {
        this.meter_number = meter_number;
        this.units = units;
        //this.amount = amount;
    }

    public String getMeter_number() { return meter_number; }
    public void setMeter_number(String meter_number) { this.meter_number = meter_number; }

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}

