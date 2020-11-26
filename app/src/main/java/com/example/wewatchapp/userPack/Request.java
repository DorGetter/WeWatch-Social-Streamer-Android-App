package com.example.wewatchapp.userPack;

/* movie request class */
public class Request {

    private String category;
    private String movieName;
    private String userName;
    private String requestID;

    /* open/closed */
    private String status;


    public Request() {

    }

    public Request(String category, String movieName, String userName, String requestID){
        this.category = category;
        this.movieName = movieName;
        this.userName = userName;
        this.requestID = requestID;

        this.status = "OPEN";
    }


    /* getters */
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

    public String getStatus() {
        return status;
    }



    /* setters */
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

    public void setStatus(String _status) {
        this.status = _status;
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


