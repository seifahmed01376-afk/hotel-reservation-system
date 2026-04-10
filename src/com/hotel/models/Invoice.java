package com.hotel.models;

import com.hotel.enums.PaymentMethod;
import java.time.LocalDate;

public class Invoice {
    private int id;
    private Reservation reservation;
    private double totalAmount;
    private PaymentMethod paymentMethod;
    private LocalDate paymentDate;
    private boolean isPaid;

    public Invoice(int id, Reservation reservation, double totalAmount, PaymentMethod paymentMethod) {
        this.id = id;
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

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void generateInvoice() {
        totalAmount = reservation.calculateTotalCost();
        paymentDate = LocalDate.now();
    }

    public void markAsPaid(PaymentMethod paymentMethod) {
        /* if (isPaid) {
              throw new InvalidPaymentException("الفاتورة مدفوعة بالفعل!");
        } */
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDate.now();
        this.isPaid = true;
    }
}
