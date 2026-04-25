package com.hotel.models;
import com.hotel.Exceptions.InvalidRoomDataException;
import com.hotel.enums.Role;
import com.hotel.interfaces.managable;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.hotel.database.HotelDataBase.*;


public class Admin extends Staff implements managable {

    public Admin( String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }
    @Override
    public void viewAll(){
        viewAllAmenities();
        viewAllGuests();
        viewAllRooms();
        viewAllReservations();
        viewAllRoomTypes();
    }



    public static boolean login(String username,String password){
        for(Admin a:admins){
            if(a.getUsername().equals(username)&&a.getPassword().equals(password))
                return true;
        }
        return false;
    }
    public static void addRoom(int roomNumber,double pricePerNight,RoomType roomType,int floor) throws InvalidRoomDataException {
        if(findRoomByRoomNumber(roomNumber)!=null) {
            throw new InvalidRoomDataException("Room already exists!");

        }
        if(pricePerNight<=0) {
           throw new InvalidRoomDataException("PricePerNight can't be less than zero!");

        }
        rooms.add(new Room(roomNumber,pricePerNight,roomType,floor));
        System.out.println("Room added successfully");
    }
    @Override
    public  void deleteRoom(int roomNumber) {
        Room toRemove = findRoomByRoomNumber(roomNumber);
        if (toRemove == null) {
            System.out.println("Room not found!");
            return;
        }
        rooms.remove(toRemove);
        System.out.println("Room removed successfully!");
    }
    public static void updateRoom(int roomNumber, double pricePerNight, RoomType roomType, ArrayList<Amenity> amenities) throws InvalidRoomDataException {
        Room toUpdate = findRoomByRoomNumber(roomNumber);
        if (toUpdate == null) {
            System.out.println("Room not found!");
            return;
        }
        if(pricePerNight<=0) {
            throw new InvalidRoomDataException("PricePerNight can't be less than zero!");
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


        amenities.add(new Amenity(amenity));
        System.out.println("Amenity added successfully!");
    }
    @Override
    public void deleteAmenity(int id){
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

        roomTypes.add(new RoomType(type));
        System.out.println("Room type added successfully!");
    }
    @Override
    public void deleteRoomType(int id){
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