package com.hotel.models;

import com.hotel.Exceptions.InvalidRoomDataException;
import com.hotel.Exceptions.RoomNotAvailableException;
import com.hotel.Validation.validator;

import java.util.ArrayList;

public class Room {
    private int floor;
 private int roomNumber;
 private double pricePerNight;
 private boolean isAvailable;
 private ArrayList<Amenity> amenities;
 private RoomType roomType;

public Room(int roomNumber,double pricePerNight,RoomType roomType,int floor) throws InvalidRoomDataException {

    if (roomNumber <= 0) {
        throw new InvalidRoomDataException("Room number must be positive");
    }                                                               //Exceptions for incorrect price or room
    if (pricePerNight <= 0) {
        throw new InvalidRoomDataException("The price must be positive");
    }

    this.roomNumber = roomNumber;
    this.pricePerNight = pricePerNight;
    this.isAvailable = true;
    this.roomType = roomType;
    this.amenities = new ArrayList<>();
    this.floor = floor;
}


public void bookRoom() throws RoomNotAvailableException {             // Exception for booked rooms
    if (!isAvailable) {
        throw new RoomNotAvailableException("Room number: " + roomNumber + "isn't available");
    }
    isAvailable = false;          //when guest write the correct room no and it's available
}


public void release(){
    isAvailable = true;                //when guest check out
}


public void setPriceForNight(double pricePerNight) throws InvalidRoomDataException{
   validator.validatePrice(pricePerNight);

    this.pricePerNight = pricePerNight;
}

public void setRoomNo(int roomNumber) throws RoomNotAvailableException {
validator.validateRoomNumber(roomNumber);
    this.roomNumber = roomNumber;
}
    public int getFloor() {
        return floor;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomType(RoomType roomType) {
        validator.validateRoomType(roomType);
    this.roomType = roomType;
    }

    public void setFloor(int floor) {
    this.floor = floor;
    }

    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }
    public void addAmenity(Amenity amenity){
    amenities.add(amenity);
    }
    public void removeAmenity(Amenity amenity){
        amenities.remove(amenity);
    }
    @Override
    public String toString(){
    return "RoomNumber: "+roomNumber+"-Available: "+isAvailable;
    }
}
