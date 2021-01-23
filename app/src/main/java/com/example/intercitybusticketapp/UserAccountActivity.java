package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private String emptiedSeats,currentSeats,from,to,date,ticketID;
    private ArrayList<Integer> freeSeatNumbersList;
    private String[] freeSeatNumbers;
    private NotificationManagerCompat managerCompat;
    private ArrayList<String> userTickets;
    private Spinner cancelReservationSpinner;



    private String tripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        back=findViewById(R.id.backToMain);
        cancelReservationSpinner =findViewById(R.id.TicketSpinner);
        mAuth = FirebaseAuth.getInstance();
        freeSeatNumbersList = new ArrayList<>();
        userTickets = new ArrayList<>();
        createNotificationChannel();

        mTicket= FirebaseDatabase.getInstance().getReference("Ticket");
        mTrips= FirebaseDatabase.getInstance().getReference("Trips");

        mTicket.orderByChild("userID").equalTo(mAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot ticket : snapshot.getChildren()) {
                        userTickets.add(ticket.getKey());

                    }
                    for(int i =0;i<userTickets.size();i++){
                        System.out.println(userTickets.get(i));
                    }

                    cancelReservationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                            ticketID = userTickets.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }

                    });

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserAccountActivity.this,
                            android.R.layout.simple_spinner_item, userTickets);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cancelReservationSpinner.setAdapter(adapter);
                }
                else{

                    Toast.makeText(UserAccountActivity.this,"Ticket(s) Cannot Found.",Toast.LENGTH_SHORT).show();
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
                else{
                    Toast.makeText(UserAccountActivity.this,"Ticket(s) Cannot Found.",Toast.LENGTH_SHORT).show();
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


    public void cancelReservation(View view){
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

                    mTrips.child(tripID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                from =snapshot.child("from").getValue().toString();
                                to= snapshot.child("to").getValue().toString();
                                date= snapshot.child("date").getValue().toString();
                            }
                            else{
                                Toast.makeText(UserAccountActivity.this,"Trip Cannot Found.",Toast.LENGTH_SHORT).show();
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


                   mTrips.child(tripID).child("TripSeats").child("Seat").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if(snapshot.exists()){
                               currentSeats = snapshot.getValue().toString();
                               char[] currentSeatsArray = currentSeats.toCharArray();
                               int index =0;
                               int counter=0;
                               System.out.println("ESKİ HALİ : " + currentSeats);
                               for(int i =0;i<currentSeatsArray.length;i++){
                                   if(currentSeatsArray[i]== 'A' || currentSeatsArray[i]=='U'){
                                       index++;
                                       if(freeSeatNumbersList.contains(index)){
                                           currentSeatsArray[i]='A';
                                           counter++;
                                       }
                                   }
                                   if(counter==freeSeatNumbersList.size()){
                                       break;
                                   }
                               }
                               currentSeats = String.copyValueOf(currentSeatsArray);
                               System.out.println("YENİ HALİ : " + currentSeats);
                               mTrips.child(tripID).child("TripSeats").child("Seat").setValue(currentSeats).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()) {


                                           cancelReservationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                                   ticketID = userTickets.get(position);
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> arg0) {
                                                   // TODO Auto-generated method stub
                                               }

                                           });

                                           ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserAccountActivity.this,
                                                   android.R.layout.simple_spinner_item, userTickets);
                                           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                           cancelReservationSpinner.setAdapter(adapter);
                                           userTickets.remove(ticketID);



                                           mTicket.child(ticketID).setValue(null);
                                           Toast.makeText(UserAccountActivity.this, "Your Reservation has been Canceled Successfully.", Toast.LENGTH_SHORT).show();
                                           Toast.makeText(UserAccountActivity.this, "Dear Passenger, Your Reservation of Trip on " + date + " From: " + from + " To: " +
                                                   to + "has been canceled.  TripID: " + tripID, Toast.LENGTH_SHORT).show();



                                           /*Notification notification = new NotificationCompat.Builder(UserAccountActivity.this, "cancelReservation")
                                                   .setContentTitle("Your Reservation Has Been Canceled!")
                                                   .setContentText("Dear Passenger, Your Reservation of Trip on " + date + " From: " + from + " To: " + to + "has been canceled.  TripID: " + tripID)
                                                   .setPriority(NotificationCompat.PRIORITY_MAX)
                                                   .setCategory(NotificationCompat.CATEGORY_EVENT)
                                                   .setSmallIcon(R.drawable.notification_bus).build();

                                           managerCompat.notify(2, notification);*/
                                       }
                                       else{
                                           Toast.makeText(UserAccountActivity.this, "Sorry we couldn't answer your request. Please try Again Later.", Toast.LENGTH_SHORT).show();

                                       }
                                   }
                               });



                           }else{
                               Toast.makeText(UserAccountActivity.this,"Ticket Cannot Found.",Toast.LENGTH_SHORT).show();
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

               }else{
                   Toast.makeText(UserAccountActivity.this,"Ticket Cannot Found.",Toast.LENGTH_SHORT).show();
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

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1= new NotificationChannel("cancelReservation","channelReservation.", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is channelReservation 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }





}