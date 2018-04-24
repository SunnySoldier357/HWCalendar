package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.AWSStartupHandler;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.mattm.calendar.Models.Assignment;
import com.example.mattm.calendar.R;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    // Private Properties
    public ArrayList<String> periods = new ArrayList<>();
    public ArrayList<String> events = new ArrayList<>();
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
        storeAssignment();
        setUpData();

        ArrayAdapter<String> periodsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, periods);
        ListView periodsListView = findViewById(R.id.periodsList);
        periodsListView.setAdapter(periodsAdapter);

        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, events);
        ListView eventsListView = findViewById(R.id.eventsList);
        eventsListView.setAdapter(eventsAdapter);

        periodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classItem_Clicked(view);
            }
        });
    }

    public void storeAssignment(){
        final Assignment assignment = new Assignment();
        assignment.setUserID("King_1/2_IBHistory");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        assignment.setDueDate(timestamp);
        assignment.setAssignmentName("Pres");
        assignment.setDescription("present to class");
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(assignment);
                // Item saved
            }
        }).start();
    }


    // Event Handlers
    public void addClassButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    public void signInButton_Clicked(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void classItem_Clicked(View view){
        Intent intent = new Intent(this,AddEventActivity.class);
        startActivity(intent);
    }



    //set up (onCreate):
    public void setUpData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        try {
            String className = bundle.getString("className");
            periods.add(className);

        } catch (Exception e) {
            Log.e("Main Activity", "extra not found: " + e.toString());
        }
        try {
            String eventName = bundle.getString("date");
            events.add(eventName);
        } catch (Exception e) {
            Log.e("Main Activity", "extra not found: " + e.toString());
        }

        //for sample purposes
        periods.add("IB Math HL1");
        periods.add("Physics");
        periods.add("Spanish");
        events.add("Soccer Practice");
    }
}