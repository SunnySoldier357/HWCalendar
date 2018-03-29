package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddEventActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Spinner monthSpinner = (Spinner) findViewById(R.id.month);
        ArrayAdapter<String> myAdapter0 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.monthArray));
        myAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(myAdapter0);

        Spinner daySpinner = (Spinner) findViewById(R.id.day);
        ArrayAdapter<String> myAdapter9 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dayArray));
        myAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(myAdapter9);

        Spinner yearSpinner = (Spinner) findViewById(R.id.year);
        ArrayAdapter<String> myAdapter10 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yearArray));
        myAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(myAdapter10);

        Spinner startSpinner = (Spinner) findViewById(R.id.startTimeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timeArray));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(myAdapter);

        Spinner amPmStartSpinner = (Spinner) findViewById(R.id.amPmStart);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.amPmArray));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmStartSpinner.setAdapter(myAdapter2);

        Spinner endSpinner = (Spinner) findViewById(R.id.endTimeSpinner);
        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timeArray));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(myAdapter3);

        Spinner amPmEndSpinner = (Spinner) findViewById(R.id.amPmEnd);
        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.amPmArray));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmEndSpinner.setAdapter(myAdapter4);

        //todo: gather the info from spinners

    }

    // Event Handlers
    public void addEventButton_Clicked(View view)
    {
        String date = GetDate();
        String startTime = GetStartTime();
        String endTime = GetEndTime();
        String eventName = GetEventName();

        Intent intentHome = new Intent(this, MainActivity.class);
        /*
        intentHome.putExtra("date", GetDate());
        intentHome.putExtra("endTime", GetEndTime());
        intentHome.putExtra("eventName", GetEventName());
        intentHome.putExtra("startTime", GetStartTime());
        */
        startActivity(intentHome);
    }




    // Accessors (gets the entered edit text data)
    public String GetDate()
    {
        return ((EditText) findViewById(R.id.date)).getText().toString();
    }

    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }

    public String GetStartTime()
    {
        return ((EditText) findViewById(R.id.startTime)).getText().toString();
    }
    public String GetEndTime()
    {
        return ((EditText) findViewById(R.id.endTime)).getText().toString();
    }
}