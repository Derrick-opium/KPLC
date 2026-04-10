package com.example.mypower;

import com.google.gson.annotations.SerializedName;

public class TransferRequest {

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("receiver_meter_number")
    private String receiverMeterNumber;

    @SerializedName("units")
    private double units;

    public TransferRequest(int memberId, String receiverMeterNumber, double units) {
        this.memberId = memberId;
        this.receiverMeterNumber = receiverMeterNumber;
        this.units = units;
    }

    public int getMemberId() {
        return memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getReceiverMeterNumber() {
        return receiverMeterNumber;
    }
    public void setReceiverMeterNumber(String receiverMeterNumber) {
        this.receiverMeterNumber = receiverMeterNumber;
    }

    public double getUnits() {
        return units;
    }
    public void setUnits(double units) {
        this.units = units;
    }
}