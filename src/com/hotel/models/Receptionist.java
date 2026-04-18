package com.hotel.models;

import com.hotel.enums.Role;

import java.time.LocalDate;

public class Receptionist extends Staff {
    public Receptionist(int id, String username, String password, LocalDate dateOfBirth,int workingHours){
        super(id,username,password,dateOfBirth, Role.RECEPTIONIST,workingHours);
    }
}
