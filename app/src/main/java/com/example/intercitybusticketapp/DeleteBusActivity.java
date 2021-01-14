package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class DeleteBusActivity extends AppCompatActivity {
    private  EditText deletebusId;
    private Button deletebusButton;
    private DatabaseReference mDatabase;
    private String[] buses;
    private String deletingPlate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);
        deletebusId = findViewById(R.id.inputBusPlateDelete);
        deletebusButton =findViewById(R.id.deleteBus);
        Spinner spinner = findViewById(R.id.spinnerDeleteBus);
        mDatabase= FirebaseDatabase.getInstance().getReference();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                deletingPlate = buses[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    buses = new String[(int)snapshot.getChildrenCount()];
                    int index =0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        buses[index] = snapshot1.getKey();
                        index++;
                    }
                    for(int i =0;i<buses.length;i++){
                        System.out.println(buses[i]);
                    }
                }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void deleteBus(View view){
        mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.hasChild(deletingPlate)){
                        String deleteBusIds = deletebusId.getText().toString();
                        mDatabase.child("Buses").child(deletingPlate).setValue(null);
                        Toast.makeText(DeleteBusActivity.this, "Bus deleted", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(DeleteBusActivity.this, "Bus can not found.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}