package com.hotel.Validation;
// Guest
public class validator {
    public void setUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty!");
        if (username.length() < 3)
            throw new IllegalArgumentException("Username must be at least 3 characters!");
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be empty!");
        if (password.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters!");
        this.password = password;
    }

    public void setBalance(double balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Balance cannot be negative!");
        this.balance = balance;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth cannot be null!");
        if (dateOfBirth.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date of birth cannot be in the future!");
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(18)))
            throw new IllegalArgumentException("Guest must be at least 18 years old!");
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        if (gender == null)
            throw new IllegalArgumentException("Gender cannot be null!");
        this.gender = gender;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("Address cannot be empty!");
        this.address = address;
    }

}
//end  Guest
// room
public void setRoomNumber(int roomNumber) {
    if (roomNumber <= 0)
        throw new IllegalArgumentException("Room number must be positive!");
    this.roomNumber = roomNumber;
}

public void setPricePerNight(double price) {
    if (price <= 0)
        throw new IllegalArgumentException("Price must be positive!");
    this.pricePerNight = price;
}

public void setRoomType(RoomType roomType) {
    if (roomType == null)
        throw new IllegalArgumentException("Room type cannot be null!");
    this.roomType = roomType;
}
//end room

//Reservation
public void setCheckInDate(LocalDate checkInDate) {
    if (checkInDate == null)
        throw new IllegalArgumentException("Check-in date cannot be null!");
    if (checkInDate.isBefore(LocalDate.now()))
        throw new IllegalArgumentException("Check-in cannot be in the past!");
    this.checkInDate = checkInDate;
}

public void setCheckOutDate(LocalDate checkOutDate) {
    if (checkOutDate == null)
        throw new IllegalArgumentException("Check-out date cannot be null!");
    if (checkOutDate.isBefore(this.checkInDate) || checkOutDate.isEqual(this.checkInDate))
        throw new IllegalArgumentException("Check-out must be after check-in!");
    this.checkOutDate = checkOutDate;
}

public void setRoom(Room room) throws RoomNotAvailableException {
    if (room == null)
        throw new IllegalArgumentException("Room cannot be null!");
    if (!room.isAvailable())
        throw new RoomNotAvailableException("Room " + room.getRoomNumber() + " is not available!");
    this.room = room;
}
//end Reservation


//Invoice
public void setTotalAmount(double amount) throws InvalidPaymentException {
    if (amount <= 0)                                  // Custom Exception
        throw new InvalidPaymentException("Amount must be positive!");
    this.totalAmount = amount;
}

public void setPaymentMethod(PaymentMethod method) throws InvalidPaymentException {
    if (method == null)                               // Custom Exception
        throw new InvalidPaymentException("Payment method cannot be null!");
    this.paymentMethod = method;
}

public void setPaymentDate(LocalDate date) {
    if (date == null)
        throw new IllegalArgumentException("Payment date cannot be null!");
    if (date.isAfter(LocalDate.now()))
        throw new IllegalArgumentException("Payment date cannot be in the future!");
    this.paymentDate = date;
}
//end Invoice






public void setWorkingHours(int hours) {
    if (hours < 0 || hours > 24)
        throw new IllegalArgumentException("Working hours must be between 0 and 24!");
    this.workingHours = hours;
}

public void setRole(Role role) {
    if (role == null)
        throw new IllegalArgumentException("Role cannot be null!");
    this.role = role;
}

//end all
