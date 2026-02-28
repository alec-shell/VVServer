package com.example.VVServer.config;

import com.example.VVServer.client.DiscogsClient;
import com.example.VVServer.oauth.DiscogsAuthorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DiscogsAuthorization discogsAuthorization(@Value("${DISCOGS_CONSUMER_KEY}") String key,
                                                     @Value("${DISCOGS_CONSUMER_SECRET}") String secret) {
        return new DiscogsAuthorization(key, secret);
    }

    @Bean
    public DiscogsClient discogsClient(DiscogsAuthorization discogsAuthorization) {
        return new DiscogsClient(discogsAuthorization);
    }
} // Configuration class
