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

        /*
        Bundle bundle = getIntent().getExtras();
        try
        {
            String className = bundle.getString("className").toString();
            periods.add(className);

        }
        catch (Exception e)
        {
            Toast.makeText(this, "wont work", Toast.LENGTH_SHORT).show();
        }
        try
        {
            String eventName = bundle.getString("eventName").toString();
            events.add(eventName);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "ont work", Toast.LENGTH_SHORT).show();
        } */

        //for sample purposes
        periods.add("IB Math HL1");
        events.add("Soccer Practice");

        ArrayAdapter<String> periodsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, periods);
        ListView periodsListView = (ListView)findViewById(R.id.periodsList);
        periodsListView.setAdapter(periodsAdapter);

        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, events);
        ListView eventsListView = (ListView)findViewById(R.id.eventsList);
        eventsListView.setAdapter(eventsAdapter);
        
        // DEBUG PURPOSES
        Toast.makeText(this, "hello KMS friends", Toast.LENGTH_SHORT).show();
    }

    public void buildList(String className){
        periods.add(className);
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
}