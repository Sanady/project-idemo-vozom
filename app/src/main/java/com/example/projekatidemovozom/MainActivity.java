package com.example.projekatidemovozom;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.projekatidemovozom.models.CityModel;
import org.json.JSONArray;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.example.projekatidemovozom.AdditionalCities.*;
import static com.example.projekatidemovozom.Keys.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final Calendar rightNow = Calendar.getInstance();
    private AutoCompleteTextView inputOd;
    private AutoCompleteTextView inputDo;
    private String cityOd;
    private String cityDo;
    private String time;
    private int monthRedirect;
    private int dayRedirect;
    private Button buttonHours;
    private Button buttonDate;
    private int minutesSend = rightNow.get(Calendar.MINUTE);
    private int hoursSend = rightNow.get(Calendar.HOUR_OF_DAY);
    private final ArrayList<String> citiesList = new ArrayList<>();

    //TODO: Da se popravi applikacija, s tim da baza podataka radi
    //TODO: Fetching data from db to card
    //TODO: Cena
    //TODO: Redirect kada klient klikne na omiljeno da uzme cityOd i cityDo i trenutni

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }


    private void initComponents() {
        inputOd = findViewById(R.id.inputOd);
        inputDo = findViewById(R.id.inputDo);
        buttonHours = findViewById(R.id.buttonHours);
        buttonDate = findViewById(R.id.buttonDate);
        Button buttonFind = findViewById(R.id.buttonFind);
        ImageView imageSwitch = findViewById(R.id.imageSwitch);

        timeDateConfiguration();

        imageSwitch.setClickable(true);
        buttonFind.setOnClickListener(this);
        buttonHours.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
        imageSwitch.setOnClickListener(this);

        initAPIJSON();
        inputOd.setOnItemClickListener((parent, view, position, id) -> cityOd = (String)parent.getItemAtPosition(position));
        inputDo.setOnItemClickListener((parent, view, position, id) -> cityDo = (String)parent.getItemAtPosition(position));
    }

    private void timeDateConfiguration() {
        String datePattern = "dd.M.yyyy.";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat(datePattern);
        buttonDate.setText(simpleDateFormatDate.format(new Date()));

        String datePatternRedirect = "MM";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDateRedirect = new SimpleDateFormat(datePatternRedirect);
        monthRedirect = Integer.parseInt(simpleDateFormatDateRedirect.format(new Date()));

        String dayPatternRedirect = "dd";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDayRedirect = new SimpleDateFormat(dayPatternRedirect);
        dayRedirect = Integer.parseInt(simpleDateFormatDayRedirect.format(new Date()));

        time = hoursSend + ":" + convertDate(minutesSend);
        buttonHours.setText(time);
    }

    @SuppressLint("HandlerLeak")
    public void initAPIJSON() {
        Api.getJSON("https://simplemaps.com/static/data/country-cities/rs/rs.json", new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                String res = getJson();
                try {
                    JSONArray resArray = new JSONArray(res);
                    for(CityModel city : CityModel.parseJSONArray(resArray)) {
                        citiesList.add(city.getCity());
                    }
                    addAditionalCitiesAndStations();
                    final ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesList);
                    ArrayAdapter<String> adapterTo = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesList);
                    inputOd.setAdapter(adapterFrom);
                    inputOd.setThreshold(1);
                    inputDo.setAdapter(adapterTo);
                    inputDo.setThreshold(1);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    private void addAditionalCitiesAndStations() {
        citiesList.addAll(Arrays.asList(additionalCities));
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + input;
        }
    }



    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inputOd:
                inputOd.showDropDown();
                break;
            case R.id.inputDo:
                inputDo.showDropDown();
                break;
            case R.id.buttonFind:
                if(cityOd == null || cityDo == null) {
                    Toast.makeText(MainActivity.this, "Lokacija od ili do su prazne!",
                            Toast.LENGTH_LONG).show();
                }
                else if(cityOd.equals(cityDo)) {
                    Toast.makeText(MainActivity.this, "Lokacija od i do su iste!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent redirect = new Intent(MainActivity.this, SecondActivity.class);
                    redirect.putExtra(INPUT_OD, cityOd);
                    redirect.putExtra(INPUT_DO, cityDo);
                    redirect.putExtra(MONTH, monthRedirect);
                    redirect.putExtra(DAY, dayRedirect);
                    redirect.putExtra(HOURS, hoursSend);
                    redirect.putExtra(MINS, minutesSend);
                    startActivity(redirect);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
                break;
            case R.id.imageSwitch:
                if(cityOd == null || cityDo == null) {
                    Toast.makeText(MainActivity.this, "Lokacija od ili do su prazne!",
                            Toast.LENGTH_LONG).show();
                }
                else if(cityOd.equals(cityDo)) {
                    Toast.makeText(MainActivity.this, "Lokacija od i do su iste!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    inputOd.setText(citiesList.get(citiesList.indexOf(cityDo)));
                    inputDo.setText(citiesList.get(citiesList.indexOf(cityOd)));

                    String tmp = citiesList.get(citiesList.indexOf(cityOd));
                    cityOd = citiesList.get(citiesList.indexOf(cityDo));
                    cityDo = tmp;

                    inputOd.clearFocus();
                    inputDo.clearFocus();
                }
                break;

            case R.id.buttonHours:
                TimePickerDialog pickerTime;
                final Calendar calendarTime = Calendar.getInstance();
                int hour = calendarTime.get(Calendar.HOUR_OF_DAY);
                int minutes = calendarTime.get(Calendar.MINUTE);
                // time picker dialog
                pickerTime = new TimePickerDialog(MainActivity.this,
                        (tp, sHour, sMinute) -> {
                            buttonHours.setText(sHour + ":" + convertDate(sMinute));
                            time = (String)buttonHours.getText();
                            hoursSend = sHour;
                            minutesSend = sMinute;
                        }, hour, minutes, true);
                pickerTime.show();
                break;

            case R.id.buttonDate:
                DatePickerDialog pickerDate;
                final Calendar calendarDate = Calendar.getInstance();
                int day = calendarDate.get(Calendar.DAY_OF_MONTH);
                int month = calendarDate.get(Calendar.MONTH);
                int year = calendarDate.get(Calendar.YEAR);
                // date picker dialog
                pickerDate = new DatePickerDialog(MainActivity.this,
                        (view, year1, monthOfYear, dayOfMonth) -> {
                            monthOfYear = monthOfYear + 1;
                            buttonDate.setText(dayOfMonth + "." + monthOfYear + "." + year1 + ".");
                            monthRedirect = monthOfYear;
                            dayRedirect = dayOfMonth;
                        }, year, month, day);
                pickerDate.show();
                break;
        }
    }
}