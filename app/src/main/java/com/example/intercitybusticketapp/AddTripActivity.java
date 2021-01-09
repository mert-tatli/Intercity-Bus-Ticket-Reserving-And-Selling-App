package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTripActivity extends AppCompatActivity {

    EditText tripid;
    EditText from;
    EditText to;
    EditText time;
    EditText date;
    EditText price;
    TripModel tripmodel;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        tripid=findViewById(R.id.inputTripId);
        from = findViewById(R.id.inputTripFrom);
        to=findViewById(R.id.inputTripTo);
        time=findViewById(R.id.inputTripTime);
        date=findViewById(R.id.inputTripDepartureDate);
        price=findViewById(R.id.inputTripPrice);
        tripmodel=new TripModel();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void addtrip(View view){
        String tripid1=tripid.getText().toString();
        String from1=from.getText().toString();
        String to1=to.getText().toString();
        String time1=time.getText().toString();
        String date1=date.getText().toString();
        String price1=price.getText().toString();

        if (TextUtils.isEmpty(tripid1) || TextUtils.isEmpty(from1) || TextUtils.isEmpty(to1)|| TextUtils.isEmpty(time1)|| TextUtils.isEmpty(date1)|| TextUtils.isEmpty(price1))
        {
            Toast.makeText(AddTripActivity.this,"All the Information Are Required,PLEASE CHECK",Toast.LENGTH_LONG).show();
        }
        else{   // BU KISIMDA TRIP OLUŞUYOR BURDAN SONRA DATABASE EKLENECEK
            Trip t = new Trip(tripid1,from1,to1,time1,date1,price1);
            tripmodel.setTrip(t);
            Toast.makeText(AddTripActivity.this,"TRIP WAS CREATED SUCCESFULLY",Toast.LENGTH_LONG).show();
            // Toast.makeText(AddTripActivity.this,t.toString(),Toast.LENGTH_LONG).show();  // test amaçlı doğru çalışıyor
            mDatabase.child("Trips").child(tripid1).child("tripid").setValue(tripid1);
            mDatabase.child("Trips").child(tripid1).child("from").setValue(from1);
            mDatabase.child("Trips").child(tripid1).child("to").setValue(to1);
            mDatabase.child("Trips").child(tripid1).child("time").setValue(time1);
            mDatabase.child("Trips").child(tripid1).child("date").setValue(date1);
            mDatabase.child("Trips").child(tripid1).child("price").setValue(price1);


        }
    }
}