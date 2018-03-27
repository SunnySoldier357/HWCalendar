package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEventActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    // Event Handlers
    public void addEventButton_Clicked(View view)
    {
        Intent intentHome = new Intent(this, MainActivity.class);
        intentHome.putExtra("date", GetDate());
        intentHome.putExtra("endTime", GetEndTime());
        intentHome.putExtra("eventName", GetEventName());
        intentHome.putExtra("startTime", GetStartTime());
        startActivity(intentHome);
    }

    // Accessors
    public String GetDate()
    {
        return ((EditText) findViewById(R.id.date)).getText().toString();
    }
    
    public String GetEndTime()
    {
        return ((EditText) findViewById(R.id.endTime)).getText().toString();
    }
    
    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }
    
    public String GetStartTime()
    {
        return ((EditText) findViewById(R.id.startTime)).getText().toString();
    }
}