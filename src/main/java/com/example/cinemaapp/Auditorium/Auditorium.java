package com.example.cinemaapp.Auditorium;

public class Auditorium {
    public String name;
    public Number seats;
    public String id;

    public Auditorium(String name, Number seats) {
        this.name = name;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return name;
    }
}
