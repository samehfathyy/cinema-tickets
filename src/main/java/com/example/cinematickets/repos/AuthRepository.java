package com.example.cinematickets.repos;

import com.example.cinematickets.models.User;

import java.util.ArrayList;
import java.util.List;

public class AuthRepository {
    private static AuthRepository repo;
<<<<<<< HEAD
    private AuthRepository(){
        users = new ArrayList<>();
    }
=======
    private AuthRepository(){}
>>>>>>> 3d4c96a11ebec8c6621f05d575f7f5d588c933eb
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
<<<<<<< HEAD
    public boolean login(String username, String password){
=======
    public boolean login(String username,String password){
>>>>>>> 3d4c96a11ebec8c6621f05d575f7f5d588c933eb
        for (User user : users) {
            if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
                CurrentUser=user;
                return true;
            }
        }
        return false;
    }

}
