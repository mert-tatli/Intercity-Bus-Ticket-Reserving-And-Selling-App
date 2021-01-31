package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteBusActivity extends AppCompatActivity {
    private Spinner deletebusId;
    private DatabaseReference mDatabase;
    private static ArrayList<String> buses ;
    private String busplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);
        deletebusId = findViewById(R.id.inputBusPlateDelete);
        buses= new ArrayList<>();
        buses.add("Select The Bus");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        buses.add(snapshot1.getKey());
                    }

                    deletebusId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                            busplate = buses.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }

                    });

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteBusActivity.this,
                            android.R.layout.simple_spinner_item, buses);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    deletebusId.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteBusActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Toast.makeText(DeleteBusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteBusActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });


    }

    public void deleteBus(View view) {
        for (int i = 0; i < buses.size(); i++) {
            System.out.println(buses.get(i));
        }
        if (busplate.equals("Select The Bus")) {
            Toast.makeText(DeleteBusActivity.this, "Please Select a Bus", Toast.LENGTH_SHORT).show();
        } else {

            mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.hasChild(busplate)) {
                            deletebusId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                    busplate = buses.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });

                            if (snapshot.child(busplate).hasChild("Trip")) { //OTOBUSUN TRİP i VAR İSE


                                String currentTrip = snapshot.child(busplate).child("Trip").getValue().toString();
                                mDatabase.child("Trips").child(currentTrip).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            buses.remove(busplate);
                                            mDatabase.child("Buses").child(busplate).setValue(null);
                                            Toast.makeText(DeleteBusActivity.this, "Bus deleted", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(DeleteBusActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteBusActivity.this,
                                        android.R.layout.simple_spinner_item, buses);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                deletebusId.setAdapter(adapter);
                                mDatabase.child("Buses").child(busplate).setValue(null);
                                buses.remove(busplate);
                                Toast.makeText(DeleteBusActivity.this, "Bus deleted", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(DeleteBusActivity.this, "Bus can not found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DeleteBusActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DeleteBusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteBusActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}