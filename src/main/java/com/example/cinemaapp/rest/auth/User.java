package com.example.cinemaapp.rest.auth;

public class User {
    public enum Role {
        Customer,
        Employee,
        Manager,
        Admin
    }

    public String username;
    public String firstName;
    public String lastName;
    public int exp; // expiry
    public Role role;

    public void setRole(String role) {
        switch (role) {
            case "employee":
                this.role = Role.Employee;
                break;
            case "manager":
                this.role = Role.Manager;
                break;
            case "admin":
                this.role = Role.Admin;
                break;
            default:
                this.role = Role.Customer;
        }
    }
}
