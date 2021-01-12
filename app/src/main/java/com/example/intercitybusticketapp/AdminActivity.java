package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }


    public void addbus(View view){

        Intent intent = new Intent(AdminActivity.this,AddBusActivity.class);
        startActivity(intent);

    }
    public void addtrip(View view){

        Intent intent = new Intent(AdminActivity.this,AddTripActivity.class);
        startActivity(intent);

    }
    public void deletebus(View view){

        Intent intent = new Intent(AdminActivity.this,DeleteBusActivity.class);
        startActivity(intent);

    }
    public void deletetrip(View view){

        Intent intent = new Intent(AdminActivity.this,DeleteTripActivity.class);
        startActivity(intent);

    }

}