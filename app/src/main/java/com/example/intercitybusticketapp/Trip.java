package com.example.intercitybusticketapp;

import android.widget.EditText;

import androidx.annotation.NonNull;

public class Trip {

    private  String tripid;
    private  String from;
    private String to;
    private String time;
    private String date;
    private String price;

    public Trip(String tripid, String from, String to, String time, String date, String price) {
        this.tripid = tripid;
        this.from = from;
        this.to = to;
        this.time = time;
        this.date = date;
        this.price = price;
    }
    public Trip(){

    }

    @NonNull
    @Override
    public String toString() {
        return "Trip{" +
                "tripid='" + tripid + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getTripid() {

        return tripid;
    }

    public void setTripid(String tripid) {

        this.tripid = tripid;
    }

    public String getFrom() {

        return from;
    }

    public void setFrom(String from) {

        this.from = from;
    }

    public String getTo() {

        return to;
    }

    public void setTo(String to) {

        this.to = to;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
