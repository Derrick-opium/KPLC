package com.example.mypower;

import com.google.gson.annotations.SerializedName;

public class Recievemtr {

    @SerializedName("member_id")
    private int memberId;

    @SerializedName("meter_number")
    private String meterNumber;

    public Recievemtr(int memberId, String meterNumber) {
        this.memberId = memberId;
        this.meterNumber = meterNumber;
    }

    public int getMemberId() { return memberId; }
    public String getMeterNumber() { return meterNumber; }
}