package com.example.wewatchapp.userPack;

/* movie request class */
public class Request {

    private String category;
    private String movieName;
    private String userName;
    private String requestID;


    public Request() {

    }

    public Request(String category, String movieName, String userName, String requestID){
        this.category = category;
        this.movieName = movieName;
        this.userName = userName;
        this.requestID = requestID;
    }

    public String getCategory() {
        return category;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRequestID() {
        return requestID;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    @Override
    public String toString() {
        return "Request{" +
                "category='" + category + '\'' +
                ", movieName='" + movieName + '\'' +
                ", userName='" + userName + '\'' +
                ", requestID='" + requestID + '\'' +
                '}';
    }
}
