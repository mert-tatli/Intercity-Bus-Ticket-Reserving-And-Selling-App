package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteBusActivity extends AppCompatActivity {
    EditText deletebusId;
    Button deletebusButton;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bus);
        deletebusId = findViewById(R.id.inputBusPlateDelete);
        deletebusButton =findViewById(R.id.deleteBus);
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }
    public void deleteBus(View view){
        String deleteBusIds = deletebusId.getText().toString();
        mDatabase.child("Buses").child(deleteBusIds).setValue(null);
        Toast.makeText(DeleteBusActivity.this, "Bus deleted", Toast.LENGTH_LONG).show();
    }
}