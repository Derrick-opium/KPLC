package com.example.mypower;

public class TransferRequest {
    private String from_meter;
    private String to_meter;
    private int units;

    public TransferRequest(String from_meter, String to_meter, int units) {
        this.from_meter = from_meter;
        this.to_meter = to_meter;
        this.units = units;
    }

    public String getFrom_meter() { return from_meter; }
    public void setFrom_meter(String from_meter) { this.from_meter = from_meter; }

    public String getTo_meter() { return to_meter; }
    public void setTo_meter(String to_meter) { this.to_meter = to_meter; }

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
}

