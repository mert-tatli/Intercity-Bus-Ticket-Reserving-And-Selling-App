package com.example.intercitybusticketapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UnregisteredTicketSearch extends AppCompatActivity {
    private EditText ticketMail1;
    private DatabaseReference mTicket;
    private ArrayList<Ticket> ticketss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregistered_ticket_search);
        ticketMail1 = findViewById(R.id.ticketMail);
        mTicket = FirebaseDatabase.getInstance().getReference("Ticket");
        ticketss = new ArrayList<>();
    }

    public void onClickCheckButton(View view) {
        String userMail = ticketMail1.getText().toString();
        Query query = mTicket.orderByChild("userID").equalTo(userMail);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
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
                    ArrayList<Integer> a = new ArrayList();
                    String emptiedSeats = snapshot.child("seats").getValue().toString();
                    String[] freeSeatNumbers = emptiedSeats.split(" --> ");
                    for (int i = 0; i < freeSeatNumbers.length; i++) {
                        a.add(Integer.parseInt(freeSeatNumbers[i]));
                    }
                    ticket1.setSeats(a);
                    System.out.println(ticket1.toString());
                    ticketss.add(ticket1);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(UnregisteredTicketSearch.this, "Something went wrong! DATABASE CONNECTİON FAİLED.", Toast.LENGTH_SHORT).show();
            Toast.makeText(UnregisteredTicketSearch.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UnregisteredTicketSearch.this, UnregisteredTicketSearch.class);
            startActivity(intent);
        }
    };
}
