package com.example.wewatchapp.utilitiesPack;


import java.util.Date;

//
public class Views {

    private String movieName;
    private String userName;
    private String viewID;
    //private long dateInLong;
    private Date date;


    public Views() {

    }

    public Views(String movieName, String userName, Long ll){
        this.movieName = movieName;
        this.userName = userName;
        //this.dateInLong = System.currentTimeMillis();
        this.date = new Date(ll);
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Views{" +
                "movieName='" + movieName + '\'' +
                ", userName='" + userName + '\'' +
                ", viewID='" + viewID + '\'' +
                '}';
    }
}
