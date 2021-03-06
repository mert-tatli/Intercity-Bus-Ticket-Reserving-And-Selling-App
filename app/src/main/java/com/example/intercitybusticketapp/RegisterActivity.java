package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;


public class RegisterActivity extends AppCompatActivity {
    private Button buttonSign;
    private EditText id, name, surname, phone, birthday, email, password;

    private RadioButton male, female;
    private CheckBox terms;
    private String gender;
    private UserModel userModel;
    private  FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        id = (EditText) findViewById(R.id.idNumber);
        name = (EditText) findViewById(R.id.inputName);
        surname = (EditText) findViewById(R.id.inputSurname);
        phone = (EditText) findViewById(R.id.inputPhoneNo);
        birthday = (EditText) findViewById(R.id.inputDate);
        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);
        male = findViewById(R.id.MaleRadioButton);
        female = findViewById(R.id.FemaleRadioButton);
        terms = findViewById(R.id.inputTerms);
        userModel = new UserModel();
        buttonSign = findViewById(R.id.buttonSignUp);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    public void backbutton1(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateEmail(EditText email){
        String emailinput = email.getText().toString();

        if(!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){

            return true;
        }else{
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onClickSignup(View view) {

        if (terms.isChecked()) {
            String id1 = id.getText().toString();
            String name1 = name.getText().toString();
            String surname1 = surname.getText().toString();
            String phone1 = phone.getText().toString();
            String birthday1 = birthday.getText().toString();
            String email1 = email.getText().toString();
            String password1 = password.getText().toString();

                if (!TextUtils.isEmpty(id1) && !TextUtils.isEmpty(name1) && !TextUtils.isEmpty(surname1) && !TextUtils.isEmpty(phone1) && !TextUtils.isEmpty(birthday1) && !TextUtils.isEmpty(email1) && !TextUtils.isEmpty(password1)) {
                    if (!(password1.length()<6))
                    {
                    if (validateEmail(email))
                    {
                        if(checkDate()){

                        User usr = new User(id1, name1, surname1, gender, phone1, birthday1, email1, password1);
                        mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_LONG).show();
                                mDatabase.child("users").child(id1).child("id").setValue(id1);
                                mDatabase.child("users").child(id1).child("name").setValue(name1);
                                mDatabase.child("users").child(id1).child("surname").setValue(surname1);
                                mDatabase.child("users").child(id1).child("gender").setValue(gender);
                                mDatabase.child("users").child(id1).child("phone").setValue(phone1);
                                mDatabase.child("users").child(id1).child("birthday").setValue(birthday1);
                                mDatabase.child("users").child(id1).child("email").setValue(email1);
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Verify your email address", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });


                }else {
                        Toast.makeText(RegisterActivity.this, "Check Your Birth Date.", Toast.LENGTH_SHORT).show();
                }
                }else {
                        Toast.makeText(RegisterActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    }
                    }else {
                        Toast.makeText(RegisterActivity.this, "Check Your Password", Toast.LENGTH_SHORT).show();
                    }


                } else{
                    Toast.makeText(RegisterActivity.this, "All the Information Are Required", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(RegisterActivity.this, "Please Accept the Terms And Conditions", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.MaleRadioButton:
                if (checked)
                    gender = "Male";
                break;
            case R.id.FemaleRadioButton:
                if (checked)
                    gender = "Female";
                break;
        }
    }
    public boolean checkDate(){
       String[] birth= birthday.getText().toString().split("/");
        int now=Calendar.getInstance().get(Calendar.YEAR);

        int month= Integer.parseInt(birth[0]);
        int day= Integer.parseInt(birth[1]);
        int year= Integer.parseInt(birth[2]);

        if (birth[2].length()==4  &&year<= now-18 && month>0 && month<13 && day>0 && day<=31){
                return true;
        }
        else{
            return false;
        }
    }


}