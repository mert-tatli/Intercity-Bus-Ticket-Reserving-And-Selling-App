package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteTripActivity extends AppCompatActivity {
    private EditText deleteTripId;
    private Button deleteTripButton;
    private DatabaseReference mDatabase;
    private String[] trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_trip);
        deleteTripId = findViewById(R.id.inputTripIdDelete);
        deleteTripButton =findViewById(R.id.deleteTrip);
        mDatabase= FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    trips = new String[(int)snapshot.getChildrenCount()];
                    int index =0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        trips[index] = snapshot1.getKey();
                        index++;
                    }
                    for(int i =0;i<trips.length;i++){
                        System.out.println(trips[i]);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteTrip(View view){
        String deleteTripIds = deleteTripId.getText().toString();
        mDatabase.child("Trips").child(deleteTripIds).setValue(null);
        Toast.makeText(DeleteTripActivity.this, "Trip deleted", Toast.LENGTH_LONG).show();
    }

}