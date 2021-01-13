package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Observable;
import java.util.Observer;

public class LoginActivity extends AppCompatActivity {
    private EditText InputId;
    private EditText passwordLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputId = findViewById(R.id.inputId);
        passwordLogin = findViewById(R.id.inputPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginButton(View view) {
        String id = InputId.getText().toString();
        String password = passwordLogin.getText().toString();
        if(id.equals("admin")&&password.equals("123456")){
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }
        else{

            mAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else if(TextUtils.isEmpty(id) || TextUtils.isEmpty(password) || password.length()<6){
                        Toast.makeText(LoginActivity.this, "All the Information Are Required and CHECK the password length", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        Toast.makeText(LoginActivity.this, "Kullanıcı sistemde kayıtlı değil.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    public void backbutton(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void forgetMyPassword(View view){


        Intent intent = new Intent(LoginActivity.this, ForgetMyPasswordActivity.class);
        startActivity(intent);
        finish();

        /*mAuth.sendPasswordResetEmail("simdilik bos ama denedik calısıyor.").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d("Success","Email sent.");
                    Toast.makeText(LoginActivity.this, "Password reset email has been sent", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "You cant send multiple password reset emails.", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }



}