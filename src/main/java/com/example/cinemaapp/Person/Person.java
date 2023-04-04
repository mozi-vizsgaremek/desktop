package com.example.cinemaapp.Person;

import java.time.LocalDateTime;

public class Person {

    public int getHourlyWage() {
        return hourlyWage;
    }

    public String getManager_id() {
        return managerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getRegistrationDate() {  //nem lehet megv.
        return registrationDate;
    }

    public LocalDateTime getHireDate() { //nem lehet megv.
        return hireDate;
    }

    public String getId() {
        return id;
    }

    public int hourlyWage;
    public String id, managerId, username, password, firstName, lastName, role;
    public LocalDateTime registrationDate;
    public LocalDateTime hireDate;

    public Person(String id, String username, String password, String first_name,
                  String last_name, int hourly_wage, LocalDateTime reg_date,
                  LocalDateTime hire_date, String role, String manager_id) {

        this.id = id;
        this.hourlyWage = hourly_wage;
        this.managerId = manager_id;
        this.username = username;
        this.password = password;
        this.firstName = first_name;
        this.lastName = last_name;
        this.role = role;
        this.registrationDate = reg_date;
        this.hireDate = hire_date;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
