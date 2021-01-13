package com.example.intercitybusticketapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordChangeActivity extends AppCompatActivity {
    private EditText newPass;
    private FirebaseAuth mAuth;
    private Button changeMyPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        newPass = findViewById(R.id.editTextNewPass);
        changeMyPass = findViewById(R.id.passwordChangeButton);
        mAuth = FirebaseAuth.getInstance();
        changeMyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPass.getText().toString();
                mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordChangeActivity.this, "Your password has been changed successfully.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PasswordChangeActivity.this, UserAccountActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(PasswordChangeActivity.this, "Invalid password Type!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}