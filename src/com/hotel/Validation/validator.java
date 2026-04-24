package com.hotel.Validation;

import com.hotel.Exceptions.InvalidInvoiceAmountException;
import com.hotel.Exceptions.InvalidPaymentException;
import com.hotel.Exceptions.InvalidRoomDataException;
import com.hotel.Exceptions.RoomNotAvailableException;
import com.hotel.enums.Gender;
import com.hotel.enums.PaymentMethod;
import com.hotel.enums.Role;
import com.hotel.models.Room;
import com.hotel.models.RoomType;
import java.time.LocalDate;

public class validator {

    // ── Guest Validation ──────────────────────────
    public static void validateUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty!");
        if (username.length() < 3)
            throw new IllegalArgumentException("Username must be at least 3 characters!");
    }

    public static void validatePassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty!");
        if (password.length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters!");
    }

    public static void validateBalance(double balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Balance cannot be negative!");
    }

    public static void validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        if (dateOfBirth.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of birth cannot be in the future!");
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(18)))
            throw new IllegalArgumentException("Guest must be at least 18 years old!");
    }

    public static void validateGender(Gender gender) {
        if (gender == null)
            throw new IllegalArgumentException("Gender cannot be null!");
    }

    public static void validateAddress(String address) {
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("Address cannot be empty!");
    }

    // ── Room Validation ───────────────────────────
    public static void validateRoomNumber(int roomNumber) throws RoomNotAvailableException {
        if (roomNumber <= 0)
            throw new RoomNotAvailableException("Room number must be positive!");
    }

    public static void validatePrice(double price) throws InvalidRoomDataException {
        if (price <= 0)
            throw new InvalidRoomDataException ("Price must be positive!");
    }

    public static void validateRoomType(RoomType roomType) {
        if (roomType == null)
            throw new IllegalArgumentException("Room type cannot be null!");
    }

    // ── Reservation Validation ────────────────────
    public static void validateCheckInDate(LocalDate checkInDate) {
        if (checkInDate == null)
            throw new IllegalArgumentException("Check-in date cannot be null!");
        if (checkInDate.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Check-in cannot be in the past!");
    }

    public static void validateCheckOutDate(LocalDate checkOutDate, LocalDate checkInDate) {
        if (checkOutDate == null)
            throw new IllegalArgumentException("Check-out date cannot be null!");
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate))
            throw new IllegalArgumentException("Check-out must be after check-in!");
    }

    public static void validateRoom(Room room) throws RoomNotAvailableException {
        if (room == null)
            throw new IllegalArgumentException("Room cannot be null!");
        if (!room.isAvailable())
            throw new RoomNotAvailableException("Room " + room.getRoomNumber() + " is not available!");
    }

    // ── Invoice Validation ────────────────────────
    public static void validateAmount(double amount) throws  InvalidInvoiceAmountException {
        if (amount <= 0)
            throw new InvalidInvoiceAmountException( "Amount must be positive!");
    }

    public static void validatePaymentMethod(PaymentMethod method) throws InvalidPaymentException {
        if (method == null)
            throw new InvalidPaymentException("Payment method cannot be null!");
    }

    public static void validatePaymentDate(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException("Payment date cannot be null!");
        if (date.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Payment date cannot be in the future!");
    }

    // ── Staff Validation ──────────────────────────
    public static void validateWorkingHours(int hours) {
        if (hours < 0 || hours > 24)
            throw new IllegalArgumentException("Working hours must be between 0 and 24!");
    }

    public static void validateRole(Role role) {
        if (role == null)
            throw new IllegalArgumentException("Role cannot be null!");
    }
}