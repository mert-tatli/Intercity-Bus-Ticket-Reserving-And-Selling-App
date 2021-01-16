package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;


public class PaymentActivity extends AppCompatActivity {

    private TextView price;
    private EditText holderName, cardNumber, month, year, cvv;
    private ListView seatInfo;
    private int tripGaping = 10;
    private int count = 0;
    boolean isReturn2;
    private  String tripId, returntripId;
    private ArrayList<Integer> selectedSeatsReturn = new ArrayList<>();
    private ArrayList<Integer> selectedSeats = new ArrayList<>();
    int total=0;
    private String selectSeatOne;
    private String selectSeatOne1;
    private String selectSeatTwo;
    private DatabaseReference mTrips = FirebaseDatabase.getInstance().getReference("Trips");
    private DatabaseReference mTicket =  FirebaseDatabase.getInstance().getReference("Ticket");
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        seatInfo = findViewById(R.id.seatInfo);
        holderName = findViewById(R.id.holderName);
        cardNumber = findViewById(R.id.cardNumber);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        cvv = findViewById(R.id.cvv);
        price = findViewById(R.id.textPrice);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        isReturn2 = intent.getBooleanExtra("isReturn", false);
        if (isReturn2) {
            selectedSeatsReturn = intent.getIntegerArrayListExtra("selectedSeatsReturn");
            returntripId = intent.getStringExtra("ReturnTripId");
            selectSeatTwo = intent.getStringExtra("selectSeatTwo");
            selectSeatOne1 = intent.getStringExtra("selectSeatOne");
            selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
            tripId = intent.getStringExtra("TripId");
        }else {
            selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
            tripId = intent.getStringExtra("TripId");
            selectSeatOne = intent.getStringExtra("oneWaySeats");
        }
        mTrips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if(!isReturn2) {
                        total += selectedSeats.size() * Integer.parseInt(snapshot.child(tripId).child("price").getValue().toString());
                    }else{
                        total += selectedSeats.size() * Integer.parseInt(snapshot.child(tripId).child("price").getValue().toString());
                        total += selectedSeatsReturn.size() * Integer.parseInt(snapshot.child(returntripId).child("price").getValue().toString());
                    }
                    selectedSeats.addAll(selectedSeatsReturn);
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(PaymentActivity.this, android.R.layout.simple_list_item_1, selectedSeats);
                    seatInfo.setAdapter(adapter);
                    price.setText("Price: "+ total + "₺");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }

    public void onBuyClick(View view) {
        String holderName1 = holderName.getText().toString();
        String cardNumber1 = cardNumber.getText().toString();
        String month1 = month.getText().toString();
        String year1 = year.getText().toString();
        String cvv1 = cvv.getText().toString();

        if (TextUtils.isEmpty(holderName1) || TextUtils.isEmpty(cardNumber1) || TextUtils.isEmpty(month1)||TextUtils.isEmpty(year1) || TextUtils.isEmpty(cvv1)){
                Toast.makeText(PaymentActivity.this, "All the Informations Are Required", Toast.LENGTH_SHORT).show();
        }
        else{
            if(isReturn2) {
                mTrips.child(tripId).child("TripSeats").child("Seat").setValue(selectSeatOne1);
                mTrips.child(returntripId).child("TripSeats").child("Seat").setValue(selectSeatTwo);
            }
            else{
                mTrips.child(tripId).child("TripSeats").child("Seat").setValue(selectSeatOne);
                mTrips.child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            String userID = "unRegisteredUser";
                            if(mAuth.getCurrentUser()!=null){
                                userID = mAuth.getCurrentUser().getEmail();
                            }
                            String TicketId="Tickett1";
                            String from = snapshot.child("from").getValue().toString();
                            String to = snapshot.child("to").getValue().toString();
                            String departureTime = snapshot.child("departuretime").getValue().toString();
                            String arrivalTime = snapshot.child("arrivaltime").getValue().toString();
                            String date = snapshot.child("date").getValue().toString();
                            String price = snapshot.child("price").getValue().toString();

                            Ticket a = new Ticket(tripId,"TicketID",userID,from,to,departureTime,arrivalTime,date,price);
                            mTicket.child(TicketId).child("tripId").setValue(tripId);
                            mTicket.child(TicketId).child("ticketId").setValue(TicketId);
                            mTicket.child(TicketId).child("from").setValue(from);
                            mTicket.child(TicketId).child("to").setValue(to);
                            mTicket.child(TicketId).child("deptime").setValue(departureTime);
                            mTicket.child(TicketId).child("arrivetime").setValue(arrivalTime);
                            mTicket.child(TicketId).child("date").setValue(date);
                            mTicket.child(TicketId).child("price").setValue(price);
                            mTicket.child(TicketId).child("userID").setValue(userID);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
            Toast.makeText(PaymentActivity.this, "The ticket(s) is paid. Have a Nice Trip", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

}
