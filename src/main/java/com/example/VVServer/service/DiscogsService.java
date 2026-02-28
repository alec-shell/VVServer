package com.example.VVServer.service;

import com.example.VVServer.client.DiscogsClient;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class DiscogsService {

    private DiscogsClient discogsClient;

    public DiscogsService(DiscogsClient discogsClient) {
        this.discogsClient = discogsClient;
    } // constructor

    public String albumsSearch(String token, String secret, String album, String artist,
                               String year, String catNo, int page)
            throws IOException, ExecutionException, InterruptedException {

        Response response = discogsClient.searchQuery(new OAuth1AccessToken(token, secret),
                album, artist, year, catNo, page
        );

        if (response.getCode() >= 400) {
            return "Discogs API Error:" + response.getCode();
        }
        return response.getBody();
    } // albumsSearch()

    public String pricingSearch(String token, String secret, int id)
    throws IOException, ExecutionException, InterruptedException {
        Response response = discogsClient.getPriceSuggestions(new OAuth1AccessToken(token, secret), id);
        if (response.getCode() >= 400) {
            return "Discogs API Error:" + response.getCode();
        }
        return response.getBody();
    } // pricingSearch()

} // DiscogsService class
