package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.AWSStartupHandler;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.mattm.calendar.Models.User;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity
{
    // Public Properties
    public ArrayList<String> periods = new ArrayList<>();
    public ArrayList<String> events = new ArrayList<>();
    public String ID;
    ArrayAdapter<String> periodsAdapter;
    ArrayAdapter<String> eventsAdapter;
    DynamoDBMapper dynamoDBMapper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising database stuff
        initAWS();
        initDynamoDBMapper();
        initAWSCognito();
        setUpData();
        Log.d("TESTING MAIN", ID);

        Log.d("TESTING MAIN",periods.toString());

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
                classItem_Clicked(view);
            }
        });
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
    
    public void initAWSCognito()
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] identityId = new String[1];
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(), // Context
                        "us-west-2:b63ba028-3e34-42f1-9b9b-6d90f70c6ac7", // Identity Pool ID
                        Regions.US_WEST_2 // Region
                );
                identityId[0] = credentialsProvider.getIdentityId();
                latch.countDown();
                Log.d("LogTag", "my ID is " + identityId[0]);
                //dynamoLoad(identityId);
            }
        }).start();

        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        Log.d("TESTING", identityId[0]);
        ID = identityId[0];
        Log.d("TESTING", ID);
    }
    
    // Event Handlers
    public void logOut_Clicked(View view)
    {
        IdentityManager.getDefaultIdentityManager().signOut();
        periods.clear();
        periodsAdapter.notifyDataSetChanged();
    }
    
    public void addClassButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    public void signInButton_Clicked(View view)
    {
        Intent intent = new Intent(this, AuthenticatorActivity.class);
        startActivity(intent);
    }

    public void classItem_Clicked(View view)
    {
        Intent intent = new Intent(this,AddEventActivity.class);
        startActivity(intent);
    }

    //set up (onCreate):
    public void setUpData()
    {
        final CountDownLatch latch = new CountDownLatch(1);
        final User[] value = new User[1];
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                User currentUser = dynamoDBMapper.load(
                        User.class,
                        ID);
                if(currentUser != null)
                {
                    Log.d("TESTING", "AVAI");
                    value[0] = currentUser;
                    Log.d("TESTING", currentUser.getClasses().toString());

                }
                latch.countDown();
            }
        }).start();
        
        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
        if(value[0] != null)
        {
            periods = value[0].getClasses();
            Log.d("TESTING", value[0].getClasses().toString());
        }
        events.add("Soccer Practice");
    }
}