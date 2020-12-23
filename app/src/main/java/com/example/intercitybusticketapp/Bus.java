package com.example.intercitybusticketapp;

import android.view.View;

import java.util.ArrayList;

public class Bus {

    private String plateNo;
    private String capacity;

    public Bus(String plateNo, String capacity) {
        this.plateNo = plateNo;
        this.capacity = capacity;

    }

    @Override
    public String toString() {
        return "Bus{" +
                "plateNo='" + plateNo + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }
}
