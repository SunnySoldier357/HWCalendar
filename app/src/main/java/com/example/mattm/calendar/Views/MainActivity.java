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
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
    // Public Properties
    public ArrayList<String> periods = new ArrayList<>();
    public ArrayList<String> events = new ArrayList<>();
    ArrayAdapter<String> periodsAdapter;
    ArrayAdapter<String> eventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialising database stuff
        Log.d("TESTING", "PART1");
        AWSConnection awsConnection = null;
        try {
            awsConnection = AWSConnection.getCurrentInstance(this);
            Log.d("TESTING", "PART2");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("TESTING", "PART3");
        Log.d("TESTING", "ID of User: " + awsConnection.getUserID());

        try {
            periods = awsConnection.getPeriods().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("TESTING", periods.toString());

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

        calendar_Selector();
    }
    
    // Event Handlers
    public void logOut_Clicked(View view)
    {
        IdentityManager.getDefaultIdentityManager().signOut();
        periods.clear();
        periodsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
    }
    
    public void addClassButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddSubjectActivity.class);
        //intent.putExtra("ID", ID);
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
        intent.putExtra("ClassName", periodsAdapter.getItem(position).toString());
        startActivity(intent);
    }

    public void calendar_Selector()             //this gets the day selected on the calendar
    {
        CalendarView mainCalendarView = findViewById(R.id.calendar);
        mainCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                Calendar cal = Calendar.getInstance();  //converts  data to a date object
                cal.set(year, month, dayOfMonth);

                Date d = cal.getTime();     //Date object of selected day on calendar

                Toast.makeText(MainActivity.this, d.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goToNav_Clicked(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }



}