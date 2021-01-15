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

public class DeleteTripActivity extends AppCompatActivity {
    private Spinner deleteTripId;
    private String deleteid;
    private DatabaseReference mDatabase;
    private ArrayList<String> trips=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_trip);
        deleteTripId = findViewById(R.id.inputDeleteTrip);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int index =0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        trips.add(snapshot1.getKey());
                        index++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, trips);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteTripId.setAdapter(adapter);

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
                            Toast.makeText(DeleteTripActivity.this, "Trip deleted", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(DeleteTripActivity.this, "Trip Can not Found.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(DeleteTripActivity.this, "Couldn't find anything on Database", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DeleteTripActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                    Toast.makeText(DeleteTripActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DeleteTripActivity.this,AdminActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}