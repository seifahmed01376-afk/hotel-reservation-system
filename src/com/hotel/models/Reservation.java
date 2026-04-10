package com.hotel.models;

import com.hotel.enums.Reservationstatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private int id;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Reservationstatus status;
    private int totalNights;

    public Reservation(int id, Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
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

   /* public void confirm() {
        if (status != Reservationstatus.PENDING) {
            throw new RuntimeException("الحجز مش في حالة PENDING!");
        }
        status = Reservationstatus.CONFIRMED;
    }

    public void cancel() {
        if (status == Reservationstatus.COMPLETED || status == Reservationstatus.CANCELLED) {
            throw new RuntimeException("مينفعش تلغي الحجز ده!");
        }
        status = Reservationstatus.CANCELLED;
        room.setAvailable(true);
    }

    public void complete() {
        if (status != Reservationstatus.CONFIRMED) {
            throw new RuntimeException("الحجز لازم يكون CONFIRMED الأول!");
        }
        status = Reservationstatus.COMPLETED;
    }*/

}

