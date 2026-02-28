package com.example.VVServer.controller;

import com.example.VVServer.oauth.DiscogsAuthorization;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/url")
    public String getVerifierURL(@RequestParam String token,
                                 @RequestParam String secret) {
        OAuth1RequestToken requestToken = new  OAuth1RequestToken(token, secret);
        return discogsAuth.getService().getAuthorizationUrl(requestToken);
    } // getVerifierURL()

    @GetMapping("auth_tkn")
    public Map<String, String> getAuthorizationToken(@RequestParam String token,
                                                     @RequestParam String secret,
                                                     @RequestParam String verifier) {
        OAuth1RequestToken requestToken = new OAuth1RequestToken(token, secret);
        OAuth1AccessToken accessToken = discogsAuth.generateAccessToken(requestToken, verifier);
        Map<String, String> components = new HashMap<>();
        components.put("token", accessToken.getToken());
        components.put("secret", accessToken.getTokenSecret());
        return components;
    } // getAuthorizationToken()

} // DiscogsController client
