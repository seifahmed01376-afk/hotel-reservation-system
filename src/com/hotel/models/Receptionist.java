package com.hotel.models;

import com.hotel.database.HotelDataBase;
import com.hotel.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;

public class Receptionist extends Staff {
    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }

   /* public void checkIn(int reservationId) {
        // Exception

    }

    public Invoice checkout() {
        // Exception

    }*/

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