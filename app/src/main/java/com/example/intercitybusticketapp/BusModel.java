package com.example.intercitybusticketapp;

import java.util.ArrayList;

public class BusModel {


    private ArrayList<Bus> buses;

    public BusModel() {

        buses = new ArrayList<>();
    }

    public void setBus(Bus bus) {

        buses.add(bus);
    }

}
