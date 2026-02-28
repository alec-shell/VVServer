package com.example.VVServer.oauth;


import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DiscogsAuthorization {
    private OAuth10aService service;


    public DiscogsAuthorization(String key, String secret) {
        this.service = new ServiceBuilder(key)
                .apiSecret(secret)
                .build(ScribeDiscogsAPI.getInstance());
    } // constructor

    public OAuth10aService getService() {
        return service;
    } // getService()

    public OAuth1RequestToken getRequestToken() {
        try {
            return service.getRequestToken();
        } catch (IOException | InterruptedException | ExecutionException e) {
            return null;
        }
    } // getRequestToken()

    public OAuth1AccessToken generateAccessToken(OAuth1RequestToken requestToken, String verifier) {
        try {
            return service.getAccessToken(requestToken, verifier);
        } catch (IOException | InterruptedException | ExecutionException e) {
            return null;
        }
    } // generateAccessToken()

} // DiscogsAuthorization class
