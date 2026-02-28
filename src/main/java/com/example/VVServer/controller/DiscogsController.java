package com.example.VVServer.controller;

import com.example.VVServer.Exception.DiscogsAPIException;
import com.example.VVServer.service.DiscogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/discogs")
public class DiscogsController {

    private DiscogsService discogsService;

    public DiscogsController(DiscogsService discogsService) {
        this.discogsService = discogsService;
    } // constructor

    @GetMapping("/search")
    public ResponseEntity<?> requestSearch(@RequestParam String token,
                                        @RequestParam String secret,
                                        @RequestParam String album,
                                        @RequestParam String artist,
                                        @RequestParam String year,
                                        @RequestParam String cat_no,
                                        @RequestParam String page_no)
            throws IOException, ExecutionException, InterruptedException, NumberFormatException, DiscogsAPIException {
        if (token.isEmpty() || secret.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Empty token or secret");
        }
        int page = Integer.parseInt(page_no);
        String response = discogsService.albumsSearch(token, secret, album, artist, year, cat_no, page);
        return  ResponseEntity
                .ok(response);
    } // requestSearch()

    @GetMapping("/pricing")
    public ResponseEntity<?> requestPricing(@RequestParam String token,
                                 @RequestParam String secret,
                                 @RequestParam String id)
            throws IOException, ExecutionException, InterruptedException, NumberFormatException, DiscogsAPIException {
        int convertedId = Integer.parseInt(id);
        String response = discogsService.pricingSearch(token, secret, convertedId);
        return ResponseEntity
                .ok(response);
    } // requestPricing()

} // DiscogsController class
