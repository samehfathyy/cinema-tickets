package com.example.cinematickets.repos;

import com.example.cinematickets.models.User;

import java.util.ArrayList;
import java.util.List;

public class AuthRepository {
    private static AuthRepository repo;
    private AuthRepository(){
        users = new ArrayList<>();
    }
    public static AuthRepository getInstance() {
        if(repo==null){
            repo = new AuthRepository();
        }
        return repo;
    }
    private List<User> users;
    private User CurrentUser;

    //return true if user registered
    //false if username is already used by another user
    public boolean registerNewUser(User user){
        //search for username
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        user.id=users.size();
        users.add(user);
        return true;
    }


    //return true if logged in
    //false if user or password incorrect
    public boolean login(String username, String password){
        for (User user : users) {
            if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
                CurrentUser=user;
                return true;
            }
        }
        return false;
    }

}
