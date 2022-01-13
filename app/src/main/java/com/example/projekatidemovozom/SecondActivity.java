package com.example.projekatidemovozom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projekatidemovozom.models.TimestampModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.projekatidemovozom.Keys.*;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private int hours;
    private int minutes;
    private String cityOd;
    private String cityDo;
    public List<TimestampModel> timestampModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_second);
        initComponents();
        initDatabaseConnection();
    }

    @SuppressLint("SetTextI18n")
    public void initComponents() {
        Bundle extras = getIntent().getExtras();
        cityOd = extras.getString(INPUT_OD);
        cityDo = extras.getString(INPUT_DO);
        hours = extras.getInt(HOURS);
        minutes = extras.getInt(MINS);
        int day = extras.getInt(DAY);
        int month = extras.getInt(MONTH);
        ImageView iconBack = findViewById(R.id.iconBack);
        TextView labelFrom = findViewById(R.id.labelFrom);
        TextView labelTo = findViewById(R.id.labelTo);

        TextView labelWhenDeparture = findViewById(R.id.labelWhenDeparture);
        TextView labelDepartureDate = findViewById(R.id.labelDepartureDate);
        labelWhenDeparture.setText("POLAZAK " + hours + ":" + minutes);
        labelDepartureDate.setText(day + " "  + getMonth(month));
        labelFrom.setText(cityOd);
        labelTo.setText(cityDo);
        iconBack.setOnClickListener(this);

        minutes = minutes + (hours * 60); // Minutes since midnight
    }

    public void initDatabaseConnection() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference timestampsRef = db.collection("timestamps");
        timestampsRef.whereEqualTo("route.cityFrom", cityOd)
                .whereEqualTo("route.cityTo", cityDo)
                .whereLessThanOrEqualTo("departure", minutes)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TimestampModel tm = document.toObject(TimestampModel.class);
                            timestampModelList.add(tm);
                            Log.d(TAG, tm.toString());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iconBack:
                Intent redirect = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(redirect);
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
        }
    }

    public String getMonth(int month){
        String monthString;
        switch (month) {
            case 1:  monthString = "Jan";
                break;
            case 2:  monthString = "Feb";
                break;
            case 3:  monthString = "Mar";
                break;
            case 4:  monthString = "Apr";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "Jun";
                break;
            case 7:  monthString = "Jul";
                break;
            case 8:  monthString = "Aug";
                break;
            case 9:  monthString = "Sep";
                break;
            case 10: monthString = "Oct";
                break;
            case 11: monthString = "Nov";
                break;
            case 12: monthString = "Dec";
                break;
            default: monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}