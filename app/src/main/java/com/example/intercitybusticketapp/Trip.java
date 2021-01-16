package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;

public class Trip {

    private  String tripid;
    private  String from;
    private String to;
    private String departuretime;
    private String arrivaltime;
    private String date;
    private String price;

    public Trip(String tripid, String from, String to, String departuretime, String arrivaltime, String date, String price) {
        this.tripid = tripid;
        this.from = from;
        this.to = to;
        this.departuretime = departuretime;
        this.arrivaltime = arrivaltime;
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
                ", departureTime='" + departuretime + '\'' +
                ", arrivalTime='" + arrivaltime + '\'' +
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

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getDepartTime() {
        return departuretime;
    }

    public void setDepartTime(String departTime) {
        this.departuretime = departTime;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
