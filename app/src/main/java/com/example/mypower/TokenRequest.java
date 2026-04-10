package com.example.mypower;

public class TokenRequest {
    private int token_units;
    private int member_id;

    public TokenRequest(int token_units, int member_id) {
        this.token_units = token_units; // ← was wrongly assigned to meter_number
        this.member_id   = member_id;  // ← was wrongly assigned to token_units
    }

    public int getToken_units() { return token_units; }
    public void setToken_units(int token_units) { this.token_units = token_units; }

    public int getMember_id() { return member_id; }
    public void setMember_id(int member_id) { this.member_id = member_id; }
}