package com.hotel.models;
import com.hotel.enums.Role;
import java.time.LocalDate;


public class Admin extends Staff {

    public Admin(int id, String username, String password, LocalDate dateOfBirth, int workingHours) {
        super(id, username, password, dateOfBirth, Role.ADMIN, workingHours);
    }

}