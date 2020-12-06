package com.example.intercitybusticketapp;

import java.util.ArrayList;

public class UserModel {

    private ArrayList<User> users;

    public UserModel() {
        users=new ArrayList<>();
    }

    public void setUsers(User user) {
        users.add(user);
    }
}
