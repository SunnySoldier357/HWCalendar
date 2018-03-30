package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    // Private Properties
    public ArrayList<String> periods = new ArrayList<>();
    public ArrayList<String> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpData();

        ArrayAdapter<String> periodsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, periods);
        ListView periodsListView = findViewById(R.id.periodsList);
        periodsListView.setAdapter(periodsAdapter);

        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, events);
        ListView eventsListView = findViewById(R.id.eventsList);
        eventsListView.setAdapter(eventsAdapter);
    }


    // Event Handlers
    public void addClassButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    public void addEventButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    public void signInButton_Clicked(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    //set up (onCreate):
    public void setUpData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        try
        {
            String className = bundle.getString("className");
            periods.add(className);

        }
        catch (Exception e) { Log.e("Main Activity", "extra not found: " + e.toString()); }
        try
        {
            String eventName = bundle.getString("date");
            events.add(eventName);
        }
        catch (Exception e) { Log.e("Main Activity", "extra not found: " + e.toString()); }

        //for sample purposes
        periods.add("IB Math HL1");
        events.add("Soccer Practice");
    }
}