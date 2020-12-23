package com.example.intercitybusticketapp;

import java.util.ArrayList;

public class TripModel {

    private ArrayList<Trip> trips;

    public TripModel() {
        trips = new ArrayList<>();
    }

    public void setTrip(Trip trip) {
        trips.add(trip);
    }
}
