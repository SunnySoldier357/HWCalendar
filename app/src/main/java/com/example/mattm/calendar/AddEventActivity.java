package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity
{
    Spinner monthSpinner;
    Spinner daySpinner;
    Spinner yearSpinner;
    Spinner startSpinner;
    Spinner amPmStartSpinner;
    Spinner endSpinner;
    Spinner amPmEndSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        setUpSpinners();
    }


    // Event Handlers
    public void addEventButton_Clicked(View view)
    {
        String date = GetDate();
        String startTime = GetStartTime();
        String startAmPm = GetStartAmPm();
        String endTime = GetEndTime();
        String endAmPm = GetEndAmPm();
        String eventName = GetEventName();

        Intent intentHome = new Intent(this, MainActivity.class);
        /*
        intentHome.putExtra("date", date);
        intentHome.putExtra("endTime", GetEndTime());
        intentHome.putExtra("eventName", GetEventName());
        intentHome.putExtra("startTime", GetStartTime());
        */
        startActivity(intentHome);
    }


    // Accessors (gets the entered edit text data)
    public String GetDate()
    {
        return monthSpinner.getSelectedItem().toString();
    }

    public String GetEventName()
    {
        return monthSpinner.getSelectedItem().toString();
    }

    public String GetStartTime()
    {
        return monthSpinner.getSelectedItem().toString();
    }

    public String GetStartAmPm()
    {
        return amPmStartSpinner.getSelectedItem().toString();
    }

    public String GetEndTime()
    {
        return monthSpinner.getSelectedItem().toString();
    }

    public String GetEndAmPm()
    {
        return amPmEndSpinner.getSelectedItem().toString();
    }


    //setup (onCreate)
    public void setUpSpinners() {
        monthSpinner = findViewById(R.id.month);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.monthArray));
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        daySpinner = findViewById(R.id.day);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dayArray));
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        yearSpinner = findViewById(R.id.year);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yearArray));
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        startSpinner = findViewById(R.id.startTimeSpinner);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timeArray));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(timeAdapter);

        amPmStartSpinner = findViewById(R.id.amPmStart);
        ArrayAdapter<String> amPmAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.amPmArray));
        amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmStartSpinner.setAdapter(amPmAdapter);

        endSpinner = findViewById(R.id.endTimeSpinner);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(timeAdapter);

        amPmEndSpinner = findViewById(R.id.amPmEnd);
        amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmEndSpinner.setAdapter(amPmAdapter);
    }

}