package com.example.wewatchapp.userPack;

import java.util.ArrayList;

/* this class represents a friend object which can be add to (user friends 'list') */
public class Friends_List {

    private String name;
    private String id;


    public Friends_List() {

    }

    public Friends_List(String name_, String id_) {
        this.name = name_;
        this.id = id_;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
