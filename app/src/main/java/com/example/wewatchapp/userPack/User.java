package com.example.wewatchapp.userPack;

import java.util.ArrayList;

public class User {

    private String fullName;
    private int age;
    private String email;
    private ArrayList<String> log = new ArrayList<String>();;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, int age , String email){
        this.fullName   = fullName;
        this.age        = age;
        this.email      = email;
        //this.log = new ArrayList<String>();
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

    public ArrayList<String> getLog() { return log; }

    protected void Logit(String to_log){
        log.add("I Just "+to_log+"\n");
    }

}