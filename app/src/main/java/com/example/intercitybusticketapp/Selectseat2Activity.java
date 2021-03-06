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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Selectseat2Activity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup layout;

    private String seats;
    private String seatsFromSeatActivityOne;

    private int autoTicketID;

    private ArrayList<Integer> seatOriantation = new ArrayList<>();
    private ArrayList<String> seatLocation = new ArrayList<>();
    private  List<TextView> seatViewList = new ArrayList<>();
    private  ArrayList<Integer> selectedSeatsReturn = new ArrayList<>();
    private  ArrayList<Integer> selectedSeats = new ArrayList<>();
    private int seatSize = 120;
    private  int seatGaping = 10;

    private  int STATUS_AVAILABLE = 1;
    private   int STATUS_BOOKED = 2;
    private  int STATUS_RESERVED = 3;
    private  String selectedIds = "";
    private  boolean isReturn2,reserved;
    private  String tripId,returntripId;
    private  DatabaseReference mSeats;

    private TextView backtoSeat;
    private FirebaseAuth mAuth;

    private DatabaseReference mTrips = FirebaseDatabase.getInstance().getReference("Trips");
    private DatabaseReference mTicket =  FirebaseDatabase.getInstance().getReference("Ticket");
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectseat2);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        returntripId = intent.getStringExtra("ReturnTripId");
        tripId = intent.getStringExtra("TripId");
        isReturn2 = intent.getBooleanExtra("isReturn",false);
        boolean reserved1 = intent.getBooleanExtra("reserve",false);
        reserved = reserved1;
        System.out.println("SelectSeat2:  " + reserved);
        selectedSeats=intent.getIntegerArrayListExtra("selectedSeats");
        seatsFromSeatActivityOne = intent.getStringExtra("roundTrip");
        mSeats = FirebaseDatabase.getInstance().getReference("Trips");
        layout = findViewById(R.id.layoutSeat);
        LinearLayout layoutSeat = new LinearLayout(Selectseat2Activity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);
        System.out.println(returntripId);
        mSeats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    seats = snapshot.child(returntripId).child("TripSeats").child("Seat").getValue().toString();
                    LinearLayout layout = null;
                    int count = 0;
                    for (int index = 0; index < seats.length(); index++) {
                        if (seats.charAt(index) == '/') {
                            layout = new LinearLayout(Selectseat2Activity.this);
                            layout.setOrientation(LinearLayout.HORIZONTAL);
                            layoutSeat.addView(layout);
                        } else if (seats.charAt(index) == 'U') {
                            count++;
                            seatOriantation.add(index);
                            seatLocation.add("U");
                            TextView view = new TextView(Selectseat2Activity.this);
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
                            view.setOnClickListener(Selectseat2Activity.this);
                        } else if (seats.charAt(index) == 'A') {
                            count++;
                            seatOriantation.add(index);
                            seatLocation.add("A");
                            TextView view = new TextView(Selectseat2Activity.this);
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
                            view.setOnClickListener(Selectseat2Activity.this);
                        } else if (seats.charAt(index) == '_') {
                            TextView view = new TextView(Selectseat2Activity.this);
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
                Toast.makeText(Selectseat2Activity.this,"Something went wrong! DATABASE CONNECTİON FAİLED.",Toast.LENGTH_SHORT).show();
                Toast.makeText(Selectseat2Activity.this,"DATABASE CONNECTİON FAİLED.",Toast.LENGTH_SHORT).show();
                Toast.makeText(Selectseat2Activity.this,"Please Try Again Later",Toast.LENGTH_SHORT).show();
                Toast.makeText(Selectseat2Activity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Selectseat2Activity.this,MainActivity.class);
                startActivity(intent);
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
            if (!selectedSeatsReturn.contains(view.getId()))
                selectedSeatsReturn.add(view.getId());
            view.setTag(STATUS_RESERVED);

        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            view.setBackgroundResource(R.drawable.ic_seats_book);
            int in = view.getId();
            char[] myNameChars = seats.toCharArray();
            myNameChars[seatOriantation.get(in - 1)] = 'A';
            seats = String.valueOf(myNameChars);
            for ( int i =0;i<selectedSeatsReturn.size();i++){
                if (selectedSeatsReturn.get(i).equals(view.getId())){
                    selectedSeatsReturn.remove(i);
                }
            }

            view.setTag(STATUS_AVAILABLE);
        }
    }

    public void selectSeat2(View v) {
        if(selectedSeatsReturn.size()>0) {


            if(reserved){
                mDatabase.child("autoTicketID").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot autoTicket) {
                        autoTicketID = Integer.parseInt(autoTicket.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Selectseat2Activity.this,"Something went wrong! DATABASE CONNECTİON FAİLED.",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Selectseat2Activity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Selectseat2Activity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                mTrips.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){

                                String userID = mAuth.getCurrentUser().getEmail();

                                String TicketIdDept="PNR2021" + autoTicketID;
                                String fromDeparture = snapshot.child(tripId).child("from").getValue().toString();
                                String toDeparture = snapshot.child(tripId).child("to").getValue().toString();
                                String departureTimeDeparture = snapshot.child(tripId).child("departuretime").getValue().toString();
                                String arrivalTimeDeparture = snapshot.child(tripId).child("arrivaltime").getValue().toString();
                                String dateDeparture= snapshot.child(tripId).child("date").getValue().toString();
                                String priceDeparture = snapshot.child(tripId).child("price").getValue().toString();
                                String busPlateDeparture = snapshot.child(tripId).child("busPlate").getValue().toString();

                                String fromArrival = snapshot.child(returntripId).child("from").getValue().toString();
                                String toArrival = snapshot.child(returntripId).child("to").getValue().toString();
                                String departureTimeArrival = snapshot.child(returntripId).child("departuretime").getValue().toString();
                                String arrivalTimeArrival = snapshot.child(returntripId).child("arrivaltime").getValue().toString();
                                String dateArrival= snapshot.child(returntripId).child("date").getValue().toString();
                                String priceArrival = snapshot.child(returntripId).child("price").getValue().toString();
                                String busPlateArrival = snapshot.child(returntripId).child("busPlate").getValue().toString();

                                Ticket a = new Ticket(tripId,TicketIdDept,userID,fromDeparture,toDeparture,departureTimeDeparture,arrivalTimeDeparture,dateDeparture,priceDeparture,selectedSeats);
                                mTicket.child(TicketIdDept).child("tripId").setValue(tripId);
                                mTicket.child(TicketIdDept).child("ticketId").setValue(TicketIdDept);
                                mTicket.child(TicketIdDept).child("from").setValue(fromDeparture);
                                mTicket.child(TicketIdDept).child("to").setValue(toDeparture);
                                mTicket.child(TicketIdDept).child("deptime").setValue(departureTimeDeparture);
                                mTicket.child(TicketIdDept).child("arrivetime").setValue(arrivalTimeDeparture);
                                mTicket.child(TicketIdDept).child("date").setValue(dateDeparture);
                                mTicket.child(TicketIdDept).child("price").setValue(priceDeparture);
                                mTicket.child(TicketIdDept).child("userID").setValue(userID);
                                mTicket.child(TicketIdDept).child("isReserved").setValue(reserved);
                                mTicket.child(TicketIdDept).child("busPlate").setValue(busPlateDeparture);

                                //
                                autoTicketID++;
                                String SeatsDeparture ="";
                                for(int i=0 ; i<selectedSeats.size() ; i++){
                                    if(SeatsDeparture.equals("")){
                                        SeatsDeparture =selectedSeats.get(i).toString();
                                    }else
                                        SeatsDeparture = SeatsDeparture + " --> " +  selectedSeats.get(i).toString();
                                }
                                mTicket.child(TicketIdDept).child("seats").setValue(SeatsDeparture);
                                mDatabase.child("autoTicketID").setValue(autoTicketID);
                                //

                                String TicketIdArrive="PNR2021" + autoTicketID;

                                Ticket b = new Ticket(returntripId,TicketIdArrive,userID,fromArrival,toArrival,departureTimeArrival,arrivalTimeArrival,dateArrival,priceArrival,selectedSeatsReturn);
                                mTicket.child(TicketIdArrive).child("tripId").setValue(returntripId);
                                mTicket.child(TicketIdArrive).child("ticketId").setValue(TicketIdArrive);
                                mTicket.child(TicketIdArrive).child("from").setValue(fromArrival);
                                mTicket.child(TicketIdArrive).child("to").setValue(toArrival);
                                mTicket.child(TicketIdArrive).child("deptime").setValue(departureTimeArrival);
                                mTicket.child(TicketIdArrive).child("arrivetime").setValue(arrivalTimeArrival);
                                mTicket.child(TicketIdArrive).child("date").setValue(dateArrival);
                                mTicket.child(TicketIdArrive).child("price").setValue(priceArrival);
                                mTicket.child(TicketIdArrive).child("userID").setValue(userID);
                                mTicket.child(TicketIdArrive).child("isReserved").setValue(reserved);
                                mTicket.child(TicketIdArrive).child("busPlate").setValue(busPlateArrival);

                                //
                                autoTicketID++;
                                String SeatsArrival ="";
                                for(int i=0 ; i<selectedSeatsReturn.size() ; i++){
                                    if(SeatsArrival.equals("")){
                                        SeatsArrival =selectedSeatsReturn.get(i).toString();
                                    }else
                                        SeatsArrival = SeatsArrival + " --> " +  selectedSeatsReturn.get(i).toString();
                                }
                                mTicket.child(TicketIdArrive).child("seats").setValue(SeatsArrival);
                                mDatabase.child("autoTicketID").setValue(autoTicketID);
                                //

                                mTrips.child(tripId).child("TripSeats").child("Seat").setValue(seatsFromSeatActivityOne);
                                mTrips.child(returntripId).child("TripSeats").child("Seat").setValue(seats);

                                Toast.makeText(Selectseat2Activity.this, "Your seat(s) has ben reserved! Have a nice trip.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Selectseat2Activity.this,UserAccountActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Selectseat2Activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                            Toast.makeText(Selectseat2Activity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Selectseat2Activity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
             //   finish();
            }
            else if (mAuth.getCurrentUser() == null) {
                Intent intent = new Intent(this, UnregisteredUserInfo.class);
                intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);
                intent.putIntegerArrayListExtra("selectedSeatsReturn", selectedSeatsReturn);
                intent.putExtra("ReturnTripId", returntripId);
                intent.putExtra("TripId", tripId);
                intent.putExtra("isReturn", isReturn2);
                intent.putExtra("selectSeatTwo", seats);
                intent.putExtra("selectSeatOne", seatsFromSeatActivityOne);
                intent.putExtra("reserve", reserved);
                startActivity(intent);
               // finish();
            }
            else{
                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);
                intent.putIntegerArrayListExtra("selectedSeatsReturn", selectedSeatsReturn);
                intent.putExtra("ReturnTripId", returntripId);
                intent.putExtra("TripId", tripId);
                intent.putExtra("isReturn", isReturn2);
                intent.putExtra("selectSeatTwo", seats);
                intent.putExtra("selectSeatOne", seatsFromSeatActivityOne);
                intent.putExtra("reserve", reserved);

                startActivity(intent);
                //finish();
            }
        }
        else{
            Toast.makeText(this, "Choose at least one seat.", Toast.LENGTH_SHORT).show();
        }

    }

}





