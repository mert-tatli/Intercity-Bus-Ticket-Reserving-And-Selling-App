package com.example.intercitybusticketapp;

import java.io.Serializable;
import java.util.ArrayList;

public class TripModel implements Serializable {

    private ArrayList<Trip> trips;

    public TripModel() {

        trips = new ArrayList<>();
    }

    public void setTrip(Trip trip) {

        trips.add(trip);
    }
}
