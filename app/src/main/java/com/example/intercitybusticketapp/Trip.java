package com.example.intercitybusticketapp;

import android.widget.EditText;

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


    public String getFrom() {
        return from;
    }


    public String getTo() {
        return to;
    }



    public String getTime() {
        return time;
    }


    public String getDate() {
        return date;
    }


    public String getPrice() {
        return price;
    }


}
