package com.hotel.main;

import com.hotel.Exceptions.*;
import com.hotel.Validation.validator;
import com.hotel.database.HotelDataBase;
import com.hotel.enums.Gender;
import com.hotel.enums.PaymentMethod;
import com.hotel.enums.Reservationstatus;
import com.hotel.models.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.hotel.database.HotelDataBase.*;
import static com.hotel.models.Admin.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws InvalidRoomDataException {
        HotelDataBase.initialize();
        System.out.println("========================================");
        System.out.println("welcome to the hotel-reservation-system!");
        System.out.println("========================================");
        while (true) {
            System.out.println("Who are you?");
            System.out.println("1:Guest");
            System.out.println("2:Admin");
            System.out.println("3:Receptionist");
            System.out.println("4:Exit");
            System.out.print("Enter number: ");
            String menu = scanner.nextLine().trim();
            switch (menu) {
                case "1" -> GuestMenu();
                case "2" -> AdminMenu();
                case "3" -> ReceptionistMenu();//ReceptionistMenu();
                case "4" -> {
                    System.out.println("Thank you!\nSee you soon!");
                    return;
                }
                default -> System.out.println("Invalid choice choose again");

            }
        }
    }

    static void GuestMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1:Register(new guest)");
        System.out.println("2:Login");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> regesterGuest();
            case "2" -> {
                Guest g = loginGuest();
                if (g != null)
                    guestDashboard(g);
            }
            default -> {
                System.out.println("invalid input");
                return;
            }
        }
    }

    static void regesterGuest() {
        try {


            System.out.print("Create username: ");
            String username = scanner.nextLine();
            System.out.print("Create password(size>6): ");
            String password = scanner.nextLine();
            System.out.print("Enter your balance: ");
            double balance = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter Date of Birth (dd/MM/yyyy): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine().trim(), formatter);
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Gender (MALE/FEMALE): ");
            Gender gender = Gender.valueOf(scanner.nextLine().trim().toUpperCase());

            Guest newGuest = Guest.register(username, password, dob, address, gender, balance);
            System.out.println("Guest regestered successfully");
            System.out.println("Do you want the guest Dashboard(yes/no): ");
            System.out.println("No means you will exit the program");
            String input = scanner.nextLine();
            switch (input) {
                case "yes" -> guestDashboard(newGuest);
                case "no" -> {
                    return;
                }
                default -> {
                    System.out.println("invalid input");
                    return;
                }


            }

        } catch (InvalidUserInformationException e) {
            System.out.println("regestration failed" + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use dd/MM/yyyy");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input" + e.getMessage());

        }

    }

    static Guest loginGuest() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        Guest found = HotelDataBase.findGuestByUsername(username);
        if (found != null && found.login(username, password)) {
            System.out.println("Login successful");
            return found;
        }
        System.out.println("invalid username or password");
        return null;

    }

    static void guestDashboard(Guest guest) {
        while (true) {
            System.out.println("===================================");
            System.out.println("    WELCOME TO GUEST DASHBOARD!");
            System.out.println("===================================");
            System.out.println("WHAT WOULD YOU LIKE TO DO?");
            System.out.println("1-View available rooms");
            System.out.println("2-Make reservation");
            System.out.println("3-View reservation");
            System.out.println("4-Cancel reservation");
            System.out.println("5-Check out");
            System.out.println("Pay Invoice");
            System.out.println("7-Logout");
            String Input = scanner.nextLine().trim();
            switch (Input) {
                case "1" -> ViewAvailableRooms(guest);
                case "2" -> MakeReservation(guest);
                case "3" -> viewReservations(guest);
                case "4" -> CancelReservation(guest);
                case "5" -> guestCheckout(guest);
                case "6" -> payInvoice(guest);
                case "7" -> {
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

    static void ViewAvailableRooms(Guest guest) {
        ArrayList<Room> rooms = guest.viewAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("NO AVAILABLE ROOMS!");
        } else {
            System.out.println("THIS IS THE AVAILABLE ROOMS");
            for (Room r : rooms) System.out.println(r);
        }
    }

    static void MakeReservation(Guest guest) {
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


    static void viewReservations(Guest guest) {
        ArrayList<Reservation> reservations = guest.viewReservations();
        if (reservations.isEmpty()) {
            System.out.println("NO RESERVATIONS FOUND!!");
        } else {
            for (Reservation r : reservations)
                System.out.println(r);
        }
    }

    static void CancelReservation(Guest guest) {
        try {
            System.out.print("Enter reservation Id: ");
            int reservation_ID = Integer.parseInt(scanner.nextLine().trim());
            guest.cancelReservation(reservation_ID);
            System.out.println("RESERVATION CANCELLED SUCCESSFULLY.");
        } catch (ReservationNotFoundException | InvalidReservationStatusException e) {
            System.out.println(e.getMessage());
        }
    }

    static void guestCheckout(Guest guest) {
        try {
            viewReservations(guest);
            System.out.print("Enter reservation ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Payment Method:");
            System.out.println("1. CASH");
            System.out.println("2. CREDIT_CARD");
            System.out.println("3. ONLINE");
            System.out.print("Choice: ");
            PaymentMethod method = switch (scanner.nextLine().trim()) {
                case "1" -> PaymentMethod.CASH;
                case "2" -> PaymentMethod.CREDIT_CARD;
                case "3" -> PaymentMethod.ONLINE;
                default -> throw new IllegalArgumentException("Invalid payment method.");
            };

            Invoice invoice = guest.checkOut(id, method);
            if (invoice != null)
                System.out.println("Checkout successful!\n" + invoice);

        } catch (ReservationNotFoundException | InvalidInvoiceAmountException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID, please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static void payInvoice(Guest guest) {
        try {
            viewReservations(guest);
            System.out.print("Enter reservation ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Payment Method:");
            System.out.println("1. CASH");
            System.out.println("2. CREDIT_CARD");
            System.out.println("3. ONLINE");
            System.out.print("Choice: ");
            PaymentMethod method = switch (scanner.nextLine().trim()) {
                case "1" -> PaymentMethod.CASH;
                case "2" -> PaymentMethod.CREDIT_CARD;
                case "3" -> PaymentMethod.ONLINE;
                default -> throw new IllegalArgumentException("Invalid payment method.");
            };

            guest.payInvoice(id, method);
            System.out.println("Payment successful! Remaining balance: $" + guest.getBalance());

        } catch (InvalidPaymentException e) {
            System.out.println("Payment failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID, please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static Admin LoginAdmin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        Admin found = HotelDataBase.findAdminByUsername(username);
        if (found != null && found.login(username, password)) {
            System.out.println("login successful");
            return found;
        }
        System.out.println("invalid username or password");
        return null;
    }

    static void AdminMenu() {
        System.out.println("Please login");
        Admin admin = LoginAdmin();
        if (admin == null)
            return;
        adminDashboard(admin);
    }

    static void adminDashboard(Admin admin) {
        while (true) {
            System.out.println("===================================");
            System.out.println("       ADMIN DASHBOARD");
            System.out.println("===================================");
            System.out.println("1.  View all");
            System.out.println("2.  Add room");
            System.out.println("3.  Delete room");
            System.out.println("4.  Update room");
            System.out.println("5.  Add amenity");
            System.out.println("6.  Delete amenity");
            System.out.println("7.  Update amenity");
            System.out.println("8.  Add room type");
            System.out.println("9.  Delete room type");
            System.out.println("10. Update room type");
            System.out.println("11. Regester Receptionist");
            System.out.println("12. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> admin.viewAll();
                case "2" -> addRoom();
                case "3" -> deleteRoom(admin);
                case "4" -> updateRoom();
                case "5" -> addAmenity();
                case "6" -> deleteAmenity(admin);
                case "7" -> updateAmenity();
                case "8" -> addRoomType();
                case "9" -> deleteRoomType(admin);
                case "10" -> updateRoomType();
                case "11" ->RegisterReceptionist();
                case "12" -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addRoom() {
        try {
            System.out.print("Enter room number: ");
            int roomNumber = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter price per night: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter floor: ");
            int floor = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Available room types:");
            for (RoomType rt : HotelDataBase.roomTypes)
                System.out.println(rt);
            System.out.print("Enter room type ID: ");
            int typeId = Integer.parseInt(scanner.nextLine().trim());
            RoomType type = HotelDataBase.findRoomTypeById(typeId);

            if (type == null) {
                System.out.println("Room type not found.");
                return;
            }

            Admin.addRoom(roomNumber, price, type, floor);

        } catch (InvalidRoomDataException e) {
            System.out.println("Failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        }
    }

    static void deleteRoom(Admin admin) {
        try {
            System.out.print("Enter room number to delete: ");
            int roomNumber = Integer.parseInt(scanner.nextLine().trim());
            admin.deleteRoom(roomNumber);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        }
    }

    static void updateRoom() {
        try {
            System.out.print("Enter room number to update: ");
            int roomNumber = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter new price per night: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.println("Available room types:");
            for (RoomType rt : HotelDataBase.roomTypes)
                System.out.println(rt);
            System.out.print("Enter new room type ID: ");
            int typeId = Integer.parseInt(scanner.nextLine().trim());
            RoomType type = HotelDataBase.findRoomTypeById(typeId);

            if (type == null) {
                System.out.println("Room type not found.");
                return;
            }

            Admin.updateRoom(roomNumber, price, type, new ArrayList<>());
            System.out.println("Room updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        } catch (InvalidRoomDataException e) {
            System.out.println("Failed" + e.getMessage());
        }

    }

    static void addAmenity() {
        System.out.print("Enter amenity name: ");
        String name = scanner.nextLine().trim();
        Admin.addAmenity(name);
        System.out.println("Amenity added successfully.");
    }

    static void deleteAmenity(Admin admin) {
        try {
            System.out.println("Available amenities:");
            for (Amenity a : HotelDataBase.amenities)
                System.out.println(a);
            System.out.print("Enter amenity ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            admin.deleteAmenity(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        }
    }

    static void updateAmenity() {
        try {
            System.out.println("Available amenities:");
            for (Amenity a : HotelDataBase.amenities)
                System.out.println(a);
            System.out.print("Enter amenity ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter new amenity name: ");
            String name = scanner.nextLine().trim();
            Admin.updateAmenity(id, name);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        }
    }

    static void addRoomType() {
        System.out.print("Enter room type name: ");
        String name = scanner.nextLine().trim();
        Admin.addRoomType(name);
    }

    static void deleteRoomType(Admin admin) {
        try {
            System.out.println("Available room types:");
            for (RoomType rt : HotelDataBase.roomTypes)
                System.out.println(rt);
            System.out.print("Enter room type ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            admin.deleteRoomType(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        }
    }

    static void updateRoomType() {
        try {
            System.out.println("Available room types:");
            for (RoomType rt : HotelDataBase.roomTypes)
                System.out.println(rt);
            System.out.print("Enter room type ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter new room type name: ");
            String name = scanner.nextLine().trim();
            Admin.updateRoomType(id, name);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number.");
        }
    }
    static void RegisterReceptionist(){
        try {


            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();
            System.out.println("Enter dateOfBirth: ");
            LocalDate dob = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Enter workingHours: ");
            int workingHours = Integer.parseInt(scanner.nextLine().trim());

            Receptionist newReceptionist = Receptionist.Register(username, password, dob, workingHours);
        }
        catch (InvalidUserInformationException e) {
            System.out.println("regestration failed" + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use dd/MM/yyyy");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input" + e.getMessage());

        }

    }


    static void ReceptionistMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1:Login");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1" -> LoginReceptionist();
            default -> {
                System.out.println("invalid input");
                return;
            }
        }
    }

    static Receptionist LoginReceptionist() {
        System.out.println("Enter username:");
        String username = scanner.nextLine().trim();
        System.out.println("Enter Password:");
        String password = scanner.nextLine().trim();

        Receptionist found = HotelDataBase.findReceptionistByUsername(username);
        if (found != null && found.login(username, password)) {
            System.out.println("Log in successful");
            ReceptionistDashboard(found);
            return found;
        }
        System.out.println("Invalid username or password!");
        return null;
    }

    static void ReceptionistDashboard(Receptionist receptionist) {
        while (true) {
            System.out.println("==========================================");
            System.out.println("    WELCOME TO RECEPTIONIST DASHBOARD!");
            System.out.println("==========================================");
            System.out.println("What would you like to do?");
            System.out.println("1-Check-In");
            System.out.println("2-Check-Out");
            System.out.println("3-View today check-in");
            System.out.println("4-View today check-out");
            System.out.println("5-View all guests");
            System.out.println("6-View all reservations");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> checkinreceptionist(receptionist);
                case "2" -> checkoutreceptionist(receptionist);
                case "3" -> viewtodaycheckin(receptionist);
                case "4" -> viewtodaycheckout(receptionist);
                case "5" -> viewallguests(receptionist);
                case "6" -> viewallreservations();
                default -> {
                    System.out.println("invalid input!");
                    return;
                }
            }
        }

    }

    static void checkinreceptionist(Receptionist receptionist){
        System.out.println("Enter reservation ID:");
        int reservation_id = scanner.nextInt();
        try{
            receptionist.checkIn(reservation_id);
        }
        catch(ReservationNotFoundException | InvalidReservationStatusException e ){
            System.out.println(e.getMessage());
        }
    }
    static void checkoutreceptionist(Receptionist receptionist){
        System.out.println("Enter reservation ID:");
        int reservation_id = scanner.nextInt();
        try{
            Invoice invoice = receptionist.checkout(reservation_id);
            System.out.println("Checkout Successful!");
            System.out.println("Total amount = " + invoice.getTotalAmount());
        }
        catch(ReservationNotFoundException | InvalidReservationStatusException | InvalidInvoiceAmountException e){
            System.out.println(e.getMessage());
        }
    }
    static void viewtodaycheckin(Receptionist receptionist){
        ArrayList<Reservation> todaylist = receptionist.viewTodayCheckIns();
        if(todaylist.isEmpty()){
            System.out.println("No check-in today:(");
            return;
        }
        System.out.println("Today's check-in:");
        for(Reservation res : todaylist){
            System.out.println(res);
        }
    }
    static void viewtodaycheckout(Receptionist receptionist){
        ArrayList<Reservation> todaylist = receptionist.viewTodayCheckOuts();
        if (todaylist.isEmpty()){
            System.out.println("No check-out today:)");
            return;
        }
        System.out.println("Today's check-out:");
        for (Reservation res : todaylist){
            System.out.println(res);
        }
    }
    static void viewallguests(Receptionist receptionist){
        ArrayList<Guest> allguests = receptionist.viewAllGuests();
        if (allguests.isEmpty()){
            System.out.println("No Guests found!");
            return;
        }
        System.out.println("All The Guests: ");
        for (Guest g : allguests){
            System.out.println(g);
        }
    }
    static void viewallreservations(){
        for(Reservation r:reservations)
            System.out.println(r);
    }



}
