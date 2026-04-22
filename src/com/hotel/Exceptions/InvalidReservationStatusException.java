package com.hotel.Exceptions;

public class InvalidReservationStatusException extends Exception {
    public InvalidReservationStatusException(String message) {
        super(message);
    }
}
