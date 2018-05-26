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
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AWSConnection
{
    // Constants
    private final String LOG_TAG = "Testing AWSConnection";
    
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
                Log.d(LOG_TAG, "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        
        dynamoDBMapper = initializeDynamoDBMapper();
        userId = updateUserID().execute().get();
    }
    
    // Public Methods
    
    /**
     * Adds a subject to the AWS Database and saves the changes to both the subject and the user.
     * @param subject The Subject object that needs to be added to the AWS Database.
     * @return An AsyncTask that returns nothing but executes the command to insert the Subject
     *         into the database.
     */
    public AsyncTask<Subject, Void, Void> addSubject(final Subject subject)
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Subject, Void, Void> task = new AsyncTask<Subject, Void, Void>()
        {
            @Override
            protected Void doInBackground(Subject... subjects)
            {
                ArrayList<String> dataCollector = new ArrayList<>();
                User oldUser = dynamoDBMapper.load(
                        User.class,
                        userId);
                
                if (null != oldUser)
                {
                    dataCollector = oldUser.getClasses();
                    
                    if (!dataCollector.contains(subject.toString()))
                        dataCollector.add(subject.toString());
                    else
                    {
                        // TODO: MAKE TOAST THAT SAYS 'YOU ARE ALREADY ENROLLED IN THIS CLASS'
                    }
                }
                User user = new User(userId, dataCollector);
                
                dynamoDBMapper.save(user);
                dynamoDBMapper.save(subject);

                return null;
            }
        };

        return task;
    }
    
    /**
     * Queries the AWS Database and returns all Assignments.
     * @param subjects An ArrayList that contains all the subjects of the user.
     * @return An AsyncTask that when executed returns an ArrayList of all the Assignments the user
     *         has.
     */
    public AsyncTask<Void, Void, ArrayList<String>> getAssignments(final ArrayList<String> subjects)
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, ArrayList<String>> task = new AsyncTask<Void, Void, ArrayList<String>>()
        {
            @Override
            protected ArrayList<String> doInBackground(Void... voids)
            {
                ArrayList<String> assignments = new ArrayList<>();
            
                for (String classes: subjects)
                {
                    SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // TODO: For Future use
                    String date = localDateFormat.format(new Date()) + "T";
                
                    Assignment template = new Assignment();
                    template.setUserID(classes);
                
                    DynamoDBQueryExpression<Assignment> queryExpression = new DynamoDBQueryExpression<Assignment>()
                            .withHashKeyValues(template);
                    
                    List<Assignment> results = dynamoDBMapper.query(Assignment.class, queryExpression);
                    for (Assignment assignment : results)
                        assignments.addAll(assignment.getAssignments());
                }
            
                return assignments;
            }
        };
    
        return task;
    }
    
    /**
     * Queries the AWS Database and returns all Subjects.
     * @return An AsyncTask that when executed returns an ArrayList of all the Subjects the user
     *         has.
     */
    public AsyncTask<Void, Void, ArrayList<Subject>> getSubjects()
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, ArrayList<Subject>> task = new AsyncTask<Void, Void, ArrayList<Subject>>()
        {
            @Override
            protected ArrayList<Subject> doInBackground(Void... voids)
            {
                List<Subject> result =  dynamoDBMapper.scan(Subject.class, new DynamoDBScanExpression());
                
                ArrayList<Subject> unsorted = new ArrayList<>(result);
                Collections.sort(unsorted, new Comparator<Subject>()
                {
                    @Override
                    public int compare(Subject o1, Subject o2)
                    {
                        return o1.getSubject().compareTo(o2.getSubject());
                    }
                });
                
                return unsorted;
            }
        };
        
        return task;
    }
    
    /**
     * Queries the AWS Database and returns all Subjects.
     * @return An AsyncTask that when executed returns an ArrayList of all the Subjects the user
     *         has, formatted as Strings.
     */
    public AsyncTask<String, Void, ArrayList<String>> getSubjectsAsStrings()
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, ArrayList<String>> task = new AsyncTask<String, Void, ArrayList<String>>()
        {
            @Override
            protected ArrayList<String> doInBackground(String... strings)
            {
                User currentUser = dynamoDBMapper.load(
                        User.class,
                        userId);
                
                if (null != currentUser)
                {
                    ArrayList<String> result = currentUser.getClasses();
                    Collections.sort(result);
                    
                    return result;
                }
                else
                    return new ArrayList<>();
            }
        };
        
        return task;
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
                Assignment oldAssignment = dynamoDBMapper.load(Assignment.class, user, dueDate);
                Assignment assignment = new Assignment(
                        user,
                        dueDate,
                        null != oldAssignment ? oldAssignment.getAssignments() : new ArrayList<String>(),
                        null != oldAssignment ? oldAssignment.getDescriptions() : new ArrayList<String>()
                );
                assignment.addAssignment(name, "".equals(description) ? " " : description);
                dynamoDBMapper.save(assignment);
    
                return null;
            }
        };
        return task;
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
    
    // Private Methods
    private DynamoDBMapper initializeDynamoDBMapper()
    {
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        return DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
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