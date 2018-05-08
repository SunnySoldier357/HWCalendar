package com.example.mattm.calendar.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AWSConnection
{
    // Private Properties
    private Context context;
    
    private DynamoDBMapper dynamoDBMapper;
    
    private String userId;

    // Constructors
    private AWSConnection(Context context) throws ExecutionException, InterruptedException
    {
        this.context = context;
        
        AWSMobileClient.getInstance().initialize(context, new AWSStartupHandler()
        {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult)
            {
                // TODO: Remove when done testing this
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        
        dynamoDBMapper = initializeDynamoDBMapper();
        userId = updateUserID().execute().get();
        
        // TODO: Remove when done testing this
        Log.d("TESTING", "User ID: " + getUserID());
    }
    
    // Public Methods
    public AsyncTask<String, Void, Void> addSubject(final String subjectName)
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>()
        {
            @Override
            protected Void doInBackground(String... strings)
            {
                ArrayList<String> dataCollector = new ArrayList<>();
                User oldUser = dynamoDBMapper.load(
                        User.class,
                        getUserID());
                
                User user = new User(getUserID());
                if (null != oldUser)
                    dataCollector = oldUser.getClasses();
                dataCollector.add(subjectName);
            
                user.setClasses(dataCollector);
                dynamoDBMapper.save(user);

                return null;
            }
        };
        return task;
    }
    
    public AsyncTask<String, Void, ArrayList<String>> getPeriods()
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, ArrayList<String>> task = new AsyncTask<String, Void, ArrayList<String>>()
        {
            @Override
            protected ArrayList<String> doInBackground(String... strings)
            {
                User currentUser = dynamoDBMapper.load(
                        User.class,
                        getUserID());
                
                return null != currentUser ? currentUser.getClasses() : new ArrayList<String>();
            }
        };
        return task;
    }
    
    public DynamoDBMapper initializeDynamoDBMapper()
    {
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        return DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
    }
    
    public void saveSubject(final Subject subject)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dynamoDBMapper.save(subject);
            }
        }).start();
    }
    
    public AsyncTask<Void, Void, String> updateUserID()
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        context, // Context
                        "us-west-2:b63ba028-3e34-42f1-9b9b-6d90f70c6ac7", // Identity Pool ID
                        Regions.US_WEST_2 // Region
                );
                
                userId = credentialsProvider.getIdentityId();
                return userId;
            }
        };
        return task;
    }
    
    // Accessors
    public String getUserID()
    {
        return userId;
    }
    
    // Singleton Pattern
    private static AWSConnection currentInstance = null;
    
    public static AWSConnection getCurrentInstance(Context context) throws ExecutionException, InterruptedException
    {
        return null == currentInstance ? currentInstance = new AWSConnection(context) : currentInstance;
    }
}