package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteTripActivity extends AppCompatActivity {
    EditText deleteTripId;
    Button deleteTripButton;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_trip);
        deleteTripId = findViewById(R.id.inputTripIdDelete);
        deleteTripButton =findViewById(R.id.deleteTrip);
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }

    public void deleteTrip(View view){
        String deleteTripIds = deleteTripId.getText().toString();
        mDatabase.child("Trips").child(deleteTripIds).setValue(null);
        Toast.makeText(DeleteTripActivity.this, "Trip deleted", Toast.LENGTH_LONG).show();
    }

}