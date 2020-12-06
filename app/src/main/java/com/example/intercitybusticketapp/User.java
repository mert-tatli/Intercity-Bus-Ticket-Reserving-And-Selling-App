package com.example.intercitybusticketapp;

public class User {

    private  String id;
    private String name;
    private String surname;
    private String gender;
    private String phone;
    private String birthday;
    private  String email;
    private String password;
    public User(String id, String name, String surname, String gender, String phone, String birthday, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phone = phone;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
    }
}
