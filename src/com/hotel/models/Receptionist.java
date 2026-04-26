package com.hotel.models;

import com.hotel.Exceptions.InvalidInvoiceAmountException;
import com.hotel.Exceptions.InvalidReservationStatusException;
import com.hotel.Exceptions.InvalidUserInformationException;
import com.hotel.Exceptions.ReservationNotFoundException;
import com.hotel.Validation.validator;
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

    public boolean login(String username,String password){
        for( Receptionist rpt : HotelDataBase.receptionists){
            if (rpt.getUsername().equals(username) && rpt.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
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
            throw new InvalidReservationStatusException("Guest must be checked in before checkout");
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
    public static Receptionist Register(String username,String password,LocalDate dateOfBirth,int workingHours) throws InvalidUserInformationException {
        validator.validateUsername(username);
        validator.validatePassword(password);
        validator.validateDateOfBirth(dateOfBirth);
        validator.validateWorkingHours(workingHours);

        if(HotelDataBase.findReceptionistByUsername(username)!=null)
            throw new InvalidUserInformationException("Receptionist: "+username+" ID: "+HotelDataBase.findReceptionistByUsername(username).getId()+"\nAlready Exists");
        Receptionist re= new Receptionist(username, password, dateOfBirth, workingHours);
        HotelDataBase.receptionists.add(re);
        return re;
    }


    @Override
    public String toString() {
        return "Receptionist: " + getUsername() + " - ID: " + getId();
    }
}