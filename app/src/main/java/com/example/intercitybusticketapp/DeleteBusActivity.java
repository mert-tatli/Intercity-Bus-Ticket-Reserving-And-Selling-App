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

public class DeleteBusActivity extends AppCompatActivity {
    private  EditText deletebusId;
    private Button deletebusButton;
    private DatabaseReference mDatabase;
    private String[] buses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);
        deletebusId = findViewById(R.id.inputBusPlateDelete);
        deletebusButton =findViewById(R.id.deleteBus);
        mDatabase= FirebaseDatabase.getInstance().getReference();
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
       if(TextUtils.isEmpty(deletebusId.getText().toString())){
           Toast.makeText(DeleteBusActivity.this, "Fill the Text Area", Toast.LENGTH_SHORT).show();
       }else{
           String deleteBusIds = deletebusId.getText().toString();
        mDatabase.child("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.hasChild(deleteBusIds)){
                        mDatabase.child("Buses").child(deleteBusIds).setValue(null);
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