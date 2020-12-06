package com.example.intercitybusticketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;


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
    private RadioGroup radioGroup;
    private RadioButton radioTrip;
    String[] arraySpinner = new String[]{"Select the City","Adana","Adiyaman","Afyon","Agri","Aksaray","Amasya","Ankara","Antalya","Ardahan","Artvin","Aydin","Balikesir","Bartin","Batman","Bayburt","Bilecik","Bingol","Bitlis","Bolu","Burdur","Bursa","Canakkale","Cankiri","Corum","Denizli","Diyarbakir","Duzce","Edirne","Elazig","Erzincan","Erzurum","Eskisehir","Gaziantep","Giresun","Gumushane","Hakkari","Hatay","Igdir","Isparta","Istanbul","Izmir","Kahramanmaras",
           "Karabuk","Karaman","Kars","Kastamonu","Kayseri","Kilis","Kirikkale","Kirklareli","Kirsehir","Kocaeli","Konya","Kutahya","Malatya","Manisa","Mardin","Mersin","Mugla","Mus","Nevsehir","Nigde","Ordu","Osmaniye","Rize","Sakarya","Samsun","Sanliurfa","Siirt","Sinop","Sirnak","Sivas","Tekirdag","Tokat","Trabzon","Tunceli","Usak","Van","Yalova","Yozgat","Zonguldak"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        radioGroup=(RadioGroup) findViewById(R.id.radioGroupTrip);

        Spinner s1 = (Spinner) findViewById(R.id.spinner);
        Spinner s2 = (Spinner) findViewById(R.id.spinner2);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), arraySpinner[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), arraySpinner[position], Toast.LENGTH_SHORT).show();
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

       ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
    public void onClickOneWayRadio(View view){
            returnDate.setVisibility(View.INVISIBLE);
    }
    public void onClickRoundRadio(View view){
        returnDate.setVisibility(View.VISIBLE);
    }

}
