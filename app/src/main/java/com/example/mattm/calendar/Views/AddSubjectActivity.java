package com.example.mattm.calendar.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;
import com.example.mattm.calendar.Models.User;
import com.example.mattm.calendar.R;

import java.util.ArrayList;

public class AddSubjectActivity extends AppCompatActivity
{
    // Private Properties
    private AWSConnection awsConnection;
    
    private DynamoDBMapper dynamoDBMapper;
    
    private String ID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        
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
        
        ID = awsConnection.getUserID();
        dynamoDBMapper = awsConnection.initializeDynamoDBMapper();
    }
    
    // Event Handlers
    public void addClassButton_Clicked(View view)
    {
        final Subject subject = new Subject(GetClassName(), GetTeacher(), GetPeriod());
        dataSet(subject).execute();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dynamoDBMapper.save(subject);
            }
        }).start();

        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }
    
    // Public Methods
    public AsyncTask<String, Void, Void> dataSet(final Subject subject)
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
                        ID);
                
                User user = new User(ID);
                if (null != oldUser)
                    dataCollector = oldUser.getClasses();
                dataCollector.add(subject.toString());
                
                user.setClasses(dataCollector);
                dynamoDBMapper.save(user);
                
                return null;
            }
        };
        
        return task;
    }

    // Accessors
    public String GetClassName()
    {
        return ((EditText) findViewById(R.id.className)).getText().toString();
    }

    public String GetPeriod()
    {
        return ((EditText) findViewById(R.id.period)).getText().toString();
    }
    
    public String GetTeacher()
    {
        return ((EditText) findViewById(R.id.teacherName)).getText().toString();
    }
}