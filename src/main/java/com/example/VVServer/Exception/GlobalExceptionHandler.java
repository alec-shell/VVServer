package com.example.VVServer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIO(IOException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Error communicating with Discogs API"));
    } // handleIO()

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<?> handleExecution(ExecutionException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Error communicating with Discogs API"));
    } // handleExecution()

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<?> handleInterrupted(InterruptedException e) {
        Thread.currentThread().interrupt();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Connection interrupted"));
    } // handleInterrupted()

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormat(NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid numeric value"));
    } // handleNumberFormat()

    @ExceptionHandler(DiscogsAPIException.class)
    public ResponseEntity<?> handleDiscogs(DiscogsAPIException e) {
        return ResponseEntity.status(e.getCode())
                .body(Map.of("error", e.getMessage()));
    } // handleDiscogs()

} // GlobalExceptionHandler class
