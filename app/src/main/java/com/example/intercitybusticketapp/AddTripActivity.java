package com.example.intercitybusticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {
    public static final String DATE_DIALOG_1 = "datePicker1";
    @SuppressLint("StaticFieldLeak")
    private static Button departureDate;
    private EditText tripid;
    private Spinner to,from,fromTime,toTime,busplate;
    private EditText price;
    private TripModel tripmodel;
    private DatabaseReference mDatabase,mBuses;
    private String fromtrip,toTrip,fromtimetrip,totimetrip,departdate,selectedBus;
    private static int mYear1;
    private static int mMonth1;
    private static int mDay1;
    private ArrayList<String> availableBusPlates;
    //sadece array oluşturup busları içine atarsınız aşşağıda busplate diye spinner tanımladım seçildiğinde selectedBus'a string olarak atacak.

    private String[] arraySpinner = new String[]{"Select Departure City", "Adana", "Adiyaman", "Afyon", "Agri", "Aksaray", "Amasya", "Ankara", "Antalya", "Ardahan", "Artvin", "Aydin", "Balikesir", "Bartin", "Batman", "Bayburt", "Bilecik", "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa", "Canakkale", "Cankiri", "Corum", "Denizli", "Diyarbakir", "Duzce", "Edirne", "Elazig", "Erzincan", "Erzurum", "Eskisehir", "Gaziantep", "Giresun", "Gumushane", "Hakkari", "Hatay", "Igdir", "Isparta", "Istanbul", "Izmir", "Kahramanmaras",
            "Karabuk", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kilis", "Kirikkale", "Kirklareli", "Kirsehir", "Kocaeli", "Konya", "Kutahya", "Malatya", "Manisa", "Mardin", "Mersin", "Mugla", "Mus", "Nevsehir", "Nigde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", "Sanliurfa", "Siirt", "Sinop", "Sirnak", "Sivas", "Tekirdag", "Tokat", "Trabzon", "Tunceli", "Usak", "Van", "Yalova", "Yozgat", "Zonguldak"};
    private String[] arraySpinner2 = new String[]{"Select Arrival City", "Adana", "Adiyaman", "Afyon", "Agri", "Aksaray", "Amasya", "Ankara", "Antalya", "Ardahan", "Artvin", "Aydin", "Balikesir", "Bartin", "Batman", "Bayburt", "Bilecik", "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa", "Canakkale", "Cankiri", "Corum", "Denizli", "Diyarbakir", "Duzce", "Edirne", "Elazig", "Erzincan", "Erzurum", "Eskisehir", "Gaziantep", "Giresun", "Gumushane", "Hakkari", "Hatay", "Igdir", "Isparta", "Istanbul", "Izmir", "Kahramanmaras",
            "Karabuk", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kilis", "Kirikkale", "Kirklareli", "Kirsehir", "Kocaeli", "Konya", "Kutahya", "Malatya", "Manisa", "Mardin", "Mersin", "Mugla", "Mus", "Nevsehir", "Nigde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", "Sanliurfa", "Siirt", "Sinop", "Sirnak", "Sivas", "Tekirdag", "Tokat", "Trabzon", "Tunceli", "Usak", "Van", "Yalova", "Yozgat", "Zonguldak"};


    private String[] timeSpinner = new String[]{"Select Departure time", "00:00", "00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45", "02:00", "02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45", "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15",
            "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15",
            "20:30", "20:45", "21:00", "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45",
            "23:00", "23:15", "23:30", "23:45"};

    private String[] timeSpinner2 = new String[]{"Select Arrival time", "00:00", "00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45", "02:00", "02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45", "04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45", "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15",
            "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15",
            "20:30", "20:45", "21:00", "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45",
            "23:00", "23:15", "23:30", "23:45"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        tripid=findViewById(R.id.inputTripId);
        from = findViewById(R.id.inputTripFrom);
        to=findViewById(R.id.inputTripTo);
        fromTime = findViewById(R.id.inputTripFromTime);
        toTime=findViewById(R.id.inputTripToTime);
        price=findViewById(R.id.inputTripPrice);
        busplate=findViewById(R.id.inputBusPlate2);
        departureDate = findViewById(R.id.departureDateButton2);


        tripmodel=new TripModel();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        availableBusPlates= new ArrayList<>();
        availableBusPlates.add("Select The Bus");

        mBuses=FirebaseDatabase.getInstance().getReference("Buses");
        mBuses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot buses : snapshot.getChildren()){
                        if(!buses.hasChild("Trip")){
                            availableBusPlates.add(buses.getKey());
                        }
                    }

                    busplate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                            selectedBus = availableBusPlates.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });


                    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(AddTripActivity.this,
                                android.R.layout.simple_spinner_item, availableBusPlates);
                        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        busplate.setAdapter(adapter5);


                }
                else{
                    Toast.makeText(AddTripActivity.this,"Buses Cannot Found.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddTripActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                Toast.makeText(AddTripActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddTripActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });



        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                fromtrip = arraySpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                toTrip = arraySpinner2[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from.setAdapter(adapter);
        to.setAdapter(adapter3);

        fromTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                fromtimetrip = timeSpinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        toTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                totimetrip = timeSpinner2[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, timeSpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, timeSpinner2);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromTime.setAdapter(adapter2);
        toTime.setAdapter(adapter4);
        departureDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });
    }
    public void addtrip(View view){
        String tripid1 =tripid.getText().toString();
        String from1=fromtrip;
        String to1=toTrip;
        String fromtimetrip1=fromtimetrip;
        String totimetrip1=totimetrip;
        departdate = departureDate.getText().toString();

        String price1=price.getText().toString();
        if (selectedBus.equals("Select The Bus")){
            Toast.makeText(AddTripActivity.this,"You Have to Select The Bus",Toast.LENGTH_SHORT).show();
        }
        else{
            if (TextUtils.isEmpty(tripid1) ||  TextUtils.isEmpty(from1) || TextUtils.isEmpty(to1)|| TextUtils.isEmpty(fromtimetrip1) || TextUtils.isEmpty(totimetrip1)|| TextUtils.isEmpty(price1) )
            {
                Toast.makeText(AddTripActivity.this,"All the Information Are Required,PLEASE CHECK",Toast.LENGTH_SHORT).show();
            }
            else{
                mDatabase.child("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.hasChild(tripid1)){
                                Toast.makeText(AddTripActivity.this,"The trip is already Created",Toast.LENGTH_SHORT).show();
                            }else{

                                ////////////////////////////
                                busplate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                        selectedBus = availableBusPlates.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });
                                ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(AddTripActivity.this,
                                        android.R.layout.simple_spinner_item, availableBusPlates);
                                adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                busplate.setAdapter(adapter6);

                                /////////////////////////////


                                if (availableBusPlates.size()==1){
                                    availableBusPlates.remove(selectedBus);


                                }
                                else{
                                    availableBusPlates.remove(selectedBus);
                                }


                                Trip t = new Trip(tripid1,from1,to1,fromtimetrip1,totimetrip1,departdate,price1);
                                tripmodel.setTrip(t);
                                Toast.makeText(AddTripActivity.this,"TRIP WAS CREATED SUCCESFULLY",Toast.LENGTH_SHORT).show();
                                mDatabase.child("Trips").child(tripid1).child("tripid").setValue(tripid1);
                                mDatabase.child("Trips").child(tripid1).child("from").setValue(from1);
                                mDatabase.child("Trips").child(tripid1).child("to").setValue(to1);
                                mDatabase.child("Trips").child(tripid1).child("departuretime").setValue(fromtimetrip1);
                                mDatabase.child("Trips").child(tripid1).child("arrivaltime").setValue(totimetrip1);
                                mDatabase.child("Trips").child(tripid1).child("date").setValue(departdate);
                                mDatabase.child("Trips").child(tripid1).child("price").setValue(price1);
                                mDatabase.child("Trips").child(tripid1).child("busPlate").setValue(selectedBus);
                                mDatabase.child("Buses").child(selectedBus).child("Trip").setValue(tripid1);
                                mDatabase.child("Trips").child(tripid1).child("TripSeats").child("Seat").setValue("/AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/"+ "AA___AA/" + "AA___AA/"+ "AA___AA/");

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddTripActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        Toast.makeText(AddTripActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddTripActivity.this,AdminActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

    }

    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @NonNull
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

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mYear1 = year;
            mMonth1 = month;
            mDay1 = day;
            departureDate.setText(new StringBuilder()
                    .append(mDay1).append("/")
                    .append(mMonth1 + 1).append("/")
                    .append(mYear1).append(""));
        }

    }




}