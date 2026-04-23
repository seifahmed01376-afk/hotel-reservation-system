package com.hotel.models;

import com.hotel.Exceptions.InvalidInvoiceAmountException;
import com.hotel.Exceptions.InvalidPaymentException;
import com.hotel.Exceptions.InvalidRoomDataException;
import com.hotel.Validation.validator;
import com.hotel.enums.PaymentMethod;
import java.time.LocalDate;

public class Invoice {
    private static int idCounter=0;
    private int id;
    private Reservation reservation;
    private double totalAmount;
    private PaymentMethod paymentMethod;
    private LocalDate paymentDate;
    private boolean isPaid;

    public Invoice( Reservation reservation, double totalAmount, PaymentMethod paymentMethod) throws InvalidInvoiceAmountException {

       if ( reservation ==null){                                                       // new exceptions
           throw new IllegalArgumentException("The reservation cannot be null.");
       }
       if (totalAmount <= 0){
           throw new InvalidInvoiceAmountException("Not enough amount, must be greater than zero");
       }
       if (paymentMethod == null){
           throw new IllegalArgumentException("Payment method cannot be null");
       }

        this.id = ++idCounter;
        this.reservation = reservation;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDate.now();
        this.isPaid = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) throws InvalidInvoiceAmountException {
        validator.validateAmount(totalAmount);
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) throws InvalidPaymentException {
        validator.validatePaymentMethod(paymentMethod);
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        validator.validatePaymentDate(paymentDate);
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void generateInvoice() throws InvalidInvoiceAmountException{
        if (reservation ==null){
            throw new IllegalArgumentException("Cannot generate invoice, as the reservation is null");
        }
        totalAmount= reservation.calculateTotalCost();        // exception for the generated invoice

        if (totalAmount <=0){
            throw new InvalidInvoiceAmountException("The calculated total amount is invalid: "+ totalAmount);
        }
    }

    public void markAsPaid(PaymentMethod paymentMethod) throws InvalidPaymentException {
         if (isPaid){
             throw new InvalidPaymentException("Invoice # "+id +" is already paid");
         }
         if (paymentMethod == null){
             throw new InvalidPaymentException("The payment method cannot be  null");
         }

        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDate.now();
        this.isPaid = true;
    }
}
