package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    // Public Properties
    public ArrayAdapter<String> periodsAdapter;
    public ArrayAdapter<String> eventsAdapter;
    
    public ArrayList<String> periods = new ArrayList<>();
    public ArrayList<String> events = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising database stuff
        // TODO: Remove when done testing
        Log.d("TESTING", "PART1");
        
        AWSConnection awsConnection = null;
        try
        {
            awsConnection = AWSConnection.getCurrentInstance(this);
            // TODO: Remove when done testing
            Log.d("TESTING", "PART2");
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand
            e.printStackTrace();
        }
    
        // TODO: Remove when done testing
        Log.d("TESTING", "PART3");
        Log.d("TESTING", "ID of User: " + awsConnection.getUserID());

        try
        {
            periods = awsConnection.getPeriods().execute().get();
            events = awsConnection.getAssignments(periods).execute().get();
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand
            e.printStackTrace();
        }
    
        // TODO: Remove when done testing
        Log.d("TESTING", "List of periods: " + periods.toString());

        periodsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, periods);
        ListView periodsListView = findViewById(R.id.periodsList);
        periodsListView.setAdapter(periodsAdapter);

        eventsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, events);
        ListView eventsListView = findViewById(R.id.eventsList);
        eventsListView.setAdapter(eventsAdapter);

        periodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                classItem_Clicked(view, position);
            }
        });

        GetCalendarDay();
    }
    
    // Event Handlers
    public void addClassButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddSubjectActivity.class);
        startActivity(intent);
    }
    
    public void classItem_Clicked(View view, int position)
    {
        Intent intent = new Intent(this,AddEventActivity.class);
        intent.putExtra("ClassName", periodsAdapter.getItem(position));
        startActivity(intent);
    }
    
    public void logOut_Clicked(View view)
    {
        IdentityManager.getDefaultIdentityManager().signOut();
        periods.clear();
        periodsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
    }
    
    public void signInButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AuthenticatorActivity.class);
        startActivity(intent);
    }
    
    // Public Methods
    public void GetCalendarDay()
    {
        CalendarView mainCalendarView = findViewById(R.id.calendar);
        
        mainCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                // Converts data to a Date object
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
    
                // Date object of selected day on calendar
                Date d = cal.getTime();

                Toast.makeText(MainActivity.this, d.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}