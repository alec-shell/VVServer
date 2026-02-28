package com.example.VVServer.controller;

import com.example.VVServer.DTO.AuthTokenRequest;
import com.example.VVServer.DTO.VerifierURLRequest;
import com.example.VVServer.oauth.DiscogsAuthorization;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private DiscogsAuthorization discogsAuth;

    public AuthorizationController(DiscogsAuthorization discogsAuth) {
        this.discogsAuth = discogsAuth;
    }

    @GetMapping("/r_tkn")
    public Map<String, String> getRequestToken() {
        Map<String, String> components = new HashMap<>();
        OAuth1RequestToken requestToken = discogsAuth.getRequestToken();
        components.put("token", requestToken.getToken());
        components.put("secret", requestToken.getTokenSecret());
        return components;
    } // getRequestToken()

    @PostMapping("/url")
    public String getVerifierURL(@RequestBody VerifierURLRequest request) {
        OAuth1RequestToken requestToken = new  OAuth1RequestToken(request.token, request.secret);
        return discogsAuth.getService().getAuthorizationUrl(requestToken);
    } // getVerifierURL()

    @PostMapping("auth_tkn")
    public Map<String, String> getAuthorizationToken(@RequestBody AuthTokenRequest request) {
        OAuth1RequestToken requestToken = new OAuth1RequestToken(request.token, request.secret);
        OAuth1AccessToken accessToken = discogsAuth.generateAccessToken(requestToken, request.verifier);
        Map<String, String> components = new HashMap<>();
        components.put("token", accessToken.getToken());
        components.put("secret", accessToken.getTokenSecret());
        return components;
    } // getAuthorizationToken()

} // DiscogsController client
