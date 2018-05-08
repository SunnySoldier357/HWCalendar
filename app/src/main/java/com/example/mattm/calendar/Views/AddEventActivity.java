package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Assignment;
import com.example.mattm.calendar.R;

public class AddEventActivity extends AppCompatActivity
{
    // Private Properties
    private AWSConnection awsConnection;
    
    private DynamoDBMapper dynamoDBMapper;
    
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner yearSpinner;
    
    private Spinner startSpinner;
    private Spinner amPmStartSpinner;
    private Spinner endSpinner;
    private Spinner amPmEndSpinner;
    
    private Button addAssignment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    
        try
        {
            awsConnection = AWSConnection.GetCurrentInstance(null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        SetUpSpinners();
    }
    
    // Event Handlers
    public void addEventButton_Clicked(View view)
    {
        boolean changeActivity = true;
    
        String day = GetDay();
        String month = GetMonth();
    
        if (Integer.parseInt(day) == 31 && (month.equalsIgnoreCase("February")
                || month.equalsIgnoreCase("April")
                || month.equalsIgnoreCase("June")
                || month.equalsIgnoreCase("September")
                || month.equalsIgnoreCase("November")))
        {
            changeActivity = false;
            Toast.makeText(this, "The month you selected doesn't have a 31st day", Toast.LENGTH_SHORT).show();
        }
        else if (Integer.parseInt(day) == 30 && (month.equalsIgnoreCase("February")))
        {
            changeActivity = false;
            Toast.makeText(this, "The month you selected doesn't have a 30th day", Toast.LENGTH_SHORT).show();
        }
    
        if (changeActivity)
        {
            Intent intentHome = new Intent(this, MainActivity.class);
            startActivity(intentHome);
        }
    }
    
    public void classButton_Clicked(View view)
    {
        Assignment assignment = new Assignment
        (
            ((EditText) findViewById(R.id.eventName)).getText().toString(),
            ((EditText) findViewById(R.id.descriptionText)).getText().toString(),
            String.format("%s-%s-%sT", GetYear(), GetMonth(), GetDay()),
            getIntent().getStringExtra("ClassName")
        );
        SaveAssignment(assignment);
        
        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }
    
    // Public Methods
    public void SetUpSpinners()
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
        /*
        startSpinner = findViewById(R.id.startTimeSpinner);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.timeArray));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(timeAdapter);

        amPmStartSpinner = findViewById(R.id.amPmStart);
        ArrayAdapter<String> amPmAdapter = new ArrayAdapter<>(AddEventActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.amPmArray));
        amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmStartSpinner.setAdapter(amPmAdapter);

        endSpinner = findViewById(R.id.endTimeSpinner);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endSpinner.setAdapter(timeAdapter);

        amPmEndSpinner = findViewById(R.id.amPmEnd);
        amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmEndSpinner.setAdapter(amPmAdapter);
        */
    }
    
    public void SaveAssignment(final Assignment assignment)
    {
        // TODO: Move to AWSConnection class
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dynamoDBMapper.save(assignment);
            }
        }).start();
    }

    // Accessors
    public String GetDay()
    {
        return daySpinner.getSelectedItem().toString();
    }
    
    public String GetEndAmPm()
    {
        return amPmEndSpinner.getSelectedItem().toString();
    }
    
    public String GetEndTime()
    {
        return endSpinner.getSelectedItem().toString();
    }
    
    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }

    public String GetMonth()
    {
        return monthSpinner.getSelectedItem().toString();
    }
    
    public String GetStartAmPm()
    {
        return amPmStartSpinner.getSelectedItem().toString();
    }
    
    public String GetStartTime()
    {
        return startSpinner.getSelectedItem().toString();
    }

    public String GetYear()
    {
        return yearSpinner.getSelectedItem().toString();
    }
}