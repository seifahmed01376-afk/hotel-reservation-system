package com.hotel.models;

import java.util.ArrayList;

public class Room {
 private int roomNumber;
 private double pricePerNight;
 private boolean isAvailable;
 private ArrayList<Amenity> amenities;
 private RoomType roomType;

public Room(int roomNumber,double pricePerNight,RoomType roomType){
     this.roomNumber=roomNumber;
     this.pricePerNight=pricePerNight;
     this.isAvailable=true;
     this.roomType=roomType;
     this.amenities=new ArrayList<>();
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
        this.roomType = roomType;
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
