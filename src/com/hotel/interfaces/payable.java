package com.hotel.interfaces;

import com.hotel.Exceptions.InvalidPaymentException;

public interface payable {
    void pay(double amount) throws InvalidPaymentException;
}
