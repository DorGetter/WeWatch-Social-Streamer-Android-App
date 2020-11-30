package com.example.wewatchapp.utilitiesPack;

public class MovieCounterView {

    String movie_counter_view_id, movie_name;
    int counter;

    public MovieCounterView(String movie_counter_view_id, String movie_name, int counter) {
        this.movie_counter_view_id = movie_counter_view_id;
        this.movie_name = movie_name;
        this.counter = counter;
    }

    public MovieCounterView() {
    }

    public String getMovie_counter_view_id() {
        return movie_counter_view_id;
    }

    public void setMovie_counter_view_id(String movie_counter_view_id) {
        this.movie_counter_view_id = movie_counter_view_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
