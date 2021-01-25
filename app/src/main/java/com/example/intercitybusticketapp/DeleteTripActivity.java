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
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteTripActivity extends AppCompatActivity {
    private Spinner deleteTripId;
    private String deleteid;
    private DatabaseReference mDatabase;
    private ArrayList<String> trips=new ArrayList<>();
    private NotificationManagerCompat managerCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_trip);
        deleteTripId = findViewById(R.id.inputDeleteTrip);
        createNotificationChannel();
        managerCompat = NotificationManagerCompat.from(this);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        trips.add(snapshot1.getKey());
                    }
                    deleteTripId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                            deleteid = trips.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteTripActivity.this,
                            android.R.layout.simple_spinner_item, trips);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    deleteTripId.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }




        });
    }
    public void deleteTrip(View view){
        if(TextUtils.isEmpty(deleteid)){
            Toast.makeText(DeleteTripActivity.this, "Fill the Text Area", Toast.LENGTH_LONG).show();
        }else{
            mDatabase.child("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        if(snapshot.hasChild(deleteid)){
                            mDatabase.child("Trips").child(deleteid).setValue(null);
                            deleteTripId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                    deleteid = trips.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteTripActivity.this,
                                    android.R.layout.simple_spinner_item, trips);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            deleteTripId.setAdapter(adapter);
                            trips.remove(deleteid);
                            mDatabase.child("Buses").child(snapshot.child(deleteid).child("busPlate").getValue().toString()).child("Trip").setValue(null);

                            mDatabase.child("Ticket").orderByChild("tripId").equalTo(deleteid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot child : snapshot.getChildren()){
                                            String mailAdress =child.child("userID").getValue().toString();
                                            mDatabase.child("Ticket").child(child.getKey()).setValue(null);
                                        }



                                    }
                                    else{
                                        Toast.makeText(DeleteTripActivity.this, "Trip Deleted.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(DeleteTripActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                                    Toast.makeText(DeleteTripActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DeleteTripActivity.this,AdminActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }else{
                            Toast.makeText(DeleteTripActivity.this, "Trip Can not Found.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(DeleteTripActivity.this, "Couldn't find anything on Database", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DeleteTripActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                    Toast.makeText(DeleteTripActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteTripActivity.this,AdminActivity.class);
                    startActivity(intent);
                }
            });


        }
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1= new NotificationChannel("ID","Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("This is channel 1");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }



}