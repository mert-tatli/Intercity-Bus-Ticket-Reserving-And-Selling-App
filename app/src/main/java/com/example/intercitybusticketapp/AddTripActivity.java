package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTripActivity extends AppCompatActivity {

    EditText tripid;
    EditText from;
    EditText to;
    EditText time;
    EditText date;
    EditText price;
    TripModel tripmodel;
    //ArrayList<Trip> tripList;
    private DatabaseReference mDatabase;
    private DatabaseReference mTrips;
    private List<Trip> tripList;


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
        tripList = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTrips = FirebaseDatabase.getInstance().getReference("Trips");
        Query query1 = mTrips.orderByChild("from").equalTo("kutahya");
        query1.orderByChild("to").equalTo("erzurum");
        Query query2 = mTrips.orderByChild("to").equalTo("erzurum");
        query1.addListenerForSingleValueEvent(valueEventListener);






    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            tripList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot :datasnapshot.getChildren()){
                    Trip trip = snapshot.getValue(Trip.class);
                    tripList.add(trip);
                }

            }
            for(int i =0;i<tripList.size();i++){
                if(tripList.get(i).getTo().equals("erzurum"))
                System.out.println(tripList.get(i).toString());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            System.out.println(error.getMessage());
        }
    };

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
            Toast.makeText(AddTripActivity.this,t.toString(),Toast.LENGTH_LONG).show();  // test amaçlı doğru çalışıyor
            mDatabase.child("Trips").child(tripid1).child("tripid").setValue(tripid1);
            mDatabase.child("Trips").child(tripid1).child("from").setValue(from1);
            mDatabase.child("Trips").child(tripid1).child("to").setValue(to1);
            mDatabase.child("Trips").child(tripid1).child("time").setValue(time1);
            mDatabase.child("Trips").child(tripid1).child("date").setValue(date1);
            mDatabase.child("Trips").child(tripid1).child("price").setValue(price1);


        }
    }
}