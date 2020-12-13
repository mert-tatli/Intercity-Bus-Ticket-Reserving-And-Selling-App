package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TripActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textview;
    ImageView imageView;
    ViewGroup layout;
    int tripGaping=10;
    int count=0;
    ArrayList<String> arr =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        layout = findViewById(R.id.layoutTrip);
        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * tripGaping, 16 * tripGaping, 8 * tripGaping, 16 * tripGaping);
        layout.addView(layoutSeat);
        LinearLayout layout = null;
        for (int index = 0; index < 5; index++) {

            count++;
            layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layoutSeat.addView(layout);
            CardView view = new CardView(this);
            Drawable myDrawable = getResources().getDrawable(R.drawable.home_gradient_maths);
            view.setBackground(myDrawable);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 30, 0, 30);
            view.setLayoutParams(layoutParams);
            view.setPadding(0, 20, 0, 20);
            view.setId(count);
            view.setCardElevation(10);
            view.setRadius(15);
            view.setOnClickListener(this);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams1 = new LinearLayout.LayoutParams(300, 200);
            textview.setLayoutParams(textParams1);
            textview.setText("18:30");
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.BLACK);
            textview.setPadding(5,5,5,5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams2 = new LinearLayout.LayoutParams(300, 350);
            textview.setLayoutParams(textParams2);
            textview.setText("22:30");
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.BLACK);
            textview.setPadding(5,5,5,5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams3 = new LinearLayout.LayoutParams(900, 200);
            textview.setLayoutParams(textParams3);
            textview.setText("Istanbul");
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.BLACK);
            textview.setPadding(5,5,5,5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams4 = new LinearLayout.LayoutParams(900, 350);
            textview.setLayoutParams(textParams4);
            textview.setText("Adana");
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.BLACK);
            textview.setPadding(5,5,5,5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams5 = new LinearLayout.LayoutParams(650, 680);
            textview.setLayoutParams(textParams5);
            textview.setText("Travel Time: "+" 5 Hours");
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.BLACK);
            textview.setPadding(5,5,5,5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);

            imageView=new ImageView(this);
            LinearLayout.LayoutParams imgParams1 = new LinearLayout.LayoutParams(550, 220);
            imageView.setLayoutParams(imgParams1);
            Drawable myDrawable2 = getResources().getDrawable(R.drawable.ic_three_dots);
            imgParams1.setMargins(0, 30, 0, 30);
            imageView.setPadding(0,50,0,50);
            imageView.setImageDrawable(myDrawable2);
            view.addView(imageView);

            imageView=new ImageView(this);
            LinearLayout.LayoutParams imgParams2 = new LinearLayout.LayoutParams(1500, 250);
            imageView.setLayoutParams(imgParams2);
            Drawable myDrawable3 = getResources().getDrawable(R.drawable.ic_bus_256);
            imageView.setImageDrawable(myDrawable3);
            view.addView(imageView);

            textview = new TextView(this);
            LinearLayout.LayoutParams textParams6 = new LinearLayout.LayoutParams(400, 150);
            textview.setLayoutParams(textParams6);
            textview.setText("$20");
            Drawable myDrawable4 = getResources().getDrawable(R.drawable.ic_button_round);
            textview.setBackground(myDrawable4);
            textParams6.setMargins(800, 470, 800, 0);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textview.setTextColor(Color.WHITE);
            textview.setPadding(5,5,5,5);
            textview.setGravity(Gravity.CENTER);
            view.addView(textview);


            layout.addView(view);
            view.setOnClickListener(this);
        }


    }
    @Override
    public void onClick(View view) {
        int in=view.getId();
        Toast.makeText(this, "Trip " + in + " is Selected", Toast.LENGTH_SHORT).show();
        Intent intent =new Intent(this,SelectSeatActivity.class);
        intent.putExtra("id",in);
        startActivity(intent);


    }




}