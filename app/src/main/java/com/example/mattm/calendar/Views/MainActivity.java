package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import com.example.mattm.calendar.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.mattm.calendar.Models.Subject.ConvertArrayListToReadable;

public class MainActivity extends AppCompatActivity
{
    // Constants
    private final String TAG = "Testing MainActivity";
    
    // Public Properties
    public ArrayAdapter<String> assignmentsAdapter;
    public ArrayAdapter<String> subjectsAdapter;
    
    public ArrayList<String> assignments = new ArrayList<>();
    public ArrayList<String> subjects = new ArrayList<>();
    public ArrayList<String> readableSubjects = new ArrayList<>();
    
    // Private Properties
    private AWSConnection awsConnection = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
    
        // Binding Data
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        String test = "testing";

        // Hamburger menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setUpFloatingActionButton();
        
        // Initialising database stuff
        try
        {
            awsConnection = AWSConnection.getCurrentInstance(this);
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages

            // Temporary solution
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();
        }

        try
        {
            subjects = awsConnection.getSubjectsAsStrings().execute().get();
            assignments = awsConnection.getAssignmentsAsStrings(subjects).execute().get();
            readableSubjects = ConvertArrayListToReadable(subjects);
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages

            // Temporary solution
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();
        }

        // TODO: Remove when done testing
        Log.d(TAG, "List of subjects: " + subjects.toString());
        Log.d(TAG, "User: " + awsConnection.getUserID());
        Log.d(TAG, "Assignments: " + assignments.toString());
        
        subjectsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, readableSubjects);
        ListView subjectsListView = findViewById(R.id.periodsList);
        subjectsListView.setAdapter(subjectsAdapter);

        assignmentsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, assignments);
        ListView assignmentsListView = findViewById(R.id.eventsList);
        assignmentsListView.setAdapter(assignmentsAdapter);

        subjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                subjectItem_Clicked(position);
            }
        });
    
        GetCalendarDay();
        timeFormatter();
    }
    
    // Event Handlers
    public void logOutButton_Clicked(View view)
    {
        // Logs out
        IdentityManager.getDefaultIdentityManager().signOut();
        subjects.clear();
        subjectsAdapter.notifyDataSetChanged();
        assignments.clear();
        assignmentsAdapter.notifyDataSetChanged();
        Intent intent = new Intent(this,AuthenticatorActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
        // TODO: Remove when done testing
        Log.d(TAG, "Logged Out");
        
        closeDrawerFunction();
    }

    public void subjectAddButton_Clicked(View view)
    {
        closeDrawerFunction();
        
        AddSubjectDialogFragment dialog = new AddSubjectDialogFragment();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }
    
    public void subjectItem_Clicked(int position)
    {
        closeDrawerFunction();

        String dbKey = subjects.get(position);
        String[] split = dbKey.split("_");
        
        Bundle bundle = new Bundle();
        bundle.putString("Subject", split[2]);
        bundle.putString("TeacherName", split[0]);
        bundle.putString("Period", split[1]);
        bundle.putString("ClassName", subjectsAdapter.getItem(position));
        
        SubjectDetailsDialogFragment dialog = new SubjectDetailsDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Dialog");
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
                
                showAssignments(d);
            }
        });
        
        // Gets current day
        String[] date = (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()))
                .replace("T", "")
                .split("-");;
        Date current = new Date(Integer.parseInt(date[0]),
                Integer.parseInt(date[1]),
                Integer.parseInt(date[2]));
        
        showAssignments(current);
    }
    
    public void timeFormatter()
    {
    
    }

    // Private Methods
    private void closeDrawerFunction()
    {
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
    }
    
    private void setUpFloatingActionButton()
    {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    
        setUpHeader();
    }
    
    private void setUpHeader()
    {
        // TODO: Connect this with AWS for the actual username - Kenneth
        String usernameh1 = "mattTest sample";
        String emailh1 = "spamybox6@gmail.com";

        // Sets the Hamburger Menu header with login info
        ((TextView) findViewById(R.id.username_header)).setText(usernameh1);
        ((TextView) findViewById(R.id.email_header)).setText(emailh1);
    }
    
    private void showAssignments(Date dateSelected)
    {
        
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
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}