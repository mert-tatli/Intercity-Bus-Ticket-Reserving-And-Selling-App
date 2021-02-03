package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CancelTicketActivity extends AppCompatActivity {
    Spinner ticketSpinner;
    private FirebaseAuth mAuth;
    private DatabaseReference mTicket, mTrips;
    private String emptiedSeats, currentSeats, from, to, date;
    private String  ticketID="";
    private ArrayList<Integer> freeSeatNumbersList;
    private String[] freeSeatNumbers;
    private NotificationManagerCompat managerCompat;
    private ArrayList<String> userTickets;
    private String tripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_ticket);
        ticketSpinner = findViewById(R.id.ticketSpinner);
        mAuth = FirebaseAuth.getInstance();
        freeSeatNumbersList = new ArrayList<>();
        userTickets = new ArrayList<>();
        userTickets.add("Select The Reservation PNR");
        createNotificationChannel();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            mTicket = FirebaseDatabase.getInstance().getReference("Ticket");
            mTrips = FirebaseDatabase.getInstance().getReference("Trips");

            mTicket.orderByChild("userID").equalTo(mAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ticket : snapshot.getChildren()) {
                            if ((Boolean) ticket.child("isReserved").getValue()) {
                                userTickets.add(ticket.getKey());
                            }
                        }
                        ticketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                ticketID = userTickets.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }

                        });

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, userTickets);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ticketSpinner.setAdapter(adapter);
                    } else {

                        Toast.makeText(getApplicationContext(), "Ticket(s) Cannot Found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Something went wrong! DATABASE CONNECTİON HAS FAİLED.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Please Try Again With Better Connection.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });


        }
    }

        public void cancelReservation (View view){

        if (!ticketID.equals("Select The Reservation PNR")){
            mTicket.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        tripID = snapshot.child("tripId").getValue().toString();
                        emptiedSeats = snapshot.child("seats").getValue().toString();
                        freeSeatNumbers = emptiedSeats.split(" --> ");
                        for (int i = 0; i < freeSeatNumbers.length; i++) {
                            freeSeatNumbersList.add(Integer.parseInt(freeSeatNumbers[i]));
                        }

                        mTrips.child(tripID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    from = snapshot.child("from").getValue().toString();
                                    to = snapshot.child("to").getValue().toString();
                                    date = snapshot.child("date").getValue().toString();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Trip Cannot Found.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Something went wrong! DATABASE CONNECTİON HAS FAİLED.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Please Try Again With Better Connection.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });


                        mTrips.child(tripID).child("TripSeats").child("Seat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    currentSeats = snapshot.getValue().toString();
                                    char[] currentSeatsArray = currentSeats.toCharArray();
                                    int index = 0;
                                    int counter = 0;
                                    for (int i = 0; i < currentSeatsArray.length; i++) {
                                        if (currentSeatsArray[i] == 'A' || currentSeatsArray[i] == 'U') {
                                            index++;
                                            if (freeSeatNumbersList.contains(index)) {
                                                currentSeatsArray[i] = 'A';
                                                counter++;
                                            }
                                        }
                                        if (counter == freeSeatNumbersList.size()) {
                                            break;
                                        }
                                    }
                                    currentSeats = String.copyValueOf(currentSeatsArray);
                                    mTrips.child(tripID).child("TripSeats").child("Seat").setValue(currentSeats).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                ticketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                                        ticketID = userTickets.get(position);
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> arg0) {
                                                        // TODO Auto-generated method stub
                                                    }

                                                });

                                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                                        android.R.layout.simple_spinner_item, userTickets);
                                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                ticketSpinner.setAdapter(adapter);
                                                userTickets.remove(ticketID);


                                                mTicket.child(ticketID).setValue(null);
                                                Toast.makeText(getApplicationContext(), "Your Reservation has been Canceled Successfully.", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Dear Passenger, Your Reservation of Trip on " + date + " From: " + from + " To: " +
                                                        to + "has been canceled.  TripID: " + tripID, Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Sorry we couldn't answer your request. Please try Again Later.", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(getApplicationContext(), "Ticket Cannot Found.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Something went wrong! DATABASE CONNECTİON HAS FAİLED.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Please Try Again With Better Connection.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Ticket Cannot Found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Something went wrong! DATABASE CONNECTİON HAS FAİLED.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Please Try Again With Better Connection.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Select a Reservation PNR", Toast.LENGTH_SHORT).show();
        }

        }

        private void createNotificationChannel () {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel("cancelReservation", "channelReservation.", NotificationManager.IMPORTANCE_HIGH);
                channel1.setDescription("This is channelReservation 1");
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel1);
            }
        }
}