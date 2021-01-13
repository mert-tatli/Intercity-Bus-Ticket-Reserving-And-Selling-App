package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserAccountActivity extends AppCompatActivity {
    private ImageView back;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        back=findViewById(R.id.backToMain);
        mAuth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserAccountActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void logOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(UserAccountActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void changeMyPassword(View view){
       Intent intent = new Intent(UserAccountActivity.this,PasswordChangeActivity.class);
       startActivity(intent);
    }
}