package com.example.intercitybusticketapp;


import androidx.annotation.NonNull;

public class Bus {

    private String plateNo;
    private String capacity;

    public Bus(String plateNo, String capacity) {
        this.plateNo = plateNo;
        this.capacity = capacity;

    }

    @NonNull
    @Override
    public String toString() {
        return "Bus{" +
                "plateNo='" + plateNo + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }
}
