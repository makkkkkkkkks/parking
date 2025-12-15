package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VehicleAlreadyParkedException.class)
    public ResponseEntity<String> handleVehicleAlreadyParkedException(VehicleAlreadyParkedException ex) {
        return ResponseEntity.status(HttpStatus.FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoAvailableParkingSpacesException.class)
    public ResponseEntity<String> handleNoAvailableParkingSpacesException(NoAvailableParkingSpacesException ex) {
        return ResponseEntity.status(HttpStatus.FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> handleCarNotFoundException(CarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
