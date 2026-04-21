package com.hotel.Validation;

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
