package com.example.mattm.calendar.Models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

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
    private static AWSConnection currentInstance = null;
    private Context context;
    private DynamoDBMapper dynamoDBMapper;
    private String userId = null;
    // TODO: Put all AWS logic in this class
    
    //Public Methods
    public static AWSConnection getCurrentInstance(Context context) throws ExecutionException, InterruptedException {
        if(null == currentInstance)
            currentInstance = new AWSConnection(context);
        return currentInstance;
    }

    public String getUserID()
    {
        return userId;
    }

    // Constructors
    private AWSConnection(Context context) throws ExecutionException, InterruptedException {
        this.context = context;
        AWSMobileClient.getInstance().initialize(context, new AWSStartupHandler()
        {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult)
            {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        dynamoDBMapper = initializeDynamoDBMapper();
        userId = updateUserID().execute().get();
        Log.d("TESTING", userId);

    }

    public DynamoDBMapper initializeDynamoDBMapper(){
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        return DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
    }
    public AsyncTask<Void, Void, String> updateUserID(){
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        context, // Context
                        "us-west-2:b63ba028-3e34-42f1-9b9b-6d90f70c6ac7", // Identity Pool ID
                        Regions.US_WEST_2 // Region
                );
                userId = credentialsProvider.getIdentityId();
                return credentialsProvider.getIdentityId();
            }
        };
        return task;
    }
    public AsyncTask<String, Void, ArrayList<String>> getPeriods(){
        AsyncTask<String, Void, ArrayList<String>> task = new AsyncTask<String, Void, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(String... strings) {
                User currentUser = dynamoDBMapper.load(
                        User.class,
                        userId);
                if(currentUser != null)
                    return currentUser.getClasses();
                else
                    return new ArrayList<>();
            }
        };
        return task;
    }

    public AsyncTask<String, Void, Void> addSubject(final String subjectName) {
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                ArrayList<String> dataCollector = new ArrayList<>();
                User oldUser = dynamoDBMapper.load(
                        User.class,
                        userId);
                Log.d("TESTING", "Is oldUser null? " + oldUser == null ? "Yes" : "No");
                User user = new User();
                if(null == oldUser)
                {
                    user.setUserId(userId);
                    dataCollector.add(subjectName);
                }
                else
                {
                    user.setUserId(userId);
                    dataCollector = oldUser.getClasses();
                    dataCollector.add(subjectName);
                }

                user.setClasses(dataCollector);
                dynamoDBMapper.save(user);
                //dynamoDBMapper.save(subject);
                return null;
            }
        };
        return task;
    }
    public void saveSubject(final Subject subject){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dynamoDBMapper.save(subject);
            }
        }).start();
    }
    public Assignment AddAssignment()
    {
        // TODO: Add an assignment to the DB
        return null;
    }
    
    public Subject AddSubject()
    {
        // TODO: Add a subject to the DB
        return null;
    }
    
    public User AddUser()
    {
        // TODO: Add a User to the DB
        return null;
    }
    
    public ArrayAdapter<User> GetUsers()
    {
        // TODO: Return a list of Users from the DB
        return null;
    }
    
    // Private Methods
    private void initialise()
    {
        // TODO: Initialise database
    }
}