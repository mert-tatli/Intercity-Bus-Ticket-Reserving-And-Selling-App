package com.example.intercitybusticketapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UnregisteredTicketSearch extends AppCompatActivity {
    private EditText ticketMail1;
    private DatabaseReference mTicket;
    private ArrayList<Ticket> ticketss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregistered_ticket_search);
        ticketMail1 = findViewById(R.id.ticketMail);
        mTicket = FirebaseDatabase.getInstance().getReference("Ticket");
        ticketss = new ArrayList<>();
    }

    public void onClickCheckButton(View view) {

        if (validateEmail(ticketMail1))
        {
            String userMail = ticketMail1.getText().toString();
            Intent intent=new Intent(getApplicationContext(),MyTicketsActivity.class);
            intent.putExtra("email",userMail);
            startActivity(intent);
        }

    }
    private boolean validateEmail(EditText email){
        String emailinput = ticketMail1.getText().toString();

        if(!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){

            return true;
        }else{
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
