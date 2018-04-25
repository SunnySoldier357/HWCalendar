package com.example.mattm.calendar.Views;

import android.content.Intent;
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity
{
    private DynamoDBMapper dynamoDBMapper;
    Spinner monthSpinner;
    Spinner daySpinner;
    Spinner yearSpinner;
    Spinner startSpinner;
    Spinner amPmStartSpinner;
    Spinner endSpinner;
    Spinner amPmEndSpinner;
    private Button addAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initAWS();
        initDynamoDBMapper();
        setUpSpinners();
    }

    public void getInformation()
    {
        String TEMP_USER_ID = "TIMOTHY_1_IBMATH";
        EditText assignment = (EditText) findViewById(R.id.eventName);
        String assignmentValue = assignment.getText().toString();
        String dueDate = GetYear() + "-" + GetMonth() + "-" + GetDay()+ "T";
        EditText description = (EditText) findViewById(R.id.descriptionText);
        String descriptionValue = description.getText().toString();
        storeAssignment(TEMP_USER_ID,dueDate,assignmentValue,descriptionValue);
    }
    
    public void classButton_Clicked(View view)
    {
        getInformation();
        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }

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

    public void storeAssignment(String user, String dueDate, String name, String description)
    {
        final Assignment assignment = new Assignment();
        assignment.setUserID(user);
        assignment.setDueDate(dueDate);
        assignment.setAssignmentName(name);
        assignment.setDescription(description);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dynamoDBMapper.save(assignment);
            }
        }).start();
    }


    // Event Handlers
    public void addEventButton_Clicked(View view)
    {
        boolean changeActivity = true;

        String day = GetDay();
        String month = GetMonth();
        String year = GetYear();
        String startTime = GetStartTime();
        String startAmPm = GetStartAmPm();
        String endTime = GetEndTime();
        String endAmPm = GetEndAmPm();
        String eventName = GetEventName();

        if(Integer.parseInt(day) == 31 && (month.equalsIgnoreCase("February")
                || month.equalsIgnoreCase("April")
                || month.equalsIgnoreCase("June")
                || month.equalsIgnoreCase("September")
                || month.equalsIgnoreCase("November")))
        {
            changeActivity = false;
            Toast.makeText(this, "The month you selected doesn't have a 31st day", Toast.LENGTH_SHORT).show();
        }

        if(Integer.parseInt(day) == 30 && (month.equalsIgnoreCase("february")))
        {
            changeActivity = false;
            Toast.makeText(this, "The month you selected doesn't have a 30th day", Toast.LENGTH_SHORT).show();
        }
        
        if (changeActivity == true)
        {
            Intent intentHome = new Intent(this, MainActivity.class);
            /*
            intentHome.putExtra("date", date);
            intentHome.putExtra("endTime", GetEndTime());
            intentHome.putExtra("eventName", GetEventName());
            intentHome.putExtra("startTime", GetStartTime());
            */
            startActivity(intentHome);
        }
    }

    // Accessors (gets the entered edit text data)
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
        String eventName;
        EditText input = findViewById(R.id.eventName);
        eventName = input.getText().toString();
        return eventName;
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


    //setup (onCreate)
    public void setUpSpinners()
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
}