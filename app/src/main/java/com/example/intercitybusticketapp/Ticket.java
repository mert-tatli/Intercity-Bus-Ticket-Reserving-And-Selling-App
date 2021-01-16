package com.example.intercitybusticketapp;

public class Ticket {

    private String tripId;
    private String TicketId;
    private String UserId;
    private  String from;
    private String to;
    private String departureTime;
    private String arrivalTime;
    private String date;
    private String price;


    public Ticket(String tripId, String ticketId, String userId, String from, String to, String departureTime, String arrivalTime, String date, String price) {
        this.tripId = tripId;
        this.TicketId = ticketId;
        this.UserId = userId;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.date = date;
        this.price = price;
    }





    @Override
    public String toString() {
        return "Ticket{" +
                "tripId='" + tripId + '\'' +
                ", TicketId='" + TicketId + '\'' +
                ", UserId='" + UserId + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", date='" + date + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public String getTicketId() {
        return TicketId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getTo() {
        return to;
    }

    public String getTripId() {
        return tripId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setTicketId(String ticketId) {
        TicketId = ticketId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
