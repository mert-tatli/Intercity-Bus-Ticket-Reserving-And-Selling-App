package com.example.intercitybusticketapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String DATE_DIALOG_1 = "datePicker1";
    static Button departureDate;
    private static int mYear1;
    private static int mMonth1;
    private static int mDay1;

    public static final String DATE_DIALOG_2 = "datePicker2";
    static Button returnDate;
    private static int mYear2;
    private static int mMonth2;
    private static int mDay2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        departureDate = findViewById(R.id.departureDateButton);
        departureDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });

        returnDate = findViewById(R.id.returnDateButton);
        returnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DialogFragment newFragment2 = new DatePickerFragment2();
                newFragment2.show(getSupportFragmentManager(), DATE_DIALOG_2);
            }
        });
    }

    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd=new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMinDate(c.getTimeInMillis());
            c.add(Calendar.DATE, 14);
            dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dpd;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            mYear1 = year;
            mMonth1 = month;
            mDay1 = day;
            returnDate.setEnabled(true);
            returnDate.setText("SELECT RETURN DATE");

            departureDate.setText(new StringBuilder()
                    .append(mDay1).append("/")
                    .append(mMonth1 + 1).append("/")
                    .append(mYear1).append(" "));
        }

    }

    public static class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd=new DatePickerDialog(getActivity(), this, year, month, day);
            Calendar c2 = Calendar.getInstance();
            c2.set(mYear1, mMonth1, mDay1);
            dpd.getDatePicker().setMinDate(c2.getTimeInMillis());
            c2.add(Calendar.DATE, 14);
            dpd.getDatePicker().setMaxDate(c2.getTimeInMillis());
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            mYear2 = year;
            mMonth2 = month;
            mDay2 = day;
            returnDate.setText(new StringBuilder()
                    .append(mDay2).append("/")
                    .append(mMonth2 + 1).append("/")
                    .append(mYear2).append(" "));

        }

    }

}
