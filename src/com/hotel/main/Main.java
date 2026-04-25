package com.hotel.main;

import com.hotel.Exceptions.*;
import com.hotel.Validation.validator;
import com.hotel.database.HotelDataBase;
import com.hotel.enums.Gender;
import com.hotel.models.Guest;
import com.hotel.models.Receptionist;
import com.hotel.models.Reservation;
import com.hotel.models.Room;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.hotel.database.HotelDataBase.rooms;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws InvalidRoomDataException {
        HotelDataBase.initialize();
        System.out.println("========================================");
        System.out.println("welcome to the hotel-reservation-system!");
        System.out.println("========================================");
        while(true){
            System.out.println("Who are you?");
            System.out.println("1:Guest");
            System.out.println("2:Admin");
            System.out.println("3:Receptionist");
            System.out.println("4:Exit");
            System.out.print("Enter number: ");
            String menu=scanner.nextLine().trim();
            switch (menu){
                case"1"->GuestMenu();
                case"2"-> System.out.println("coming soon");//AdminMenu();
                case"3"-> System.out.println("coming soon");//ReceptionistMenu();
                case"4"-> {
                    System.out.println("Thank you!\nSee you soon!");
                    return;
                }
                default -> System.out.println("Invalid choice choose again");

            }
        }
    }
    static void GuestMenu(){
        System.out.println("What would you like to do?");
        System.out.println("1:Register(new guest)");
        System.out.println("2:Login");
        String choice=scanner.nextLine().trim();
        switch (choice){
            case"1"->regesterGuest();
            case"2"-> {
                Guest g= loginGuest();
                if(g!=null)
                    guestDashboard(g);
            }
            default -> {
                System.out.println("invalid input");
                return;
            }
        }
    }
    static void regesterGuest(){
        try {


            System.out.print("Create username: ");
            String username = scanner.nextLine();
            System.out.print("Create password(size>6): ");
            String password = scanner.nextLine();
            System.out.print("Enter your balance: ");
            double balance=Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter Date of Birth (dd/MM/yyyy): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine().trim(), formatter);
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Gender (MALE/FEMALE): ");
            Gender gender = Gender.valueOf(scanner.nextLine().trim().toUpperCase());

            Guest newGuest=Guest.register(username,password,dob,address,gender,balance);
            System.out.println("Guest regestered successfully");
            System.out.println("Do you want the guest Dashboard(yes/no): ");
            System.out.println("No means you will exit the program");
            String input=scanner.nextLine();
            switch (input){
                case"yes"->guestDashboard(newGuest);
                case"no"-> {
                    return;
                }
                default ->{
                    System.out.println("invalid input");
                    return;
                }


            }

        }
        catch (InvalidUserInformationException e){
            System.out.println("regestration failed"+e.getMessage());
        }catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use dd/MM/yyyy");
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid input"+e.getMessage());

        }

    }
    static Guest loginGuest(){
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        Guest found=HotelDataBase.findGuestByUsername(username);
        if(found!=null && found.login(username,password)) {
            System.out.println("Login successful");
            return found;
        }
        System.out.println("invalid username or password");
        return null;

    }
    static void guestDashboard(Guest guest){
        while (true) {
            System.out.println("===================================");
            System.out.println("    WELCOME TO GUEST DASHBOARD!");
            System.out.println("===================================");
            System.out.println("WHAT WOULD YOU LIKE TO DO?");
            System.out.println("1-View available rooms");
            System.out.println("2-Make reservation");
            System.out.println("3-View reservation");
            System.out.println("4-Cancel reservation");
            System.out.println("5-Logout");
            String Input = scanner.nextLine().trim();
            switch (Input) {
                case "1" -> ViewAvailableRooms(guest);
                case "2" -> MakeReservation(guest);
                case "3" -> viewReservations(guest);
                case "4" -> CancelReservation(guest);
                case "5" -> {
                    System.out.println("logged out!");
                    return;
                }
                default -> {
                    System.out.println("invalid input");
                    return;
                }
            }
        }
    }
    static void ViewAvailableRooms(Guest guest){
        ArrayList<Room> rooms = guest.viewAvailableRooms();
        if (rooms.isEmpty()){
            System.out.println("NO AVAILABLE ROOMS!");
        }
        else{
            System.out.println("THIS IS THE AVAILABLE ROOMS");
            for (Room r : rooms) System.out.println(r);
        }
    }
    static void MakeReservation(Guest guest){
            try {
                ViewAvailableRooms(guest);
                System.out.print("Enter room number: ");
                int roomNumber = Integer.parseInt(scanner.nextLine().trim());

                Room room = HotelDataBase.findRoomByRoomNumber(roomNumber);
                if (room == null) {
                    System.out.println("Room not found.");
                    return;
                }

                System.out.print("Check-in date (dd/MM/yyyy): ");
                LocalDate checkIn = LocalDate.parse(scanner.nextLine().trim(), formatter);
                System.out.print("Check-out date (dd/MM/yyyy): ");
                LocalDate checkOut = LocalDate.parse(scanner.nextLine().trim(), formatter);

                Reservation res = guest.makeReservation(room, checkIn, checkOut);
                room.bookRoom();
                System.out.println("Reservation made successfully!\n" + res);

            } catch (NumberFormatException e) {
                System.out.println("Invalid room number, please enter a number.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use dd/MM/yyyy");
            } catch (ReservationNotFoundException | InvalidDateRangeException e) {
                System.out.println("Reservation failed: " + e.getMessage());
            } catch (RoomNotAvailableException e) {
                throw new RuntimeException(e);
            }
    }



    static void viewReservations(Guest guest){
        ArrayList<Reservation> reservations = guest.viewReservations();
        if (reservations.isEmpty()) {
            System.out.println("NO RESERVATIONS FOUND!!");
        }
        else{
            for(Reservation r : reservations)
                System.out.println(r);
        }
    }
    static void CancelReservation(Guest guest){
        try{
            System.out.print("Enter reservation Id: ");
            int reservation_ID = Integer.parseInt(scanner.nextLine().trim());
            guest.cancelReservation(reservation_ID);
            System.out.println("RESERVATION CANCELLED SUCCESSFULLY.");
        }
        catch (ReservationNotFoundException | InvalidReservationStatusException e){
            System.out.println(e.getMessage());
        }
    }
}
