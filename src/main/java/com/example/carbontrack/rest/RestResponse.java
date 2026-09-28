package com.example.carbontrack.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

final class RestResponse {
    private RestResponse() { }
    static <T> ResponseEntity<T> created(T body) { return ResponseEntity.status(HttpStatus.CREATED).body(body); }
}
