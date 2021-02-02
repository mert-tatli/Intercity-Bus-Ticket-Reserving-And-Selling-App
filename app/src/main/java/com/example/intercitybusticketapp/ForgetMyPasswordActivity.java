package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetMyPasswordActivity extends AppCompatActivity {
    private EditText eMail;
    private FirebaseAuth mAuth;
    private Button sendPassEmailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_my_password);
        eMail = findViewById(R.id.forgetPassEmail);
        mAuth = FirebaseAuth.getInstance();
        sendPassEmailButton = findViewById(R.id.sendResetEmailButton);
        sendPassEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validateEmail(eMail)) {
                   mAuth.sendPasswordResetEmail(eMail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               Log.d("Success", "Email sent.");
                               Toast.makeText(ForgetMyPasswordActivity.this, "Password reset email has been sent", Toast.LENGTH_LONG).show();
                           } else {
                               Toast.makeText(ForgetMyPasswordActivity.this, "You cant send multiple password reset emails.", Toast.LENGTH_LONG).show();
                           }
                       }
                   });
               }
            }
            }
        );
    }

    private boolean validateEmail(EditText email){
        String emailinput = eMail.getText().toString();

        if(!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){

            return true;
        }else{
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}