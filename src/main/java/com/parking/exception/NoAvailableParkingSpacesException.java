package com.parking.exception;

public class NoAvailableParkingSpacesException extends RuntimeException {
    
    public NoAvailableParkingSpacesException(String message) {
        super(message);
    }
}

