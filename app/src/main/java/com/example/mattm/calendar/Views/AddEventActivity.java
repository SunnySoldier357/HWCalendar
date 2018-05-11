package com.example.mattm.calendar.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.mattm.calendar.Models.Assignment;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends AppCompatActivity
{
    // Private Properties
    private Button addAssignment;
    
    private DynamoDBMapper dynamoDBMapper;
    
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner yearSpinner;
    private Spinner startSpinner;
    private Spinner amPmStartSpinner;
    private Spinner endSpinner;
    private Spinner amPmEndSpinner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initAWS();
        initDynamoDBMapper();
        setUpSpinners();
    }
    
    // Event Handlers
    public void addEventButton_Clicked(View view)
    {
        boolean changeActivity = true;
    
        String day = GetDay();
        String month = GetMonth();
        // TODO: Are we doing anything with this?
        String year = GetYear();
        String startTime = GetStartTime();
        String startAmPm = GetStartAmPm();
        String endTime = GetEndTime();
        String endAmPm = GetEndAmPm();
        String eventName = GetEventName();
    
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
        // Get Information
        String USER_ID = getIntent().getStringExtra("ClassName");
        EditText assignment = (EditText) findViewById(R.id.eventName);
        String assignmentValue = assignment.getText().toString();
        String dueDate = GetYear() + "-" + GetMonth() + "-" + GetDay()+ "T";
        Log.d("TESTING", USER_ID);
        EditText description = (EditText) findViewById(R.id.descriptionText);
        String descriptionValue = description.getText().toString();
        storeAssignment(USER_ID,dueDate,assignmentValue,descriptionValue).execute();
        
        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }
    
    // Public Methods
    // TODO: Move these 3 methods to AWSConnection class
    public void initAWS()
    {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler()
        {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult)
            {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
    }
    
    public void initDynamoDBMapper()
    {
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
    }
    
    public AsyncTask<Void, Void, Void> storeAssignment(
            final String user, final String dueDate, final String name, final String description)
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                List<String> assignmentName = new ArrayList<>();
                List<String> descriptionName = new ArrayList<>();
                Log.d("TESTING", user + " | " + dueDate);
                Assignment oldAssignment = dynamoDBMapper.load(
                        Assignment.class,
                        user,
                        dueDate);
                Assignment assignment = new Assignment();
                if (null != oldAssignment)
                {
                    assignmentName = oldAssignment.GetAssignments();
                    descriptionName = oldAssignment.GetDescriptions();
                }
                descriptionName.add(description);
                assignmentName.add(name);
                assignment.SetAssignments(assignmentName);
                assignment.SetDescriptions(descriptionName);
                assignment.SetUserID(user);
                assignment.SetDueDate(dueDate);
                dynamoDBMapper.save(assignment);
            
                return null;
            }
        };
    
        return task;
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
        // TODO: Do we need this?
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
    
    // Accessors
    public String GetDay()
    {
        return daySpinner.getSelectedItem().toString();
    }

    public String GetMonth()
    {
        return monthSpinner.getSelectedItem().toString();
    }

    public String GetYear()
    {
        return yearSpinner.getSelectedItem().toString();
    }

    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }

    public String GetStartTime()
    {
        return startSpinner.getSelectedItem().toString();
    }

    public String GetStartAmPm()
    {
        return amPmStartSpinner.getSelectedItem().toString();
    }

    public String GetEndTime()
    {
        return endSpinner.getSelectedItem().toString();
    }

    public String GetEndAmPm()
    {
        return amPmEndSpinner.getSelectedItem().toString();
    }
}