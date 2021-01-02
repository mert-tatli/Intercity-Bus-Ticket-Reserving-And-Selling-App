package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity  {
    Button buttonSign;
    EditText id,name,surname,phone,birthday,email,password;
    RadioButton male,female;
    CheckBox terms;
    String gender;
    UserModel userModel;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent =getIntent();
        id=(EditText)findViewById(R.id.idNumber);
        name=(EditText)findViewById(R.id.inputName);
        surname=(EditText)findViewById(R.id.inputSurname);
        phone=(EditText)findViewById(R.id.inputPhoneNo);
        birthday=(EditText)findViewById(R.id.inputDate);
        email=(EditText)findViewById(R.id.inputEmail);
        password=(EditText)findViewById(R.id.inputPassword);
        male=findViewById(R.id.MaleRadioButton);
        female=findViewById(R.id.FemaleRadioButton);
        terms=findViewById(R.id.inputTerms);
        userModel=new UserModel();
        buttonSign=findViewById(R.id.buttonSignUp);
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }
    public void backbutton1(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void onClickSignup(View view){

        if (terms.isChecked()){
            String id1=id.getText().toString();
            String name1=name.getText().toString();
            String surname1=surname.getText().toString();
            String phone1= phone.getText().toString();
            String birthday1=birthday.getText().toString();
            String email1=email.getText().toString();
            String password1=password.getText().toString();
            if (TextUtils.isEmpty(id1) || TextUtils.isEmpty(name1) || TextUtils.isEmpty(surname1)|| TextUtils.isEmpty(phone1)|| TextUtils.isEmpty(birthday1)|| TextUtils.isEmpty(email1)|| TextUtils.isEmpty(password1))
            {
                Toast.makeText(RegisterActivity.this,"All the Information Are Required",Toast.LENGTH_SHORT).show();
            }
            else{
                System.out.println(gender);
                User usr=new User(id1,name1,surname1,gender,phone1,birthday1,email1,password1);
                userModel.setUsers(usr);
                mAuth.createUserWithEmailAndPassword(email1,password1);
                Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();

                mDatabase.child("users").child(id1).child("id").setValue(id1);
                mDatabase.child("users").child(id1).child("name").setValue(name1);
                mDatabase.child("users").child(id1).child("surname").setValue(surname1);
                mDatabase.child("users").child(id1).child("gender").setValue(gender);
                mDatabase.child("users").child(id1).child("phone").setValue(phone1);
                mDatabase.child("users").child(id1).child("birthday").setValue(birthday1);
                mDatabase.child("users").child(id1).child("email").setValue(email1);

                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(RegisterActivity.this,"Please Accept the Terms And Conditions",Toast.LENGTH_SHORT).show();
        }
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.MaleRadioButton:
                if (checked)
                    gender="Male";
                break;
            case R.id.FemaleRadioButton:
                if (checked)
                    gender="Female";
                break;
        }
    }



}