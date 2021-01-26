package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectSeatActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup layout;
    private int autoTicketID;
    private String seats;
    private boolean reserved;
    private ArrayList<Integer> seatOriantation = new ArrayList<>();
    private ArrayList<String> seatLocation = new ArrayList<>();
    private List<TextView> seatViewList = new ArrayList<>();
    private ArrayList<Integer> selectedSeats = new ArrayList<>();
    private int seatSize = 120;
    private int seatGaping = 10;
    private int STATUS_AVAILABLE = 1;
    private int STATUS_BOOKED = 2;
    private int STATUS_RESERVED = 3;
    private boolean isReturn2;
    private String tripId;
    private DatabaseReference mSeats;
    private String returnTripId;
    private FirebaseAuth mAuth;
    private DatabaseReference mTrips = FirebaseDatabase.getInstance().getReference("Trips");
    private DatabaseReference mTicket = FirebaseDatabase.getInstance().getReference("Ticket");
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectseat);

        Intent intent = getIntent();

        boolean reserved1 = intent.getBooleanExtra("reserve", false);
        reserved = reserved1;

        returnTripId = intent.getStringExtra("ReturnTripID");
        tripId = intent.getStringExtra("TripID");
        System.out.println(tripId);
        isReturn2 = intent.getBooleanExtra("isReturn", false);
        mSeats = FirebaseDatabase.getInstance().getReference("Trips");

        mAuth = FirebaseAuth.getInstance();

        layout = findViewById(R.id.layoutSeat);
        LinearLayout layoutSeat = new LinearLayout(SelectSeatActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        mSeats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    seats = snapshot.child(tripId).child("TripSeats").child("Seat").getValue().toString();
                    LinearLayout layout = null;
                    int count = 0;
                    for (int index = 0; index < seats.length(); index++) {
                        if (seats.charAt(index) == '/') {
                            layout = new LinearLayout(SelectSeatActivity.this);
                            layout.setOrientation(LinearLayout.HORIZONTAL);
                            layoutSeat.addView(layout);
                        } else if (seats.charAt(index) == 'U') {
                            count++;
                            seatOriantation.add(index);
                            seatLocation.add("U");
                            TextView view = new TextView(SelectSeatActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                            layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                            view.setLayoutParams(layoutParams);
                            view.setPadding(0, 0, 0, 2 * seatGaping);
                            view.setId(count);
                            view.setGravity(Gravity.CENTER);
                            view.setBackgroundResource(R.drawable.ic_seats_booked);
                            view.setTextColor(Color.WHITE);
                            view.setTag(STATUS_BOOKED);
                            view.setText(count + "");
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                            layout.addView(view);
                            seatViewList.add(view);
                            view.setOnClickListener(SelectSeatActivity.this);
                        } else if (seats.charAt(index) == 'A') {
                            count++;
                            seatOriantation.add(index);
                            seatLocation.add("A");
                            TextView view = new TextView(SelectSeatActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                            layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                            view.setLayoutParams(layoutParams);
                            view.setPadding(0, 0, 0, 2 * seatGaping);
                            view.setId(count);
                            view.setGravity(Gravity.CENTER);
                            view.setBackgroundResource(R.drawable.ic_seats_book);
                            view.setText(count + "");
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                            view.setTextColor(Color.BLACK);
                            view.setTag(STATUS_AVAILABLE);
                            layout.addView(view);
                            seatViewList.add(view);
                            view.setOnClickListener(SelectSeatActivity.this);
                        } else if (seats.charAt(index) == '_') {
                            TextView view = new TextView(SelectSeatActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                            layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                            view.setLayoutParams(layoutParams);
                            view.setBackgroundColor(Color.TRANSPARENT);
                            view.setText("");
                            layout.addView(view);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SelectSeatActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                Toast.makeText(SelectSeatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            view.setBackgroundResource(R.drawable.ic_seats_b);
            int in = view.getId();
            char[] myNameChars = seats.toCharArray();
            myNameChars[seatOriantation.get(in - 1)] = 'U';
            seats = String.valueOf(myNameChars);
            if (!selectedSeats.contains(view.getId()))
                selectedSeats.add(view.getId());
            view.setTag(STATUS_RESERVED);

        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            view.setBackgroundResource(R.drawable.ic_seats_book);
            int in = view.getId();
            char[] myNameChars = seats.toCharArray();
            myNameChars[seatOriantation.get(in - 1)] = 'A';
            seats = String.valueOf(myNameChars);
            for (int i = 0; i < selectedSeats.size(); i++) {
                if (selectedSeats.get(i).equals(view.getId())) {
                    selectedSeats.remove(i);
                }
            }

            view.setTag(STATUS_AVAILABLE);
        }
    }

    public void selectSeat1(View v) {
        if (selectedSeats.size() > 0) {

            if (isReturn2) {
                Intent intent = new Intent(this, Selectseat2Activity.class);
                intent.putExtra("ReturnTripId", returnTripId);
                intent.putExtra("TripId", tripId);
                intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);
                intent.putExtra("isReturn", isReturn2);
                intent.putExtra("roundTrip", seats);
                intent.putExtra("reserve", reserved);
                startActivity(intent);

            } else if (reserved) {

                mDatabase.child("autoTicketID").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot autoTicket) {
                        autoTicketID = Integer.parseInt(autoTicket.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SelectSeatActivity.this, "Something went wrong! DATABASE CONNECTİON FAİLED.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SelectSeatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SelectSeatActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                mTrips.child(tripId).child("TripSeats").child("Seat").setValue(seats);
                mTrips.child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String userID = mAuth.getCurrentUser().getEmail();
                            String TicketId = "Ticket" + autoTicketID;
                            String from = snapshot.child("from").getValue().toString();
                            String to = snapshot.child("to").getValue().toString();
                            String departureTime = snapshot.child("departuretime").getValue().toString();
                            String arrivalTime = snapshot.child("arrivaltime").getValue().toString();
                            String date = snapshot.child("date").getValue().toString();
                            String price = snapshot.child("price").getValue().toString();
                            Ticket a = new Ticket(tripId, "TicketID", userID, from, to, departureTime, arrivalTime, date, price, selectedSeats);
                            mTicket.child(TicketId).child("tripId").setValue(tripId);
                            mTicket.child(TicketId).child("ticketId").setValue(TicketId);
                            mTicket.child(TicketId).child("from").setValue(from);
                            mTicket.child(TicketId).child("to").setValue(to);
                            mTicket.child(TicketId).child("deptime").setValue(departureTime);
                            mTicket.child(TicketId).child("arrivetime").setValue(arrivalTime);
                            mTicket.child(TicketId).child("date").setValue(date);
                            mTicket.child(TicketId).child("price").setValue(price);
                            mTicket.child(TicketId).child("userID").setValue(userID);
                            //
                            mTicket.child(TicketId).child("isReserved").setValue(reserved);

                            autoTicketID++;
                            String SeatsDeparture = "";
                            for (int i = 0; i < selectedSeats.size(); i++) {
                                if (SeatsDeparture.equals("")) {
                                    SeatsDeparture = selectedSeats.get(i).toString();
                                } else
                                    SeatsDeparture = SeatsDeparture + " --> " + selectedSeats.get(i).toString();
                            }
                            mTicket.child(TicketId).child("seats").setValue(SeatsDeparture);
                            mDatabase.child("autoTicketID").setValue(autoTicketID);
                            //


                            Toast.makeText(SelectSeatActivity.this, "Your seat(s) has ben reserved! Have a nice trip.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SelectSeatActivity.this, UserAccountActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SelectSeatActivity.this, "Something went wrong! DATABASE CONNECTİON FAİLED.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SelectSeatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SelectSeatActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(this, UnregisteredUserInfo.class);
                    intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);
                    intent.putExtra("TripId", tripId);
                    intent.putExtra("isReturn", isReturn2);
                    intent.putExtra("oneWaySeats", seats);
                    intent.putExtra("reserve", reserved);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, PaymentActivity.class);
                    intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);
                    intent.putExtra("TripId", tripId);
                    intent.putExtra("isReturn", isReturn2);
                    intent.putExtra("oneWaySeats", seats);
                    intent.putExtra("reserve", reserved);
                    startActivity(intent);
                    finish();
                }
            }
        } else {
            Toast.makeText(this, "Choose at least one seat.", Toast.LENGTH_SHORT).show();
        }


    }


}

