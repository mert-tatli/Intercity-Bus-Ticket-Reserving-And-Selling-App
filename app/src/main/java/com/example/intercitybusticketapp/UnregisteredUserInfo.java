package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class UnregisteredUserInfo extends AppCompatActivity {
    private Button buttonSign;
    private EditText id, name, surname, phone, email;

    boolean isReturn2;
    private  String tripId, returntripId;
    private ArrayList<Integer> selectedSeatsReturn = new ArrayList<>();
    private ArrayList<Integer> selectedSeats = new ArrayList<>();

    private String selectSeatOne;
    private String selectSeatOne1;
    private String selectSeatTwo;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregistered_user_info);
        Intent intent = getIntent();
        isReturn2 = intent.getBooleanExtra("isReturn", false);
        if (isReturn2) {
            selectedSeatsReturn = intent.getIntegerArrayListExtra("selectedSeatsReturn");
            returntripId = intent.getStringExtra("ReturnTripId");
            selectSeatTwo = intent.getStringExtra("selectSeatTwo");
            selectSeatOne1 = intent.getStringExtra("selectSeatOne");
            selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
            tripId = intent.getStringExtra("TripId");
        }else {
            selectedSeats = intent.getIntegerArrayListExtra("selectedSeats");
            tripId = intent.getStringExtra("TripId");
            selectSeatOne = intent.getStringExtra("oneWaySeats");
        }

        System.out.println("ÇİFT YÖN GİDİŞ : " + selectSeatOne1);
        System.out.println("ÇİFT YÖN DÖNÜŞ : " + selectSeatTwo);

        System.out.println("Sadece Gidiş : " + selectSeatOne);



        id = findViewById(R.id.idNumberUnregistered);
        name =  findViewById(R.id.inputNameUnregistered);
        surname = findViewById(R.id.inputSurnameUnregistered);
        phone = findViewById(R.id.inputPhoneNoUnregistered);
        email = findViewById(R.id.inputEmailUnregistered);
        buttonSign = findViewById(R.id.buttonSignUpUnregistered);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    private boolean validateEmail(EditText email){
        String emailinput = email.getText().toString();

        if(!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){

            return true;
        }else{
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void backbutton1(View view) {
        Intent intent = new Intent(UnregisteredUserInfo.this, SelectSeatActivity.class);
        startActivity(intent);
    }

    public void onClickSaveUnregistered(View view) {

        String id1 = id.getText().toString();
        String name1 = name.getText().toString();
        String surname1 = surname.getText().toString();
        String phone1 = phone.getText().toString();
        String email1 = email.getText().toString();

        if (TextUtils.isEmpty(id1) || TextUtils.isEmpty(name1) || TextUtils.isEmpty(surname1) || TextUtils.isEmpty(phone1) || TextUtils.isEmpty(email1) || !validateEmail(email)) {
            Toast.makeText(UnregisteredUserInfo.this, "All the Information Are Required", Toast.LENGTH_SHORT).show();
        } else {
            mDatabase.child("users").child(id1).child("id").setValue(id1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mDatabase.child("users").child(id1).child("name").setValue(name1);
                        mDatabase.child("users").child(id1).child("surname").setValue(surname1);
                        mDatabase.child("users").child(id1).child("phone").setValue(phone1);
                        mDatabase.child("users").child(id1).child("email").setValue(email1);
                        Intent intent = new Intent(UnregisteredUserInfo.this, PaymentActivity.class);

                        if(isReturn2) {
                            intent.putIntegerArrayListExtra("selectedSeatsReturn", selectedSeatsReturn);    //DÖNÜŞ
                            intent.putExtra("ReturnTripId", returntripId);                      //DÖNÜŞ
                            intent.putExtra("selectSeatTwo", selectSeatTwo);                    //DÖNÜŞ

                            intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);  // GİDİŞ
                            intent.putExtra("TripId", tripId);                               //GİDİŞ
                            intent.putExtra("selectSeatOne", selectSeatOne1);                  //GİDİŞ

                            intent.putExtra("isReturn", isReturn2);
                            intent.putExtra("unregisteredUser", email1);
                        }
                        else{
                            intent.putIntegerArrayListExtra("selectedSeats", selectedSeats);  // GİDİŞ
                            intent.putExtra("TripId", tripId);                               //GİDİŞ
                            intent.putExtra("selectSeatOne", selectSeatOne);                  //GİDİŞ

                            intent.putExtra("isReturn", isReturn2);
                            intent.putExtra("unregisteredUser", email1);
                        }


                        startActivity(intent);
                    } else {
                        Toast.makeText(UnregisteredUserInfo.this, "Something is wrong check values and try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}