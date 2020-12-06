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

public class RegisterActivity extends AppCompatActivity  {
    Button buttonSign;
    EditText id,name,surname,phone,birthday,email,password;
    RadioButton male,female;
    CheckBox terms;
    String gender;
    UserModel userModel;

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
                User usr=new User(id1,name1,surname1,gender,phone1,birthday1,email1,password1);
                userModel.setUsers(usr);
                Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();

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