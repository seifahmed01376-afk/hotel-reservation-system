package com.hotel.models;
import com.hotel.enums.Role;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.hotel.database.HotelDataBase.*;


public class Admin extends Staff {

    public Admin(int id, String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(id, username, password, dateOfBirth, Role.ADMIN, workingHours);
    }


    public static Admin login(String username,String password){
        for(Admin a:admins){
            if(a.getUsername().equals(username)&&a.getPassword().equals(password))
                return a;
        }
        return null;
    }
    public static void addRoom(int roomNumber,double pricePerNight,RoomType roomType,int floor){
        if(findRoomByRoomNumber(roomNumber)!=null) {
            System.out.println("Room: " + roomNumber + " Already exists");
            return;
        }
        if(pricePerNight<=0) {
            System.out.println("Price must be greater than zero!");
            return;
        }
        rooms.add(new Room(roomNumber,pricePerNight,roomType,floor));
        System.out.println("Room added successfully");
    }
    public static void deleteRoom(int roomNumber) {
        Room toRemove = findRoomByRoomNumber(roomNumber);
        if (toRemove == null) {
            System.out.println("Room not found!");
            return;
        }
        rooms.remove(toRemove);
        System.out.println("Room removed successfully!");
    }
    public static void updateRoom(int roomNumber, double pricePerNight, RoomType roomType, ArrayList<Amenity> amenities) {
        Room toUpdate = findRoomByRoomNumber(roomNumber);
        if (toUpdate == null) {
            System.out.println("Room not found!");
            return;
        }
        toUpdate.setPricePerNight(pricePerNight);
        toUpdate.setRoomType(roomType);
        toUpdate.setAmenities(amenities);
        System.out.println("Room updated successfully!");
    }
    public static void addAmenity(String amenity){
        if(findAmenityByName(amenity)!=null){
            System.out.println("Amenity already exists!");
            return;
        }

        int id=nextAmenityId();
        Amenity toAdd=findAmenityById(id);
        amenities.add(new Amenity(id,amenity));
        System.out.println("Amenity added successfully!");
    }
    public static void deleteAmenity(int id){
        Amenity toRemove=findAmenityById(id);
        if(toRemove==null){
            System.out.println("Amenity not found!");
            return;
        }
        amenities.remove(toRemove);
        System.out.println("Amenity removed successfully!");
    }
    public static void updateAmenity(int id,String amenity){
        Amenity toUpdate=findAmenityById(id);
        if(toUpdate==null){
            System.out.println("Amenity not found!");
            return;
        }
        toUpdate.setAmenity(amenity);
        System.out.println("Amenity updated Successfully!");

    }
    public static void addRoomType(String type) {
        if (findRoomTypeByName(type) != null) {
            System.out.println("Room type already exists!");
            return;
        }
        int id = nextRoomTypeID();
        roomTypes.add(new RoomType(id, type));
        System.out.println("Room type added successfully!");
    }
    public static void deleteRoomType(int id){
        RoomType toRemove=findRoomTypeById(id);
        if(toRemove==null){
            System.out.println("RoomType not found!");
            return;
        }
        roomTypes.remove(toRemove);
        System.out.println("RoomType Removed successfully!");
    }
    public static void updateRoomType(int id, String newType) {
        RoomType toUpdate = findRoomTypeById(id);
        if (toUpdate == null) {
            System.out.println("RoomType not found!");
            return;
        }
        if (findRoomTypeByName(newType) != null) {
            System.out.println("RoomType already exists!");
            return;
        }
        toUpdate.setType(newType);
        System.out.println("RoomType updated successfully!");
    }
    public static void viewAllGuests() {
        System.out.println("=== All Guests ===");
        for (Guest g : guests)
            System.out.println(g);
    }
    public static void viewAllRooms() {
        System.out.println("=== All Rooms ===");
        for (Room r : rooms)
            System.out.println(r);
    }
    public static void viewAllReservations() {
        System.out.println("=== All Reservations ===");
        for (Reservation r : reservations)
            System.out.println(r);
    }
    public static void viewAllAmenities() {
        System.out.println("=== All Amenities ===");
        for (Amenity a : amenities)
            System.out.println(a);
    }
    public static void viewAllRoomTypes() {
        System.out.println("=== All Room Types ===");
        for (RoomType rt : roomTypes)
            System.out.println(rt);
    }

}