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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteBusActivity extends AppCompatActivity {
    private Spinner deletebusId;
    private DatabaseReference mDatabase;
    private ArrayList<String> buses =new ArrayList<>();
    private String busplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);
        deletebusId = findViewById(R.id.inputBusPlateDelete);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int index =0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        buses.add(snapshot1.getKey());
                        index++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, buses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deletebusId.setAdapter(adapter);

    }

    public void deleteBus(View view){
       if(TextUtils.isEmpty(busplate)){
           Toast.makeText(DeleteBusActivity.this, "Select The Bus", Toast.LENGTH_SHORT).show();
       }else{

            mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.hasChild(busplate)){
                        mDatabase.child("Buses").child(busplate).setValue(null);
                        Toast.makeText(DeleteBusActivity.this, "Bus deleted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(DeleteBusActivity.this, "Bus can not found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteBusActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                Toast.makeText(DeleteBusActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteBusActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

    }
    }
}