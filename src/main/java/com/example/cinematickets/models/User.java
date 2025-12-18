package com.example.cinematickets.models;

public class User {
    public int id;
    String name;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String name, int id, String username, String password, boolean isAdmin) { //for admin creation
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    public User( String username, String password) { //for login
        this.name = "name";
        this.id = 0;
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }




    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
