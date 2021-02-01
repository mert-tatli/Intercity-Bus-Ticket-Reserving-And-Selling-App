package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyTicketsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textview;
    private ImageView imageView;
    private ViewGroup layout;
    private int tripGaping = 10;
    private int count = 0;
    private List<Ticket> Tickets;
    private ArrayList<Integer> arr = new ArrayList<>();
    private String ticketCondition = "";
    private boolean isReserved = false;
    private DatabaseReference mTickets;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);


        // Tickets = MainActivity.getTripList();
        mAuth = FirebaseAuth.getInstance();
        Tickets = new ArrayList<>();
        layout = findViewById(R.id.linearLayoutTickets);
        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(4 * tripGaping, 4 * tripGaping, 4 * tripGaping, 4 * tripGaping);
        layout.addView(layoutSeat);
        mTickets = FirebaseDatabase.getInstance().getReference("Ticket");
        if (isReserved) {
            ticketCondition = "RESERVED";
        } else {
            ticketCondition = "TICKET";
        }
        mTickets.orderByChild("userID").equalTo(mAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        Ticket ticket1 = new Ticket();
                        ticket1.setArrivalTime(snapshot.child("arrivetime").getValue().toString());
                        ticket1.setDate(snapshot.child("date").getValue().toString());
                        ticket1.setFrom(snapshot.child("from").getValue().toString());
                        ticket1.setTo(snapshot.child("to").getValue().toString());
                        ticket1.setPrice(snapshot.child("price").getValue().toString());
                        ticket1.setTicketId(snapshot.child("ticketId").getValue().toString());
                        ticket1.setUserId(snapshot.child("userID").getValue().toString());
                        ticket1.setDepartureTime(snapshot.child("deptime").getValue().toString());
                        ticket1.setTripId(snapshot.child("tripId").getValue().toString());
                        ticket1.setPlateNumber(snapshot.child("busPlate").getValue().toString());
                        ticket1.setReserved((Boolean) snapshot.child("isReserved").getValue());

//busplate yazdırılacak
// seatler yazdırılacak
                        ArrayList<Integer> a = new ArrayList();
                        String emptiedSeats = snapshot.child("seats").getValue().toString();
                        String[] freeSeatNumbers = emptiedSeats.split(" --> ");
                        for (int i = 0; i < freeSeatNumbers.length; i++) {
                            a.add(Integer.parseInt(freeSeatNumbers[i]));
                        }
                        ticket1.setSeats(a);
                        System.out.println(ticket1.toString());
                        Tickets.add(ticket1);

                    }
                    LinearLayout layout = null;
                    for (int index = 0; index < Tickets.size(); index++) {
                        count++;
                        layout = new LinearLayout(MyTicketsActivity.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layoutSeat.addView(layout);
                        CardView view = new CardView(MyTicketsActivity.this);
                        Drawable myDrawable = getResources().getDrawable(R.drawable.home_gradient_maths);
                        view.setBackground(myDrawable);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(5, 30, 5, 30);
                        view.setLayoutParams(layoutParams);
                        view.setPadding(0, 20, 0, 20);
                        view.setId(count);
                        view.setCardElevation(10);
                        view.setRadius(15);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams0 = new LinearLayout.LayoutParams(300, 100);
                        textview.setLayoutParams(textParams0);
                        textview.setText("Ticket PNR: " + Tickets.get(index).getTicketId());
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams1 = new LinearLayout.LayoutParams(300, 300);
                        textview.setLayoutParams(textParams1);
                        textview.setText("Departure Time: " + Tickets.get(index).getDepartureTime());
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams2 = new LinearLayout.LayoutParams(300, 550);
                        textview.setLayoutParams(textParams2);
                        textview.setText("Arrival Time: " + Tickets.get(index).getArrivalTime());
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams3 = new LinearLayout.LayoutParams(1100, 300);
                        textview.setLayoutParams(textParams3);
                        textview.setText("From: " + Tickets.get(index).getFrom());
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams4 = new LinearLayout.LayoutParams(1100, 500);
                        textview.setLayoutParams(textParams4);
                        textview.setText("To:   " + Tickets.get(index).getTo());
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);


                        imageView = new ImageView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams imgParams1 = new LinearLayout.LayoutParams(550, 220);
                        imageView.setLayoutParams(imgParams1);
                        Drawable myDrawable2 = getResources().getDrawable(R.drawable.ic_three_dots);
                        imgParams1.setMargins(120, 90, 120, 90);
                        imageView.setPadding(0, 50, 0, 50);
                        imageView.setImageDrawable(myDrawable2);
                        view.addView(imageView);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams7 = new LinearLayout.LayoutParams(300, 750);
                        textview.setLayoutParams(textParams7);
                        textview.setText("Date:   " + Tickets.get(index).getDate());
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);

                        textview = new TextView(MyTicketsActivity.this);
                        LinearLayout.LayoutParams textParams6 = new LinearLayout.LayoutParams(500, 200);
                        textview.setLayoutParams(textParams6);
                        textview.setText(ticketCondition);
                        Drawable myDrawable4 = getResources().getDrawable(R.drawable.ic_button_round);
                        textview.setBackground(myDrawable4);
                        textParams6.setMargins(600, 400, 600, 0);
                        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        textview.setTextColor(Color.WHITE);
                        textview.setPadding(5, 5, 5, 5);
                        textview.setGravity(Gravity.CENTER);
                        view.addView(textview);
                        layout.addView(view);
                        view.setOnClickListener(MyTicketsActivity.this);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}