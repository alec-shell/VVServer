package com.example.VVServer.controller;

import com.example.VVServer.service.DiscogsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
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
                                        @RequestParam String page_no) {
        if (token.isEmpty() || secret.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Empty token or secret");
        }
        try {
            int page = Integer.parseInt(page_no);
            String response = discogsService.albumsSearch(token, secret, album, artist, year, cat_no, page);
            return  ResponseEntity.ok(response);
        } catch(IOException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Error communicating with Discogs API"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Connection interrupted"));
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid page no"));
        }
    } // requestSearch()

    @GetMapping("/pricing")
    public ResponseEntity<?> requestPricing(@RequestParam String token,
                                 @RequestParam String secret,
                                 @RequestParam String id) {
        try {
            int convertedId = Integer.parseInt(id);
            String response = discogsService.pricingSearch(token, secret, convertedId);
            return ResponseEntity.ok(response);
        }  catch(IOException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Error communicating with Discogs API"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Connection interrupted"));
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid page no"));
        }
    } // requestPricing()

} // DiscogsController class
