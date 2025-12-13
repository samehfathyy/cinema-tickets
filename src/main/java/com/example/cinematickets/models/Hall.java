package com.example.cinematickets.models;

public class Hall {
    public int id;
    public String name;
    public int capacity;

    public Hall(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }
    public int getCapacity(){
        return capacity;
    }
}
