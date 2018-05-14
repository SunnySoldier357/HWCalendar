package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    // Constants
    private final String LOG_TAG = "Testing MainActivity";
    
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

        //Hamburger menu:
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpHeader();

        // Initialising database stuff
        AWSConnection awsConnection = null;
        try
        {
            awsConnection = AWSConnection.getCurrentInstance(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();
    }

        try
        {
            periods = awsConnection.getPeriods().execute().get();
            events = awsConnection.getAssignments(periods).execute().get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();
        }

        // TODO: Remove when done testing
        Log.d(LOG_TAG, "List of periods: " + periods.toString());
        Log.d(LOG_TAG, "User: " + awsConnection.getUserID());
        Log.d(LOG_TAG, "Assignments: " + events.toString());

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
    public void classAddButton_Clicked(View view)
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

    // Overridden Methods
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
    
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    
    }
    
    @Override           //for the hamburger menu. idk what it does.
    public void onPointerCaptureChanged(boolean hasCapture) { }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_in) 
        {        
            // Goes to the login screen
            Intent intent = new Intent(this, AuthenticatorActivity.class);
            startActivity(intent);

        } 
        else if (id == R.id.log_out) 
        {        
            // Logs out
            IdentityManager.getDefaultIdentityManager().signOut();
            periods.clear();
            periodsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Logged Out");
        } 
        else if (id == R.id.preferences) 
        {         
            // Preferences?
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        
        return true;
    }

    private void setUpHeader(){
        String usernameh1 = "mattTest sample";      //todo: connect this with AWS for the actual username - kenneth
        String emailh1 = "spamybox6@gmail.com";

        TextView tv_username = (TextView) findViewById(R.id.username_header);   //sets the hamburger menu header with login info
        tv_username.setText(usernameh1);
        TextView tv_email = (TextView) findViewById(R.id.email_header);
        tv_email.setText(emailh1);
    }

}