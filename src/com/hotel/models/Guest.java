package com.hotel.models;

import java.time.LocalDate;
import java.util.ArrayList;

import com.hotel.Exceptions.*;
import com.hotel.Validation.validator;
import com.hotel.database.HotelDataBase;
import com.hotel.enums.Gender;
import com.hotel.enums.PaymentMethod;
import com.hotel.enums.Reservationstatus;
import com.hotel.interfaces.payable;

public class Guest implements payable {
    private static int idCounter=0;
    private int id;
    private String username;
    private String password;
    private double balance;
    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;
    private RoomPreference roomPreferences;


    public Guest( String username, String password, double balance, String address, LocalDate dateOfBirth, Gender gender, RoomPreference roomPreferences) {
        validator.validateUsername(username);
        validator.validatePassword(password);
        validator.validateBalance(balance);
        validator.validateAddress(address);
        validator.validateDateOfBirth(dateOfBirth);
        validator.validateGender(gender);
        this.id = ++idCounter;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.roomPreferences = roomPreferences;
    }

    public Guest( String username, String password, double balance, String address, LocalDate dateOfBirth, Gender gender) {
        validator.validateUsername(username);
        validator.validatePassword(password);
        validator.validateBalance(balance);
        validator.validateAddress(address);
        validator.validateDateOfBirth(dateOfBirth);
        validator.validateGender(gender);
        this.id = ++idCounter;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        validator.validateUsername(username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validator.validatePassword(password);
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        validator.validateBalance(balance);
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validator.validateAddress(address);
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        validator.validateDateOfBirth(dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        validator.validateGender(gender);
        this.gender = gender;
    }

    public RoomPreference getRoomPreferences() {
        return roomPreferences;
    }

    public void setRoomPreferences(RoomPreference roomPreferences) {
        this.roomPreferences = roomPreferences;
    }
    @Override
    public String toString(){
        return "Guest id: "+id+"Guest Username: "+username;
    }


@Override
    public void pay(double amount) throws InvalidPaymentException {
        if (balance < amount){                                         // Exception for insufficient balance
            throw new InvalidPaymentException("Insufficient amount of money, you  have " + balance + " and you have "+ amount);
        }
       balance -= amount;
    }


    public static Guest register(String username, String password, LocalDate dateOfBirth, String address, Gender gender, double balance) throws InvalidUserInformationException {

        validator.validateUsername(username);
        validator.validatePassword(password);
        validator.validateDateOfBirth(dateOfBirth);
        validator.validateAddress(address);
        validator.validateGender(gender);
        validator.validateBalance(balance);

        if (HotelDataBase.findGuestByUsername(username) != null)
            throw new InvalidUserInformationException("The username " + username + " already exists");

        Guest newGuest = new Guest(username, password, balance, address, dateOfBirth, gender);
        HotelDataBase.guests.add(newGuest);
        return newGuest;
    }

    public boolean login(String username,String password){
        for( Guest g : HotelDataBase.guests){
            if (g.getUsername().equals(username) && g.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Room> viewAvailableRooms(){
        ArrayList<Room> availableRooms =new ArrayList<>();
        for ( Room r : HotelDataBase.rooms) {
            if (r.isAvailable()) {
                availableRooms.add(r);
            }
        }
        return availableRooms;
    }

   public Reservation makeReservation (Room room,LocalDate checkIn,LocalDate checkOut) throws ReservationNotFoundException, InvalidDateRangeException {
       if (room == null || !room.isAvailable()) {
           throw new ReservationNotFoundException("This room is not available");
       }
       if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
           throw new InvalidDateRangeException("The check-out must be after the check-in");
       }
       return HotelDataBase.makeReservation(this ,room,checkIn,checkOut);
   }


    public ArrayList<Reservation> viewReservations (){
        ArrayList<Reservation> myReservations =new ArrayList<>();
        for ( Reservation res : HotelDataBase.reservations) {
            if (res.getGuest().getId() == this.id) {
                myReservations.add(res);
            }
        }
        return myReservations;

    }

    public void cancelReservation(int reservationId) throws ReservationNotFoundException, InvalidReservationStatusException {
        Reservation found = null;
        for (Reservation res : HotelDataBase.reservations){
            if (res.getId() == reservationId && res.getGuest().getId() == this.id){
                found = res;
                break;
            }
        }

        if (found == null){
            throw new ReservationNotFoundException("This reservation with this ID: "+ reservationId+ " not found");
        }
        HotelDataBase.cancelReservation(reservationId);

    }

    public Invoice checkOut(int reservationId,PaymentMethod paymentMethod) throws ReservationNotFoundException, InvalidInvoiceAmountException {
        Reservation found = null;
        for (Reservation res : HotelDataBase.reservations) {
            if (res.getId() == reservationId && res.getGuest().getId() == this.id) {
                found = res;
                break;
            }
        }

        if (found == null)
            throw new ReservationNotFoundException("No reservation found with ID: " + reservationId);

        found.setStatus(Reservationstatus.COMPLETED);
        found.getRoom().release();
        Invoice invoice=new Invoice(found, found.calculateTotalCost(),paymentMethod);
        HotelDataBase.invoices.add(invoice);

        return invoice;
    }

    public void payInvoice(int reservationId, PaymentMethod paymentMethod) throws InvalidPaymentException{
        Invoice found=HotelDataBase.findInvoiceById(reservationId);


        if (found == null)
            throw new InvalidPaymentException("No invoice found with ID: " + reservationId);

        if (found.isPaid())
            throw new InvalidPaymentException("Invoice #" + reservationId + " is already paid.");
        this.pay(found.getTotalAmount());
        found.markAsPaid(paymentMethod);
    }
    public static boolean isRoomAvailableForDates(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Reservation res : HotelDataBase.reservations) {
            if (res.getRoom().getRoomNumber() == room.getRoomNumber()
                    && res.getStatus() != Reservationstatus.CANCELLED
                    && res.getStatus() != Reservationstatus.COMPLETED) {

                // check if dates overlap
                boolean overlaps = res.getCheckInDate().isBefore(checkOut)
                        && res.getCheckOutDate().isAfter(checkIn);
                if (overlaps) return false;
            }
        }
        return true;
    }

}
