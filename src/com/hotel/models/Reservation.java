package com.hotel.models;

import com.hotel.Exceptions.InvalidDateRangeException;
import com.hotel.Exceptions.InvalidReservationStatusException;
import com.hotel.Exceptions.ReservationNotFoundException;
import com.hotel.Exceptions.RoomNotAvailableException;
import com.hotel.Validation.validator;
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

    public Reservation( Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) throws RoomNotAvailableException , InvalidDateRangeException {

        if(guest ==null){
            throw new IllegalArgumentException("The guest cannot be null");
        }
        if (room ==null){
            throw new IllegalArgumentException("The room cannot be null");
        }
        if(!room.isAvailable()){
            throw new RoomNotAvailableException("The room " + room.getRoomNumber()+"is not available");
        }

        if (checkInDate==null || checkOutDate ==null){
            throw new InvalidDateRangeException("The check-in and check-out cannot be null");
        }

        if (!checkOutDate.isAfter(checkInDate)){
            throw new InvalidDateRangeException("The check-out cannot be before the check-in");
        }

        if (checkInDate.isBefore(LocalDate.now())){
            throw new InvalidDateRangeException("The check-in date cannot be in the past");
        }

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

    public void setRoom(Room room) throws RoomNotAvailableException {
        validator.validateRoom(room);
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        validator.validateCheckInDate(checkInDate);
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate  checkOutDate,LocalDate checkInDate) {
        validator.validateCheckOutDate(checkOutDate,checkInDate);
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
        if (room == null) {
            throw new IllegalArgumentException("The room is null, cannot calculate the cost");
        }
        if (totalNights <=0){
            throw new IllegalStateException("Total night must be greater than zero");
        }
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

    public void cancel() throws InvalidReservationStatusException {
        if (status == Reservationstatus.COMPLETED){
            throw new InvalidReservationStatusException("The reservation "+ id + " is already completed, can't cancel");
        }
        if (status == Reservationstatus.CANCELLED){
            throw new InvalidReservationStatusException("The reservation "+ id +" is  already cancelled");
        }
     status= Reservationstatus.CANCELLED;
        room.release();
    }
}

