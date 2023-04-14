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
    public String role;
}
