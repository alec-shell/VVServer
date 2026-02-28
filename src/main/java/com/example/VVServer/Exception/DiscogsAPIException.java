package com.example.VVServer.Exception;

public class DiscogsAPIException extends RuntimeException {

    private int code;
    private String message;
    private String body;

    public DiscogsAPIException(int code, String message, String body) {
        super(message);
    } // constructor

    public int getCode() {
        return code;
    } // getCode()

    public String getBody() {
        return body;
    } // getBody()

} // DiscogsAPIException class
