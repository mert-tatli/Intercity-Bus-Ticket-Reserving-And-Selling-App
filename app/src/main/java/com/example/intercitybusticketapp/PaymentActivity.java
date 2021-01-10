package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PaymentActivity extends AppCompatActivity{

    private TextView price;
    private EditText holderName,cardNumber,month,year,cvv;
    private ListView seatInfo;
    private int tripGaping = 10;
    private int count = 0;
    boolean isReturn2;
    String tripId,returntripId;
    ArrayList<Integer> selectedSeatsReturn = new ArrayList<>();
    ArrayList<Integer> selectedSeats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        seatInfo=findViewById(R.id.seatInfo);
        holderName=findViewById(R.id.holderName);
        cardNumber=findViewById(R.id.cardNumber);
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        cvv=findViewById(R.id.cvv);
        price=findViewById(R.id.textPrice);

        Intent intent=getIntent();
        selectedSeats=intent.getIntegerArrayListExtra("selectedSeats");
        selectedSeatsReturn=intent.getIntegerArrayListExtra("selectedSeatsReturn");
        returntripId=intent.getStringExtra("ReturnTripId");
        tripId=intent.getStringExtra("TripId");
        isReturn2=intent.getBooleanExtra("isReturn",false);

        //fdfs

        selectedSeats.addAll(selectedSeatsReturn);
        ArrayAdapter<Integer> adapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, selectedSeats);
        seatInfo.setAdapter(adapter);
        price.setText("Price: "  );//burada ayarlanacak...

    }


}