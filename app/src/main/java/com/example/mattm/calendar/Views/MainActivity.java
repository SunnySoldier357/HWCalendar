package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    public ArrayAdapter<String> EventsAdapter;
    public ArrayAdapter<String> PeriodsAdapter;
    
    public ArrayList<String> Events = new ArrayList<>();
    public ArrayList<String> Periods = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising database stuff
        Log.d("TESTING", "PART 1");
        AWSConnection awsConnection = null;
        try
        {
            awsConnection = AWSConnection.GetCurrentInstance(this);
            Log.d("TESTING", "PART 2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        Log.d("TESTING", "PART 3");
        Log.d("TESTING", "ID of User: " + awsConnection.GetUserID());

        try
        {
            Periods = awsConnection.GetPeriods().execute().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        Log.d("TESTING", "List of Periods: " + Periods.toString());
        
        EventsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, Events);
        ListView eventsListView = findViewById(R.id.eventsList);
        eventsListView.setAdapter(EventsAdapter);
        
        PeriodsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, Periods);
        ListView periodsListView = findViewById(R.id.periodsList);
        periodsListView.setAdapter(PeriodsAdapter);
        
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
    public void logOut_Clicked(View view)
    {
        IdentityManager.getDefaultIdentityManager().signOut();
        Periods.clear();
        PeriodsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
    }
    
    public void addClassButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddSubjectActivity.class);
        startActivity(intent);
    }

    public void signInButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AuthenticatorActivity.class);
        startActivity(intent);
    }

    public void classItem_Clicked(View view, int position)
    {
        Intent intent = new Intent(this,AddEventActivity.class);
        intent.putExtra("ClassName", PeriodsAdapter.getItem(position));
        startActivity(intent);
    }

    // Public Methods
    /**
     * Gets the day selected on the calendar
     */
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