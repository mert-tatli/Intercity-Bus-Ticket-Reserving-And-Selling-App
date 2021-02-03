package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentReservedActivity extends AppCompatActivity {
    private EditText holderName, cardNumber, month, year, cvv;
    private TextView price;
    private String ticket_id,price1;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_reserved);
        holderName = findViewById(R.id.holderName3);
        cardNumber = findViewById(R.id.cardNumber3);
        month = findViewById(R.id.month3);
        year = findViewById(R.id.year3);
        cvv = findViewById(R.id.cvv3);
        price = findViewById(R.id.textPrice3);
        Intent intent=getIntent();
        ticket_id=intent.getStringExtra("ticket_id");
        price1=intent.getStringExtra("price");
        price.setText("Price: "+ price1 + "₺");
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }
    public void onBuyClick(View view) {
        String holderName1 = holderName.getText().toString();
        String cardNumber1 = cardNumber.getText().toString();
        String month1 = month.getText().toString();
        String year1 = year.getText().toString();
        String cvv1 = cvv.getText().toString();
        int y=Integer.valueOf(year1);
        int m=Integer.valueOf(month1);
        if (TextUtils.isEmpty(holderName1) || TextUtils.isEmpty(cardNumber1) || TextUtils.isEmpty(month1)||TextUtils.isEmpty(year1) || TextUtils.isEmpty(cvv1)
                || y<21  || m>12 || cvv1.length()<3 || m==0 || y==0){
            Toast.makeText(PaymentReservedActivity.this, "Check your information", Toast.LENGTH_LONG).show();
        }
        else
        {
            mDatabase.child("Ticket").child(ticket_id).child("isReserved").setValue(false);
            Toast.makeText(PaymentReservedActivity.this, "The Ticket is paid", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),MyTicketsActivity.class);
            startActivity(intent);
            finish();
        }


    }
}