package com.example.wewatchapp.utilitiesPack;


//
public class Views {

    private String movieName;
    private String userName;
    private String viewID;


    public Views() {

    }

    public Views(String movieName, String userName){
        this.movieName = movieName;
        this.userName = userName;
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

    @Override
    public String toString() {
        return "Views{" +
                "movieName='" + movieName + '\'' +
                ", userName='" + userName + '\'' +
                ", viewID='" + viewID + '\'' +
                '}';
    }
}
