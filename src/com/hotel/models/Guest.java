package com.hotel.models;

import java.time.LocalDate;
import java.util.ArrayList;
import com.hotel.enums.Gender;
import com.hotel.enums.PaymentMethod;

public class Guest {
    private int id;
    private String username;
    private String password;
    private double balance;
    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;
    private RoomPreference roomPreferences;

    public Guest(int id, String username, String password, double balance, String address, LocalDate dateOfBirth, Gender gender, RoomPreference roomPreferences) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.roomPreferences = roomPreferences;
    }

    public Guest(int id, String username, String password, double balance, String address, LocalDate dateOfBirth, Gender gender) {
        this.id = id;
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
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

   /* public void register(String username,String password,LocalDate dateOfBirth,String address,Gender gender){

    }

    public boolean login(String username,String password){

    }

    public ArrayList<Room> viewAvailableRooms(){

    }

    public Reservation makeReservation (Room room,LocalDate checkIn,LocalDate checkOut){

    }

    public ArrayList<Reservation> viewReservations (){

    }

    public void cancelReservation(int reservationId){

    }

    public Invoice checkOut(int reservationId){

    }

    public void payInvoice(int invoiceId, PaymentMethod paymentMethod){

    } */
}
