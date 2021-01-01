package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity implements Observer {
        EditText InputId;
        EditText passwordLogin;
        FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputId = findViewById(R.id.inputId);
        passwordLogin = findViewById(R.id.inputPassword);
        mAuth=FirebaseAuth.getInstance();
    }
    public void signUp(View view){

        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
    public  void loginButton(View view){
        String id = InputId.getText().toString();
        String password = passwordLogin.getText().toString();
        mAuth.signInWithEmailAndPassword(id,password);//id ile giriş değiştirilip mail ile giriş olarak düzeltilecek
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void backbutton(View view){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void update(Observable o, Object arg) {

    }



}