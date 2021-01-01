package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBusActivity extends AppCompatActivity {
    EditText plateNo;
    EditText capacity;
    BusModel busmodel;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        plateNo=findViewById(R.id.inputBusPlate);
        capacity=findViewById(R.id.inputBusCapacity);
        busmodel = new BusModel();
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }




    public void addbus(View view){
        String plate = plateNo.getText().toString();
        String capacityy=capacity.getText().toString();

        if (TextUtils.isEmpty(plate) || TextUtils.isEmpty(capacityy)){
            Toast.makeText(AddBusActivity.this,"All information are required please check",Toast.LENGTH_LONG).show();
        }
        else{ // BURDAN SONRA BUS OLUŞUYOR , DATABASE EKLENECEK
            Bus b = new Bus(plate,capacityy);
            busmodel.setBus(b);
            Toast.makeText(this, "BUS WAS CREATED SUCCESFULLY", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, b.toString(), Toast.LENGTH_SHORT).show();
            mDatabase.child("Buses").child(plate).child("Capacity").setValue(capacityy);
        }

    }
}