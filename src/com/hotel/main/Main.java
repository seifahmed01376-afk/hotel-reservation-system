package com.hotel.main;

import com.hotel.Exceptions.InvalidDateRangeException;
import com.hotel.Exceptions.InvalidRoomDataException;
import com.hotel.Exceptions.InvalidUserInformationException;
import com.hotel.Validation.validator;
import com.hotel.database.HotelDataBase;
import com.hotel.enums.Gender;
import com.hotel.models.Guest;
import com.hotel.models.Receptionist;
import com.hotel.models.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

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
        System.out.println("1:Regester(new guest)");
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
            System.out.println("Login successfull");
            return found;
        }
        System.out.println("invalid username or password");
        return null;

    }
    static void guestDashboard(Guest guest){
        System.out.println("WELCOME TO GUEST DASHBOARD!");
        System.out.println("WHAT WOULD YOU LIKE TO DO?");
        System.out.println("1-View available rooms");
        System.out.println("2-Make reservation");
        System.out.println("3-View reservation");
        System.out.println("4-Cancel reservation");
        String Input = scanner.nextLine().trim();
        switch(Input){
            case"1"-> ViewAvailableRooms();
            case"2"-> MakeReservation();
            case"3"-> viewReservations();
            case"4" -> CancelReservation();
            default -> {
                System.out.println("invalid input");
                return;
            }
        }
    }
    static void ViewAvailableRooms(){}
    static void MakeReservation(){}
    static void viewReservations(){}
    static void CancelReservation(){}
}
