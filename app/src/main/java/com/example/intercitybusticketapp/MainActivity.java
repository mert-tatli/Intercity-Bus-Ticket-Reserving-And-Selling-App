package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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
    private boolean isReturn = false;
    private RadioButton round, oneway;
    private String option = "Round";
    private CheckBox reservation;
    private Spinner s1, s2;
    private boolean reserve = false;
    private String from, to;
    private String departdate, returndate;
    private DatabaseReference mDatabase;
    private DatabaseReference mTrips;
    private List<Trip> tripList;
    String[] arraySpinner = new String[]{"Select the City", "Adana", "Adiyaman", "Afyon", "Agri", "Aksaray", "Amasya", "Ankara", "Antalya", "Ardahan", "Artvin", "Aydin", "Balikesir", "Bartin", "Batman", "Bayburt", "Bilecik", "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa", "Canakkale", "Cankiri", "Corum", "Denizli", "Diyarbakir", "Duzce", "Edirne", "Elazig", "Erzincan", "Erzurum", "Eskisehir", "Gaziantep", "Giresun", "Gumushane", "Hakkari", "Hatay", "Igdir", "Isparta", "Istanbul", "Izmir", "Kahramanmaras",
            "Karabuk", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kilis", "Kirikkale", "Kirklareli", "Kirsehir", "Kocaeli", "Konya", "Kutahya", "Malatya", "Manisa", "Mardin", "Mersin", "Mugla", "Mus", "Nevsehir", "Nigde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", "Sanliurfa", "Siirt", "Sinop", "Sirnak", "Sivas", "Tekirdag", "Tokat", "Trabzon", "Tunceli", "Usak", "Van", "Yalova", "Yozgat", "Zonguldak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        tripList = new ArrayList<>();
        round = findViewById(R.id.roundRadioButton);
        oneway = findViewById(R.id.oneWayRadioButton);
        reservation = findViewById(R.id.reservation);

        s1 = findViewById(R.id.spinner);
        s2 =findViewById(R.id.spinner2);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                from = arraySpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                to = arraySpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
        s2.setAdapter(adapter);

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

    public void isReservation(View view) {
        if (reservation.isChecked()) {
            reserve = true;
        } else {
            reserve = false;
        }
    }
    public void signmain(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginmain(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void searchtrip(View view) {
        departdate = departureDate.getText().toString();
        departdate.replace(" ", "");
        returndate = returnDate.getText().toString();
        returndate.replace(" ", "");
        boolean check;
        if (option.equals("OneWay")) {
            isReturn = false;
            check=returndate.equals("");

        } else {
            isReturn = true;
            check=returndate.equals("RETURN DATE");
        }
        if (!from.equals(to) && !from.equals("Select the City") && !to.equals("Select the City") && !departdate.equals("DEPARTURE DATE") && !check) {


            reserve = reservation.isChecked();
            if (reserve == false) { // burda kullanıcı ise , && ile kontrol edilmeli ,, (Sorgu yaparken iki date demek iki trip demek)
                // dönüş için from --> to  , , , to--> from olucak   (2. trip yani)
                // burda veritabınında trip varmı diye kontrol edilip ona göre yönlendirilmesi lazım

                System.out.println("DENEME");
                System.out.println("From: "+ from);
                System.out.println("To: "+ to);
                System.out.println("departure Date: "+ departdate);
                System.out.println("Length of DepartDate :" + departdate.length());
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mTrips = FirebaseDatabase.getInstance().getReference("Trips");
                Query query1 = mTrips.orderByChild("from").equalTo(from);
                query1.addListenerForSingleValueEvent(valueEventListener);




                /*Intent intent = new Intent(MainActivity.this, TripActivity.class);
                intent.putExtra("isReturn",isReturn);
                startActivity(intent);
                finish();*/

            } else if (reserve == true) {  // ve kullanıcı değilse
                Toast.makeText(this, "To Make a Reservation Please First Login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {

                System.out.println("From: "+ from);
                System.out.println("To: "+ to);
                System.out.println("departure Date: "+ departdate);





                /*Intent intent = new Intent(MainActivity.this, TripActivity.class);
                intent.putExtra("isReturn", isReturn);
                startActivity(intent);
                finish();*/

            }

            // burda veritabınında trip varmı diye kontrol edilip ona göre yönlendirilmesi lazım

        } else {
            Toast.makeText(this, "All information is required -- You cannot select the same city", Toast.LENGTH_SHORT).show();
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            tripList.clear();
            if (datasnapshot.exists()) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    Trip trip = snapshot.getValue(Trip.class);
                    tripList.add(trip);
                }

            }
            for (int i = 0; i < tripList.size(); i++) {
                    System.out.println(tripList.get(i).toString());
            }
            System.out.println("***********************************************************");
            for (int i = 0; i < tripList.size(); i++) {
                if (!tripList.get(i).getTo().equals(to) || !tripList.get(i).getDate().equals(departdate)){
                    tripList.remove(i);
                    i--;
                }
            }
            System.out.println("***********************************************************");
            for (int i = 0; i < tripList.size(); i++) {
                System.out.println(tripList.get(i).toString());
            }
            if (tripList.isEmpty()) {
                System.out.println("Trip can not Found");
                Toast.makeText(MainActivity.this, "Trip can not found!!", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this, TripActivity.class);
                intent.putExtra("isReturn", isReturn);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
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
            returnDate.setText("RETURN DATE");

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

            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
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

    public void onClickOneWayRadio(View view) {
        option = "OneWay";
        returnDate.setVisibility(View.INVISIBLE);
        returnDate.setText("");
    }

    public void onClickRoundRadio(View view) {
        option = "Round";
        returnDate.setVisibility(View.VISIBLE);
        returnDate.setText("RETURN DATE");
    }


}
