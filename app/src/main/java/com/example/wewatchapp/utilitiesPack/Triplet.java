package com.example.wewatchapp.utilitiesPack;

import java.util.Date;

/* class of single watch */
public class Triplet {
    private String name;
    private String movie;
    private Date date;

    public Triplet(String name, String movie, Date date) {
        this.name = name;
        this.movie = movie;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
