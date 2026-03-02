package com.example.VVServer.controller;

import com.example.VVServer.DTO.PricingRequest;
import com.example.VVServer.DTO.SearchRequest;
import com.example.VVServer.Exception.DiscogsAPIException;
import com.example.VVServer.service.DiscogsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/discogs")
public class DiscogsController {

    private DiscogsService discogsService;

    public DiscogsController(DiscogsService discogsService) {
        this.discogsService = discogsService;
    } // constructor

    @PostMapping("/search")
    public ResponseEntity<?> postSearch(@RequestBody SearchRequest request)
            throws IOException, ExecutionException, InterruptedException, DiscogsAPIException {
        if (request.token.isEmpty() || request.secret.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Empty token or secret");
        }
        String response = discogsService.albumsSearch(request.token, request.secret,
                request.album, request.artist,
                request.year, request.catNo, request.pageNo);
        return  ResponseEntity
                .ok(response);
    } // requestSearch()

    @PostMapping("/pricing")
    public ResponseEntity<?> postPricing(@RequestBody PricingRequest request)
            throws IOException, ExecutionException, InterruptedException, DiscogsAPIException {
        String response = discogsService.pricingSearch(request.token, request.secret, request.id);
        return ResponseEntity
                .ok(response);
    } // postPricing()

} // DiscogsController class
