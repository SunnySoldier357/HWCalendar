package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

public class AddEventActivity extends AppCompatActivity
{
    // Private Properties
    private AWSConnection awsConnection;
    
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner yearSpinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_add_event);
        
        try
        {
            awsConnection = AWSConnection.getCurrentInstance(null);
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages

            // Temporary solution
            e.printStackTrace();
            Toast.makeText(this, "Unable to connect to network", Toast.LENGTH_LONG).show();
        }
        
        setUpSpinners();
    }
    
    // Event Handlers
    public void classButton_Clicked(View view)
    {
        // Get Information
        awsConnection.storeAssignment(
                getIntent().getStringExtra("ClassName"),
                String.format("%s-%s-%s", GetYear(), GetMonth(), GetDay()),
                GetEventName(),
                GetDescription()
        ).execute();

        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }
    
    // Private Methods
    private void setUpSpinners()
    {
        monthSpinner = findViewById(R.id.month);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.monthArray));
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
    
        daySpinner = findViewById(R.id.day);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dayArray));
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
    
        yearSpinner = findViewById(R.id.year);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.yearArray));
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }
    
    // Accessors
    public String GetDay()
    {
        return daySpinner.getSelectedItem().toString();
    }
    
    public String GetDescription()
    {
        return ((EditText) findViewById(R.id.descriptionText)).getText().toString();
    }
    
    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }

    public String GetMonth()
    {
        return monthSpinner.getSelectedItem().toString();
    }

    public String GetYear()
    {
        return yearSpinner.getSelectedItem().toString();
    }
}