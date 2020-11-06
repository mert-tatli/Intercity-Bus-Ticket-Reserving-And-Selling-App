package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent =getIntent();


    }

    public void backbutton1(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

        startActivity(intent);
    }
}