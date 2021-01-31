package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAccountActivity extends AppCompatActivity {
    private ImageView back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        back = findViewById(R.id.backToMain);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            });
    }

    public void logOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void changeMyPassword(View view) {
        Intent intent = new Intent(UserAccountActivity.this, PasswordChangeActivity.class);
        startActivity(intent);
    }


    public void showMyTickets(View view) {
        Intent intent = new Intent(UserAccountActivity.this, MyTicketsActivity.class);
        startActivity(intent);



       /*Query myTickets = mTicket.orderByChild("userID").equalTo(mAuth.getCurrentUser().getEmail());
        myTickets.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        System.out.println(child.getKey());
                    }
                } else {
                    Toast.makeText(UserAccountActivity.this, "Ticket(s) Cannot Found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserAccountActivity.this, "Something went wrong! DATABASE CONNECTİON HAS FAİLED.", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserAccountActivity.this, "Please Try Again With Better Connection.", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserAccountActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/



    }

    public void cancelReservation(View view) {
        Intent intent = new Intent(UserAccountActivity.this, CancelTicketActivity.class);
        startActivity(intent);
    }




}