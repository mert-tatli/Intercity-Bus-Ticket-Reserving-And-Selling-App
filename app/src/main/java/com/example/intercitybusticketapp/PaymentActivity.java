package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;


public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textview;
    private ImageView imageView;
    private ScrollView layout;
    private int tripGaping = 10;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        layout = (ScrollView) findViewById(R.id.layoutView);
        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(4 * tripGaping, 4 * tripGaping, 4 * tripGaping, 4 * tripGaping);

        textview = new TextView(this);
        LinearLayout.LayoutParams textParams0 = new LinearLayout.LayoutParams(300, 200);
        textview.setLayoutParams(textParams0);
        textview.setText("Outbound Trips");
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(5, 5, 5, 5);
        textview.setGravity(Gravity.CENTER);
        textview.setOnClickListener(this);
        layoutSeat.addView(textview);
        layout.addView(layoutSeat);

    }

    @Override
    public void onClick(View v) {

    }
}