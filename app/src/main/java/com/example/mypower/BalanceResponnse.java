package com.example.mypower;

public class BalanceResponnse {
    private String meter_number;
    private int balance;
    private String message;

    public String getMeter_number() { return meter_number; }
    public void setMeter_number(String meter_number) { this.meter_number = meter_number; }

    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

