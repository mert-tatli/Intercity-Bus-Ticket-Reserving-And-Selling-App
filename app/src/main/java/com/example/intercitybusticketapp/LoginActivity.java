package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity implements Observer {
        EditText InputId;
        EditText passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputId = findViewById(R.id.inputId);
        passwordLogin = findViewById(R.id.inputPassword);
    }
    public void signUp(View view){

        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);

        startActivity(intent);
    }
    public  void loginButton(View view){
      //  Intent intent = new Intent(LoginActivity.this,.......);

      //  startActivity(intent);

    }

    public void backbutton(View view){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);

        startActivity(intent);
    }
    @Override
    public void update(Observable o, Object arg) {

    }



}