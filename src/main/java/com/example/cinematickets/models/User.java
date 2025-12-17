package org.example.models;

public class User {
    public int id;
    String name;
    private String username;
    private String password;

    public User(String name, int id, String username, String password) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User( String username, String password) {
        this.name = "name";
        this.id = 0;
        this.username = username;
        this.password = password;
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
}
