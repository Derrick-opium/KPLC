package com.example.mypower;

import com.google.gson.annotations.SerializedName;

public class Meter {
    private int id;

    @SerializedName("meter_number")
    private String meter_number;

    @SerializedName("token_units")
    private double token_units;

    @SerializedName("delivered_units")
    private double delivered_units;

    @SerializedName("sender_meter")
    private String senderMeter;

    @SerializedName("receiver_meter")
    private String receiverMeter;

    @SerializedName("units_transferred")
    private double unitsTransferred;

    @SerializedName("sender_new_balance")
    private double senderNewBalance;

    @SerializedName("receiver_new_balance")
    private double receiverNewBalance;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    public Meter() {}

    public Meter(String meter_number, int token_units) {
        this.meter_number = meter_number;
        this.token_units = token_units;
        this.delivered_units = 0;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMeter_number() {
        return meter_number;
    }
    public void setMeter_number(String meter_number) {
        this.meter_number = meter_number;
    }

    public double getToken_units() {
        return token_units;
    }
    public void setToken_units(double token_units) {
        this.token_units = token_units;
    }

    public double getDelivered_units() {
        return delivered_units;
    }
    public void setDelivered_units(double delivered_units) {
        this.delivered_units = delivered_units;
    }

    public String getSenderMeter() { return senderMeter; }
    public void setSenderMeter(String senderMeter) {
        this.senderMeter = senderMeter;
    }

    public String getReceiverMeter() {
        return receiverMeter;
    }
    public void setReceiverMeter(String receiverMeter) { this.receiverMeter = receiverMeter; }

    public double getUnitsTransferred() {
        return unitsTransferred;
    }
    public void setUnitsTransferred(double unitsTransferred) {
        this.unitsTransferred = unitsTransferred;
    }

    public double getSenderNewBalance() {
        return senderNewBalance;
    }
    public void setSenderNewBalance(double senderNewBalance) {
        this.senderNewBalance = senderNewBalance;
    }

    public double getReceiverNewBalance() {
        return receiverNewBalance;
    }
    public void setReceiverNewBalance(double receiverNewBalance) {
        this.receiverNewBalance = receiverNewBalance;
    }

    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}