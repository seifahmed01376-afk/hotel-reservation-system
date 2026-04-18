package com.hotel.database;
import com.hotel.enums.Gender;
import com.hotel.enums.PaymentMethod;
import com.hotel.enums.Role;
import com.hotel.models.*;
import java.time.LocalDate;
import java.util.ArrayList;

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


    public static void initialize(){
        roomTypes.add(new RoomType(1,"single"));
        roomTypes.add(new RoomType(2,"double"));
        roomTypes.add(new RoomType(3,"triple"));
        roomTypes.add(new RoomType(4,"suite"));

        amenities.add(new Amenity(1,"wifi"));
        amenities.add(new Amenity(2,"tv"));
        amenities.add(new Amenity(3,"minibar"));

        Room room1 = new Room(100, 139.9,roomTypes.get(0),13);
        room1.addAmenity(amenities.get(0));
        room1.addAmenity(amenities.get(1));
        rooms.add(room1);
        Room room2 = new Room(67, 259.9,  roomTypes.get(1), 21);
        room2.addAmenity(amenities.get(0));
        room2.addAmenity(amenities.get(2));
        rooms.add(room2);
        Room room3 = new Room(167, 339.9, roomTypes.get(2),52 );
        room3.addAmenity(amenities.get(0)); // WiFi
        room3.addAmenity(amenities.get(1)); // TV
        room3.addAmenity(amenities.get(2)); // Mini-bar
        rooms.add(room3);
        Room room4 = new Room(203, 439.9,  roomTypes.get(3), 27);
        room4.addAmenity(amenities.get(0)); // WiFi
        room4.addAmenity(amenities.get(1)); // TV
        room4.addAmenity(amenities.get(2)); // Mini-bar
        rooms.add(room4);


        ArrayList<Amenity> prefAmenities1=new ArrayList<>();
        prefAmenities1.add(amenities.get(0));
        prefAmenities1.add(amenities.get(1));
        RoomPreference pref1=new RoomPreference(roomTypes.get(0),13,prefAmenities1);
        guests.add(new Guest(1,"seif","seif2007",5000.9,"Nasr city", LocalDate.of(1999,5,3), Gender.MALE));
        guests.add(new Guest(2,"Abdo","Abdo2007",2000.0,"Newyork",LocalDate.of(1998,4,17),Gender.MALE,pref1));
        guests.add(new Guest(3,"sara","sara123",360,"paris",LocalDate.of(2001,6,17),Gender.FEMALE));

        admins.add(new Admin(1,"Admin1","Admin123",LocalDate.of(1993,9,21),8));

        receptionists.add(new Receptionist(1,"Receptionist1","Receptionist123",LocalDate.of(1995,7,11),12));

        Reservation res1=new Reservation(1,guests.get(0),rooms.get(0),LocalDate.of(2026,5,1),LocalDate.of(2026,5,4));
        res1.confirm();
        reservations.add(res1);

        Invoice in1=new Invoice(1,reservations.get(0),reservations.get(0).calculateTotalCost(), PaymentMethod.CASH);
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

}