package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class TripActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean reserved;
    private boolean isReturn2;
    private TextView textview;
    private ImageView imageView;
    private ViewGroup layout;
    private int tripGaping = 10;
    private int count = 0;
    private DatabaseReference mTrips;
    private List<Trip> Trips;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Intent intent = getIntent();

        boolean isReturn = intent.getBooleanExtra("isReturn",false);
        isReturn2 = isReturn;
        boolean reserved1 = intent.getBooleanExtra("reserve",false);
        reserved = reserved1;

        Trips = MainActivity.getTripList();
        mAuth=FirebaseAuth.getInstance();

        layout = findViewById(R.id.layoutTrip);
        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(4 * tripGaping, 8* tripGaping, 4 * tripGaping, 8 * tripGaping);
        layout.addView(layoutSeat);
        mTrips = FirebaseDatabase.getInstance().getReference("Trips");

        LinearLayout layout = null;
        for (int index = 0; index < Trips.size(); index++) {

            count++;
            layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layoutSeat.addView(layout);
            CardView view = new CardView(this);
            Drawable myDrawable = getResources().getDrawable(R.drawable.home_gradient_maths);
            view.setBackground(myDrawable);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 30, 10, 30);
            view.setLayoutParams(layoutParams);
            view.setPadding(0, 20, 0, 20);
            view.setId(count);
            view.setCardElevation(10);
            view.setRadius(15);
            view.setOnClickListener(this);

            textview = new TextView(TripActivity.this);
            LinearLayout.LayoutParams textParams1 = new LinearLayout.LayoutParams(450, 200);
            textview.setLayoutParams(textParams1);
            textview.setText("Departure Time: " + Trips.get(index).getDepartTime());
            textParams1.setMargins(20, 50, 20, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setPadding(0, 0, 0, 0);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(TripActivity.this);
            LinearLayout.LayoutParams textParams2 = new LinearLayout.LayoutParams(400, 200);
            textview.setLayoutParams(textParams2);
            textview.setText("Arrival Time: " + Trips.get(index).getArrivaltime());
            textParams2.setMargins(10, 150, 10, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(TripActivity.this);
            LinearLayout.LayoutParams textParams3 = new LinearLayout.LayoutParams(450, 200);
            textview.setLayoutParams(textParams3);
            textview.setText("From: " + Trips.get(index).getFrom());
            textParams3.setMargins(440, 50, 440, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(TripActivity.this);
            LinearLayout.LayoutParams textParams4 = new LinearLayout.LayoutParams(450, 200);
            textview.setLayoutParams(textParams4);
            textview.setText("To:   " + Trips.get(index).getTo());
            textParams4.setMargins(450, 150, 450, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setPadding(5, 5, 5, 5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            imageView = new ImageView(TripActivity.this);
            LinearLayout.LayoutParams imgParams1 = new LinearLayout.LayoutParams(150, 200);
            imageView.setLayoutParams(imgParams1);
            Drawable myDrawable2 = getResources().getDrawable(R.drawable.ic_three_dots);
            imgParams1.setMargins(415, 90, 415, 90);
            imageView.setImageDrawable(myDrawable2);
            view.addView(imageView);

            textview = new TextView(TripActivity.this);
            LinearLayout.LayoutParams textParams7 = new LinearLayout.LayoutParams(400, 200);
            textview.setLayoutParams(textParams7);
            textview.setText("Date: " + Trips.get(index).getDate());
            textParams7.setMargins(5, 250, 5, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);



            imageView = new ImageView(this);
            LinearLayout.LayoutParams imgParams2 = new LinearLayout.LayoutParams(1800, 250);
            imageView.setLayoutParams(imgParams2);
            Drawable myDrawable3 = getResources().getDrawable(R.drawable.ic_bus_256);
            imageView.setImageDrawable(myDrawable3);
            view.addView(imageView);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams6 = new LinearLayout.LayoutParams(500, 200);
            textview.setLayoutParams(textParams6);
            textview.setText("Select");
            Drawable myDrawable4 = getResources().getDrawable(R.drawable.ic_button_round);
            textview.setBackground(myDrawable4);
            textParams6.setMargins(600, 400, 600, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setPadding(5, 5, 5, 5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);
            layout.addView(view);
            view.setOnClickListener(this);
        }
    }



    @Override
    public void onClick(View view) {
        int in = view.getId();
        if (isReturn2) {
            Intent intent = new Intent(this, ReturnTripActivity.class);
            intent.putExtra("isReturn", isReturn2);
            intent.putExtra("TripID" , Trips.get(in-1).getTripid());
            intent.putExtra("reserve" , reserved);
            startActivity(intent);
         //   finish();
        } else {
            Intent intent = new Intent(this, SelectSeatActivity.class);
            intent.putExtra("TripID" , Trips.get(in-1).getTripid());
            intent.putExtra("isReturn", isReturn2);
            intent.putExtra("reserve" , reserved);
            startActivity(intent);
           // finish();
        }


    }


}