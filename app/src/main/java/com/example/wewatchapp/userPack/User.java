package com.example.wewatchapp.userPack;

public class User {

    private String fullName;
    private int age;
    private String email;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, int age , String email){
        this.fullName   = fullName;
        this.age        = age;
        this.email      = email;
    }


    public int getAge() {
        return age;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

}