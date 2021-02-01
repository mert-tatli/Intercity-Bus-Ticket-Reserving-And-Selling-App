package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PaymentActivity extends AppCompatActivity {

    private TextView price;
    private EditText holderName, cardNumber, month, year, cvv;
    private ListView seatInfo,seat2Info;
    private int tripGaping = 10;
    private int count = 0;
    private int autoTicketID;
    boolean isReturn2;
    private  String tripId, returntripId;
    private ArrayList<Integer> selectedSeatsReturn = new ArrayList<>();
    private ArrayList<Integer> selectedSeats = new ArrayList<>();
    int total=0;
    private String selectSeatOne,unRegisteredUserMail;
    private String selectSeatOne1;
    private String selectSeatTwo;
    private DatabaseReference mTrips = FirebaseDatabase.getInstance().getReference("Trips");
    private DatabaseReference mTicket =  FirebaseDatabase.getInstance().getReference("Ticket");
    private DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        seatInfo = findViewById(R.id.seatInfo1);
        seat2Info = findViewById(R.id.seat2Info);
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
            if(mAuth.getCurrentUser()==null){
                selectedSeatsReturn = intent.getIntegerArrayListExtra("selectedSeatsReturn");
                returntripId = intent.getStringExtra("ReturnTripId");
                selectSeatTwo = intent.getStringExtra("selectSeatTwo");
                selectSeatOne1 = intent.getStringExtra("selectSeatOne");
                selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
                tripId = intent.getStringExtra("TripId");

            }else{
                selectedSeatsReturn = intent.getIntegerArrayListExtra("selectedSeatsReturn");
                returntripId = intent.getStringExtra("ReturnTripId");
                selectSeatTwo = intent.getStringExtra("selectSeatTwo");
                selectSeatOne1 = intent.getStringExtra("selectSeatOne");
                selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
                tripId = intent.getStringExtra("TripId");
            }

        }else {
            if(mAuth.getCurrentUser()==null){
                selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
                tripId = intent.getStringExtra("TripId");
                selectSeatOne = intent.getStringExtra("selectSeatOne");
            }else{
                selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
                tripId = intent.getStringExtra("TripId");
                selectSeatOne = intent.getStringExtra("oneWaySeats");
            }

        }

        unRegisteredUserMail = intent.getStringExtra("unregisteredUser");
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

                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(PaymentActivity.this, android.R.layout.simple_list_item_1, selectedSeats);
                    ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(PaymentActivity.this, android.R.layout.simple_list_item_1, selectedSeatsReturn);
                    seat2Info.setAdapter(adapter2);
                    seatInfo.setAdapter(adapter);
                    price.setText("Price: "+ total + "₺");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentActivity.this,"Something went wrong! DATABASE CONNECTION HAS FAILED.",Toast.LENGTH_SHORT).show();
                Toast.makeText(PaymentActivity.this,"Please Try Again With Better Connection.",Toast.LENGTH_SHORT).show();
                Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBuyClick(View view) {
        String holderName1 = holderName.getText().toString();
        String cardNumber1 = cardNumber.getText().toString();
        String month1 = month.getText().toString();
        String year1 = year.getText().toString();
        String cvv1 = cvv.getText().toString();
        int y=Integer.valueOf(year1);
        int m=Integer.valueOf(month1);

        if (TextUtils.isEmpty(holderName1) || TextUtils.isEmpty(cardNumber1) || TextUtils.isEmpty(month1)||TextUtils.isEmpty(year1) || TextUtils.isEmpty(cvv1)
        || y<21  || m>12 || cvv1.length()<3 || m==0 || y==0){
                Toast.makeText(PaymentActivity.this, "All the Informations Are Required,Check your information", Toast.LENGTH_LONG).show();
        }
        else{
            if(isReturn2) {
                mTrips.child(tripId).child("TripSeats").child("Seat").setValue(selectSeatOne1);
                mTrips.child(returntripId).child("TripSeats").child("Seat").setValue(selectSeatTwo);

                mDatabase.child("autoTicketID").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot autoTicket) {
                        autoTicketID = Integer.parseInt(autoTicket.getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PaymentActivity.this,"Something went wrong! DATABASE CONNECTİON FAİLED.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                });

                mDatabase.child("Trips").child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String userID = unRegisteredUserMail;
                            if(mAuth.getCurrentUser()!=null){
                                userID = mAuth.getCurrentUser().getEmail();
                            }
                            String from = snapshot.child("from").getValue().toString();
                            String to = snapshot.child("to").getValue().toString();
                            String departureTime = snapshot.child("departuretime").getValue().toString();
                            String arrivalTime = snapshot.child("arrivaltime").getValue().toString();
                            String date = snapshot.child("date").getValue().toString();
                            String price = snapshot.child("price").getValue().toString();
                            String busPlate = snapshot.child("busPlate").getValue().toString();

                            String TicketId="PNR2021" + autoTicketID;

                            Ticket a = new Ticket(tripId,TicketId,userID,from,to,departureTime,arrivalTime,date,price,selectedSeats);
                            mTicket.child(TicketId).child("tripId").setValue(tripId);
                            mTicket.child(TicketId).child("ticketId").setValue(TicketId);
                            mTicket.child(TicketId).child("from").setValue(from);
                            mTicket.child(TicketId).child("to").setValue(to);
                            mTicket.child(TicketId).child("deptime").setValue(departureTime);
                            mTicket.child(TicketId).child("arrivetime").setValue(arrivalTime);
                            mTicket.child(TicketId).child("date").setValue(date);
                            mTicket.child(TicketId).child("price").setValue(String.valueOf(Integer.parseInt(price)* selectedSeats.size()));
                            mTicket.child(TicketId).child("userID").setValue(userID);
                            mTicket.child(TicketId).child("busPlate").setValue(busPlate);
                            mTicket.child(TicketId).child("isReserved").setValue(false);

                            autoTicketID++;
                            String Seatsa ="";
                            for(int i=0 ; i<selectedSeats.size() ; i++){
                                if(Seatsa.equals("")){
                                    Seatsa =selectedSeats.get(i).toString();
                                }else
                                    Seatsa = Seatsa + " --> " +  selectedSeats.get(i).toString();
                            }
                            mTicket.child(TicketId).child("seats").setValue(Seatsa);
                            mDatabase.child("autoTicketID").setValue(autoTicketID);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PaymentActivity.this,"Something went wrong! DATABASE CONNECTİON HAS FAİLED.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,"Please Try Again With Better Connection.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                mDatabase.child("Trips").child(returntripId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String userID = unRegisteredUserMail;
                            if(mAuth.getCurrentUser()!=null){
                                userID = mAuth.getCurrentUser().getEmail();
                            }
                            String from = snapshot.child("from").getValue().toString();
                            String to = snapshot.child("to").getValue().toString();
                            String departureTime = snapshot.child("departuretime").getValue().toString();
                            String arrivalTime = snapshot.child("arrivaltime").getValue().toString();
                            String date = snapshot.child("date").getValue().toString();
                            String price = snapshot.child("price").getValue().toString();
                            String busPlate = snapshot.child("busPlate").getValue().toString();
                            String TicketId="PNR2021" + (String)Integer.toString(autoTicketID);
                            Ticket a = new Ticket(tripId,TicketId,userID,from,to,departureTime,arrivalTime,date,price,selectedSeatsReturn);
                            mTicket.child(TicketId).child("tripId").setValue(returntripId);
                            mTicket.child(TicketId).child("ticketId").setValue(TicketId);
                            mTicket.child(TicketId).child("from").setValue(from);
                            mTicket.child(TicketId).child("to").setValue(to);
                            mTicket.child(TicketId).child("deptime").setValue(departureTime);
                            mTicket.child(TicketId).child("arrivetime").setValue(arrivalTime);
                            mTicket.child(TicketId).child("date").setValue(date);
                            mTicket.child(TicketId).child("price").setValue(String.valueOf(Integer.parseInt(price)* selectedSeatsReturn.size()));
                            mTicket.child(TicketId).child("userID").setValue(userID);
                            mTicket.child(TicketId).child("busPlate").setValue(busPlate);
                            mTicket.child(TicketId).child("isReserved").setValue(false);
                            autoTicketID++;
                            String Seats ="";
                            for(int i=0 ; i<selectedSeatsReturn.size() ; i++){
                                if(Seats.equals("")){
                                    Seats =selectedSeatsReturn.get(i).toString();
                                }else
                                    Seats = Seats + " --> " +  selectedSeatsReturn.get(i).toString();
                            }
                            mTicket.child(TicketId).child("seats").setValue(Seats);
                            mDatabase.child("autoTicketID").setValue(autoTicketID);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PaymentActivity.this,"Something went wrong! DATABASE CONNECTİON HAS FAİLED.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,"Please Try Again With Better Connection.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });


            }
            else{
                mDatabase.child("autoTicketID").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot autoTicket) {
                        autoTicketID = Integer.parseInt(autoTicket.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PaymentActivity.this,"Something went wrong! DATABASE CONNECTİON FAİLED.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                mDatabase.child("Trips").child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            String userID = unRegisteredUserMail;
                            if(mAuth.getCurrentUser()!=null){
                                userID = mAuth.getCurrentUser().getEmail();
                            }
                            String from = snapshot.child("from").getValue().toString();
                            String to = snapshot.child("to").getValue().toString();
                            String departureTime = snapshot.child("departuretime").getValue().toString();
                            String arrivalTime = snapshot.child("arrivaltime").getValue().toString();
                            String date = snapshot.child("date").getValue().toString();
                            String price = snapshot.child("price").getValue().toString();
                            String busPlate = snapshot.child("busPlate").getValue().toString();
                            String TicketId="PNR2021" + (String)Integer.toString(autoTicketID);
                            Ticket a = new Ticket(tripId,TicketId,userID,from,to,departureTime,arrivalTime,date,price,selectedSeats);
                            mTicket.child(TicketId).child("tripId").setValue(tripId);
                            mTicket.child(TicketId).child("ticketId").setValue(TicketId);
                            mTicket.child(TicketId).child("from").setValue(from);
                            mTicket.child(TicketId).child("to").setValue(to);
                            mTicket.child(TicketId).child("deptime").setValue(departureTime);
                            mTicket.child(TicketId).child("arrivetime").setValue(arrivalTime);
                            mTicket.child(TicketId).child("date").setValue(date);
                            mTicket.child(TicketId).child("price").setValue(price);
                            mTicket.child(TicketId).child("userID").setValue(userID);
                            mTicket.child(TicketId).child("isReserved").setValue(false);
                            mTicket.child(TicketId).child("busPlate").setValue(busPlate);


                            autoTicketID++;
                            String Seats ="";
                            for(int i=0 ; i<selectedSeats.size() ; i++){
                                if(Seats.equals("")){
                                    Seats =selectedSeats.get(i).toString();
                                }else
                                Seats = Seats + " --> " +  selectedSeats.get(i).toString();
                            }
                            mTicket.child(TicketId).child("seats").setValue(Seats);
                            mDatabase.child("autoTicketID").setValue(autoTicketID);

                            mTrips.child(tripId).child("TripSeats").child("Seat").setValue(selectSeatOne);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PaymentActivity.this,"Something went wrong! DATABASE CONNECTİON HAS FAİLED.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,"Please Try Again With Better Connection.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(PaymentActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this,MainActivity.class);
                        startActivity(intent);
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
