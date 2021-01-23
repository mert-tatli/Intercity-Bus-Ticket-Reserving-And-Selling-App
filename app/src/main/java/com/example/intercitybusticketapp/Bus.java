package com.example.intercitybusticketapp;


import androidx.annotation.NonNull;

public class Bus {

    private String plateNo;

    public Bus(String plateNo) {
        this.plateNo = plateNo;

    }

    @NonNull
    @Override
    public String toString() {
        return "Bus{" +
                "plateNo='" + plateNo+"'}";
    }
}
