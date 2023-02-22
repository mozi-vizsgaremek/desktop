package com.example.cinemaapp;

import java.time.LocalDate;

public class Person {

    public int getId() {
        return id;
    }

    public int getHourly_wage() {
        return hourly_wage;
    }

    public int getManager_id() {
        return manager_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getReg_date() {
        return reg_date;
    }

    public LocalDate getHire_date() {
        return hire_date;
    }

    private int id, hourly_wage, manager_id;
    private String username, password, first_name, last_name, role;
    private LocalDate reg_date;
    private LocalDate hire_date;

    public Person(int id, String username, String password, String first_name,
                  String last_name, int hourly_wage, LocalDate reg_date,
                  LocalDate hire_date, String role, int manager_id) {

        this.id = id;
        this.hourly_wage = hourly_wage;
        this.manager_id = manager_id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.reg_date = reg_date;
        this.hire_date = hire_date;
    }

    @Override
    public String toString() {
        return first_name + " " + last_name;
    }
}
