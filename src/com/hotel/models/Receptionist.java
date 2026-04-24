package com.hotel.models;

import com.hotel.Exceptions.InvalidInvoiceAmountException;
import com.hotel.Exceptions.InvalidReservationStatusException;
import com.hotel.Exceptions.ReservationNotFoundException;
import com.hotel.database.HotelDataBase;
import com.hotel.enums.PaymentMethod;
import com.hotel.enums.Reservationstatus;
import com.hotel.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;

public class Receptionist extends Staff {
    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }

    public void checkIn(int reservationId) throws ReservationNotFoundException, InvalidReservationStatusException {
        Reservation found =HotelDataBase.findReservationById(reservationId);
        if(found==null){
            throw new ReservationNotFoundException("No Reservation found with id: "+reservationId);
        }
        if(found.getStatus()!=Reservationstatus.CONFIRMED){
            throw new InvalidReservationStatusException("Your Reservation wasn't confirmed");
        }
        found.setStatus(Reservationstatus.CHECKED_IN);
        System.out.println("Guest: "+found.getGuest().getId()+" Has checked in room: "+found.getRoom().getRoomNumber());

    }

    public Invoice checkout(int reservationId) throws ReservationNotFoundException, InvalidReservationStatusException, InvalidInvoiceAmountException {
        Reservation found=HotelDataBase.findReservationById(reservationId);
        if(found==null){
            throw new ReservationNotFoundException("Can't find Reservation!");
        }
        if(found.getStatus()!=Reservationstatus.CHECKED_IN){
            throw new InvalidReservationStatusException("You didn't confirm your Reservation");
        }
        found.setStatus(Reservationstatus.COMPLETED);
        found.getRoom().release();
        Invoice invoice=new Invoice(found, found.calculateTotalCost(), PaymentMethod.CASH);
        HotelDataBase.invoices.add(invoice);
        System.out.println("Guest: "+found.getGuest().getId()+" Has checked out room: "+found.getRoom().getRoomNumber());
        return invoice;



    }

    public ArrayList<Reservation> viewTodayCheckOuts() {
        ArrayList<Reservation> today = new ArrayList<>();
        for (Reservation res : HotelDataBase.reservations){
            if(res.getCheckOutDate().equals(LocalDate.now())){
                today.add(res);
            }
        }
        return today;
    }

    public ArrayList<Reservation> viewTodayCheckIns() {
        ArrayList<Reservation> today = new ArrayList<>();
        for (Reservation res : HotelDataBase.reservations){
            if(res.getCheckInDate().equals(LocalDate.now())){
                today.add(res);
            }
        }
        return today;
    }

    public ArrayList<Guest> viewAllGuests() {
        return HotelDataBase.guests;
    }

    public ArrayList<Reservation> viewAllReservations() {
        return HotelDataBase.reservations;
    }

    @Override
    public String toString() {
        return "Receptionist: " + getUsername() + " - ID: " + getId();
    }
}