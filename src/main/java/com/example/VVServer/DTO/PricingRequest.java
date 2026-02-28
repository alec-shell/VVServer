package com.example.VVServer.DTO;

public class PricingRequest {
    public int id;
    public String token;
    public String secret;

    public PricingRequest() {}

    public PricingRequest(int id, String token, String secret) {
        this.id = id;
        this.token = token;
        this.secret = secret;
    }

} // PricingRequest class
