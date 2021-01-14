package com.example.intercitybusticketapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBusActivity extends AppCompatActivity {
    private EditText plateNo;
    private EditText capacity;
    private BusModel busmodel;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        plateNo = findViewById(R.id.inputBusPlate);
        capacity = findViewById(R.id.inputBusCapacity);
        busmodel = new BusModel();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void addbus(View view) {
        String plate = plateNo.getText().toString();
        String capacityy = capacity.getText().toString();

        if (TextUtils.isEmpty(plate) || TextUtils.isEmpty(capacityy)) {
            Toast.makeText(AddBusActivity.this, "All information are required please check", Toast.LENGTH_LONG).show();
        } else {
                mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.hasChild(plate)){
                                Toast.makeText(AddBusActivity.this, "THİS PLATE NUMBER İS ALREADY ON THE SYSTEM.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Bus b = new Bus(plate, capacityy);
                                busmodel.setBus(b);
                                Toast.makeText(AddBusActivity.this, "BUS WAS CREATED SUCCESFULLY", Toast.LENGTH_SHORT).show();
                                Toast.makeText(AddBusActivity.this, b.toString(), Toast.LENGTH_SHORT).show();
                                mDatabase.child("Buses").child(plate).child("Capacity").setValue(capacityy);
                                mDatabase.child("Buses").child(plate).child("Plate").setValue(plate);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddBusActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddBusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddBusActivity.this,AdminActivity.class);
                        startActivity(intent);
                    }
                });


        }
    }
}