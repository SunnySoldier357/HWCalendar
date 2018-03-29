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

        Spinner startSpinner = (Spinner) findViewById(R.id.startTimeSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timeArray));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(myAdapter);

        Spinner endSpinner = (Spinner) findViewById(R.id.endTimeSpinner);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timeArray));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(myAdapter2);


        //todo: add am and pm spinners + date + gather the info from it

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