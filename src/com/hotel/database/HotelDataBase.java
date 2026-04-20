package com.hotel.database;
import com.hotel.Exceptions.InvalidRoomDataException;
import com.hotel.enums.Gender;
import com.hotel.enums.PaymentMethod;
import com.hotel.models.*;
import java.time.LocalDate;
import java.util.ArrayList;
import com.hotel.Exceptions.RoomNotAvailableException;
import com.hotel.Exceptions.ReservationNotFoundException;
import com.hotel.Exceptions.InvalidPaymentException;


public class HotelDataBase {
    public static ArrayList<Guest> guests = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Amenity> amenities = new ArrayList<>();
    public static ArrayList<Receptionist> receptionists = new ArrayList<>();
    public static ArrayList<Invoice> invoices = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<RoomType> roomTypes = new ArrayList<>();

    public static int nextGuestID(){
        return (guests.size()+1);
    }
    public static int nextReservationID(){
        return(reservations.size()+1);
    }
    public static int nextAmenityId(){
        return(amenities.size()+1);
    }
    public static int nextInvoice(){
        return(invoices.size()+1);
    }
    public static int nextRoomTypeID(){
        return(roomTypes.size()+1);
    }


    public static void initialize() throws InvalidRoomDataException {
        roomTypes.add(new RoomType("single"));
        roomTypes.add(new RoomType("double"));
        roomTypes.add(new RoomType("triple"));
        roomTypes.add(new RoomType("suite"));

        amenities.add(new Amenity( "wifi"));
        amenities.add(new Amenity("tv"));
        amenities.add(new Amenity( "minibar"));

        Room room1 = new Room(100, 139.9, roomTypes.get(0), 13);
        room1.addAmenity(amenities.get(0));
        room1.addAmenity(amenities.get(1));
        rooms.add(room1);
        Room room2 = new Room(67, 259.9, roomTypes.get(1), 21);
        room2.addAmenity(amenities.get(0));
        room2.addAmenity(amenities.get(2));
        rooms.add(room2);
        Room room3 = new Room(167, 339.9, roomTypes.get(2), 52);
        room3.addAmenity(amenities.get(0));
        room3.addAmenity(amenities.get(1));
        room3.addAmenity(amenities.get(2));
        rooms.add(room3);
        Room room4 = new Room(203, 439.9, roomTypes.get(3), 27);
        room4.addAmenity(amenities.get(0));
        room4.addAmenity(amenities.get(1));
        room4.addAmenity(amenities.get(2));
        rooms.add(room4);


        ArrayList<Amenity> prefAmenities1 = new ArrayList<>();
        prefAmenities1.add(amenities.get(0));
        prefAmenities1.add(amenities.get(1));
        RoomPreference pref1 = new RoomPreference(roomTypes.get(0), 13, prefAmenities1);
        guests.add(new Guest( "seif", "seif2007", 5000.9, "Nasr city", LocalDate.of(1999, 5, 3), Gender.MALE));
        guests.add(new Guest( "Abdo", "Abdo2007", 2000.0, "Newyork", LocalDate.of(1998, 4, 17), Gender.MALE, pref1));
        guests.add(new Guest( "sara", "sara123", 360, "paris", LocalDate.of(2001, 6, 17), Gender.FEMALE));

        admins.add(new Admin(1, "Admin1", "Admin123", LocalDate.of(1993, 9, 21), 8));

        receptionists.add(new Receptionist( "Receptionist1", "Receptionist123", LocalDate.of(1995, 7, 11), 12));

        Reservation res1 = new Reservation( guests.get(0), rooms.get(0), LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 4));

        try {
            res1.confirm();
        } catch (ReservationNotFoundException e) {
            throw new RuntimeException(e);
        }
        reservations.add(res1);

        Invoice in1 = new Invoice( reservations.get(0), reservations.get(0).calculateTotalCost(), PaymentMethod.CASH);
        invoices.add(in1);
    }

    public static Guest findGuestByUsername(String username) {
        for (Guest g : guests) {
            if (g.getUsername().equals(username)) {
                return g;
            }
        }
        return null;
    }
    public static Guest findGuestById(int id){
        int i=0;
        while(i<guests.size()) {
            if(guests.get(i).getId()==id){
                return guests.get(i);
            }
                i++;
        }
        return null;
    }
    public static Reservation findReservationById(int id){
        int i=0;
        while(i<reservations.size()) {
            if(reservations.get(i).getId()==id){
                return reservations.get(i);
            }
            i++;
        }
        return null;
    }
    public static Amenity findAmenityById(int id){
        int i=0;
        while(i<amenities.size()) {
            if(amenities.get(i).getId()==id){
                return amenities.get(i);
            }
            i++;
        }
        return null;
    }
    public static Amenity findAmenityByName(String amenity){
        for(Amenity a:amenities){
           if(a.getAmenity().equals(amenity))
               return a;
        }
        return null;

    }
    public static Invoice findInvoiceById(int reservationId){
        int i=0;
        while(i<invoices.size()) {
            if(invoices.get(i).getReservation().getId()==reservationId){
                return invoices.get(i);
            }
            i++;
        }
        return null;
    }
    public static RoomType findRoomTypeById(int id){
        int i=0;
        while(i<roomTypes.size()) {
            if(roomTypes.get(i).getId()==id){
                return roomTypes.get(i);
            }
            i++;
        }
        return null;
    }
    public static RoomType findRoomTypeByName(String type) {
        for (RoomType rt : roomTypes) {
            if (rt.getType().equalsIgnoreCase(type)) {
                return rt;
            }
        }
        return null;
    }
    public static Room findRoomByRoomNumber(int roomNumber){
        int i=0;
        while(i<rooms.size()) {
            if(rooms.get(i).getRoomNumber()== roomNumber){
                return rooms.get(i);
            }
            i++;
        }
        return null;
    }
    public static void makeReservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        try {
            room.bookRoom(); // throws RoomNotAvailableException if it is already booked
            Reservation newRes = new Reservation( guest, room, checkIn, checkOut);
            newRes.confirm();
            reservations.add(newRes);
            System.out.println("Reservation successful! Room " + room.getRoomNumber() + " is booked.");
        } catch (RoomNotAvailableException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } catch (ReservationNotFoundException e) {
            System.out.println("Confirmation failed: " + e.getMessage());
        }
    }

    public static void cancelReservation(int reservationId) {
        Reservation res = findReservationById(reservationId);
        try {
            if (res == null) {
                throw new ReservationNotFoundException(
                        "No reservation found with ID: " + reservationId
                );
            }
            res.cancel(); // throws ReservationNotFoundException if already cancelled/completed
            System.out.println("Reservation " + reservationId + " cancelled successfully.");
        } catch (ReservationNotFoundException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }
    }

    public static void processPayment(Guest guest, double amount) {
        try {
            guest.pay(amount); // throws InvalidPaymentException if balance is not enough
            System.out.println("Payment of $" + amount + " successful!");
        } catch (InvalidPaymentException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }

}