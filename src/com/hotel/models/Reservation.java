package com.hotel.models;

import com.hotel.Exceptions.ReservationNotFoundException;
import com.hotel.enums.Reservationstatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private static int idCounter=0;
    private int id;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Reservationstatus status;
    private int totalNights;

    public Reservation( Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = ++idCounter;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = Reservationstatus.PENDING;
        this.totalNights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Reservationstatus getStatus() {
        return status;
    }

    public void setStatus(Reservationstatus status) {
        this.status = status;
    }

    public int getTotalNights() {
        return totalNights;
    }

    public void setTotalNights(int totalNights) {
        this.totalNights = totalNights;
    }

    public double calculateTotalCost() {
        return totalNights * room.getPricePerNight();
    }
    @Override
    public String toString() {
        return "=== Reservation ===" +
                "\nID: " + id +
                "\nGuest: " + guest.getUsername() +
                "\nRoom: " + room.getRoomNumber() +
                "\nRoom Type: " + room.getRoomType().getType() +
                "\nCheck-in: " + checkInDate +
                "\nCheck-out: " + checkOutDate +
                "\nTotal Nights: " + totalNights +
                "\nTotal Cost: $" + calculateTotalCost() +
                "\nStatus: " + status;
    }

   public void confirm()throws ReservationNotFoundException {     // see if the res. is pending or not
        if (status != Reservationstatus.PENDING) {
            throw new ReservationNotFoundException("The reservation "+ id + " isn't on pending, cannot confirm");
        }
        status = Reservationstatus.CONFIRMED;
    }

    public void cancel() throws ReservationNotFoundException{
        if (status == Reservationstatus.COMPLETED){
            throw new ReservationNotFoundException("The reservation "+ id + " is already completed, can't cancel");
        }
        if (status == Reservationstatus.CANCELLED){
            throw new ReservationNotFoundException("The reservation "+ id +" is  already cancelled");
        }
     status= Reservationstatus.CANCELLED;
        room.release();
    }
}

