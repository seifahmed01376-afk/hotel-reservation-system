package com.hotel.models;
import com.hotel.enums.Role;
import java.time.LocalDate;

public abstract class Staff {
    private int id;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
    private int workingHours;

   public Staff(int id,String username,String password,LocalDate dateOfBirth,Role role,int workingHours){
        this.id=id;
        this.password=password;
        this.username=username;
        this.dateOfBirth=dateOfBirth;
        this.role=role;
        this.workingHours=workingHours;
    }

    public int getId() {
        return id;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public Role getRole() {
        return role;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString(){
       return "Username: "+this.username+"-ID: "+this.id;
    }
}
