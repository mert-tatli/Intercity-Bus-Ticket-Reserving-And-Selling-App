package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAccountActivity extends AppCompatActivity {
    private ImageView back;
    private FirebaseAuth mAuth;
    private DatabaseReference mTicket,mTrips;
    private String emptiedSeats;
    private ArrayList<Integer> freeSeatNumbersList;
    //private ArrayList<Object> Deneme;
    private String[] freeSeatNumbers,currentSeats;


    private String tripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        back=findViewById(R.id.backToMain);
        mAuth = FirebaseAuth.getInstance();
        freeSeatNumbersList = new ArrayList<>();
        //Deneme = new ArrayList<>();
        mTicket= FirebaseDatabase.getInstance().getReference("Ticket");
        mTrips= FirebaseDatabase.getInstance().getReference("Trips");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserAccountActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void logOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(UserAccountActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void changeMyPassword(View view){
       Intent intent = new Intent(UserAccountActivity.this,PasswordChangeActivity.class);
       startActivity(intent);
    }


    public void showMyTickets(View view){
        Query myTickets = mTicket.orderByChild("userID").equalTo(mAuth.getCurrentUser().getEmail());
        myTickets.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot child: snapshot.getChildren()){
                        System.out.println(child.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void cancelReservation(View view){
        String ticketID = "Ticket6";
       mTicket.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){

                   tripID=snapshot.child("tripId").getValue().toString();
                   emptiedSeats = snapshot.child("seats").getValue().toString();
                   freeSeatNumbers= emptiedSeats.split(" --> ");
                    for(int i =0;i<freeSeatNumbers.length;i++){
                        freeSeatNumbersList.add(Integer.parseInt(freeSeatNumbers[i]));
                    }

                   mTrips.child(tripID).child("TripSeats").child("Seat").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.exists()){
                               currentSeats = snapshot.getValue().toString().split("");
                           }
                       }
                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                           Toast.makeText(UserAccountActivity.this,"Something went wrong! DATABASE CONNECTİON HAS FAİLED.",Toast.LENGTH_SHORT).show();
                           Toast.makeText(UserAccountActivity.this,"Please Try Again With Better Connection.",Toast.LENGTH_SHORT).show();
                           Toast.makeText(UserAccountActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(UserAccountActivity.this,MainActivity.class);
                           startActivity(intent);
                       }
                   });









               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(UserAccountActivity.this,"Something went wrong! DATABASE CONNECTİON HAS FAİLED.",Toast.LENGTH_SHORT).show();
               Toast.makeText(UserAccountActivity.this,"Please Try Again With Better Connection.",Toast.LENGTH_SHORT).show();
               Toast.makeText(UserAccountActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(UserAccountActivity.this,MainActivity.class);
               startActivity(intent);
           }
       });








    }





}