package com.hotel.Exceptions;

public class InvalidInvoiceAmountException extends Exception {
    public InvalidInvoiceAmountException(String message) {
        super(message);
    }
}
