package com.example.VVServer.client;


import com.example.VVServer.oauth.DiscogsAuthorization;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DiscogsClient {

    private DiscogsAuthorization discogsAuth;

    public DiscogsClient(DiscogsAuthorization discogsAuth) {
        this.discogsAuth = discogsAuth;
    }

    public Response searchQuery(OAuth1AccessToken token, String album, String artist,
                                       String year, String catNo, int pageNo)
            throws IOException, ExecutionException, InterruptedException {
        StringBuilder query = new StringBuilder();
        query.append("https://api.discogs.com/database/search?format=Vinyl&page=");
        query.append(pageNo);
        if (album != null) query.append("&release_title=").append(album.strip().replace(" ", "+"));
        if (artist != null) query.append("&artist=").append(artist.strip().replace(" ", "+"));
        if (year != null) query.append("&year=").append(year.strip());
        if (catNo != null) query.append("&catno=").append(catNo.strip().replace(" ", "+"));

        OAuthRequest request =  new OAuthRequest(Verb.GET, query.toString());
        discogsAuth.getService().signRequest(token, request);
        return discogsAuth.getService().execute(request);
    } // searchQuery()

    public Response getPriceSuggestions(OAuth1AccessToken token, int id)
            throws IOException, ExecutionException, InterruptedException {
        StringBuilder query = new StringBuilder();
        query.append("https://api.discogs.com/marketplace/price_suggestions/");
        query.append(id);
        OAuthRequest request =  new OAuthRequest(Verb.GET, query.toString());
        discogsAuth.getService().signRequest(token, request);
        return discogsAuth.getService().execute(request);
    } // getPriceSuggestions()

    public Response getIdentity(OAuth1AccessToken token) throws IOException, ExecutionException, InterruptedException {
        String queryUrl = "https://api.discogs.com/oauth/identity";
        OAuthRequest request =  new OAuthRequest(Verb.GET, queryUrl);
        discogsAuth.getService().signRequest(token, request);
        return discogsAuth.getService().execute(request);
    } // getIdentity()

} // DiscogsClient class
