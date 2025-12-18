package org.example.models;

public class Hall implements Prototype{
    private int id;
    private String name;
    private int capacity;

    public Hall(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }
    public int getCapacity(){
        return capacity;
    }

    @Override
    public Hall getClone() {
        return new Hall(this.id,this.name,this.capacity);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
