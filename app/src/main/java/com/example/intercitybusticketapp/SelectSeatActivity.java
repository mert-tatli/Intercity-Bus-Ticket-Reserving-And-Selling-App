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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectSeatActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup layout;

        String seats;

        ArrayList<Integer> seatOriantation = new ArrayList<>();
        ArrayList<String> seatLocation = new ArrayList<>();
        List<TextView> seatViewList = new ArrayList<>();
        ArrayList<Integer> selectedSeats = new ArrayList<>();
        int seatSize = 120;
        int seatGaping = 10;

        int STATUS_AVAILABLE = 1;
        int STATUS_BOOKED = 2;
        int STATUS_RESERVED = 3;
        String selectedIds = "";
        boolean isReturn2;
        String tripId;
        DatabaseReference mSeats;
        String returnTripId;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_selectseat);

            Intent intent = getIntent();
            returnTripId = intent.getStringExtra("ReturnTripID");
            tripId = intent.getStringExtra("TripID");
            isReturn2 = intent.getBooleanExtra("isReturn",false);
            mSeats = FirebaseDatabase.getInstance().getReference("Trips");

            mSeats.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        seats = snapshot.child(tripId).child("TripSeats").child("Seat").getValue().toString();
                        layout = findViewById(R.id.layoutSeat);
                        seats = "/" + seats;
                        LinearLayout layoutSeat = new LinearLayout(SelectSeatActivity.this
                        );
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutSeat.setOrientation(LinearLayout.VERTICAL);
                        layoutSeat.setLayoutParams(params);
                        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
                        layout.addView(layoutSeat);
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
                    Toast.makeText(SelectSeatActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);

            } else {
                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_b);
                int in = view.getId();
                char[] myNameChars = seats.toCharArray();
                myNameChars[seatOriantation.get(in - 1)] = 'U';
                seats = String.valueOf(myNameChars);
                Toast.makeText(this, "Seat " +  view.getId() + " is Selected", Toast.LENGTH_SHORT).show();
                //if(view.getId()==selectedSeats.get())
                selectedSeats.add(view.getId());

            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectSeat1(View v) {
        if (isReturn2) {
            Intent intent = new Intent(this, Selectseat2Activity.class);
            intent.putExtra("ReturnTripId", returnTripId);
            intent.putExtra("TripId", tripId);
            intent.putIntegerArrayListExtra("selectedSeats",selectedSeats);
            intent.putExtra("isReturn",isReturn2);
            startActivity(intent);
            finish();


        } else {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putIntegerArrayListExtra("selectedSeats",selectedSeats);
            intent.putExtra("TripId", tripId);
            intent.putExtra("isReturn",isReturn2);


            startActivity(intent);
            finish();


        }

    }



}

