package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;
import com.example.mattm.calendar.Models.User;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddSubjectActivity extends AppCompatActivity
{
    AWSConnection awsConnection;
    String ID;
    DynamoDBMapper dynamoDBMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        try {
            awsConnection = AWSConnection.getCurrentInstance(null);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ID = awsConnection.getUserID();
        dynamoDBMapper = awsConnection.initializeDynamoDBMapper();
    }

    public AsyncTask<String, Void, Void> dataSet(final Subject subject) {
        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                ArrayList<String> dataCollector = new ArrayList<>();
                User oldUser = dynamoDBMapper.load(
                        User.class,
                        ID);
                User user = new User();
                if(oldUser == null)
                {
                    user.setUserId(ID);
                    dataCollector.add(subject.toString());
                }
                else
                {
                    user.setUserId(ID);
                    dataCollector = oldUser.getClasses();
                    dataCollector.add(subject.toString());
                }

                user.setClasses(dataCollector);
                dynamoDBMapper.save(user);
                return null;
            }
        };
        return task;
    }
    
    // Event Handlers
    public void addClassButton_Clicked(View view)
    {
        final Subject subject = new Subject();
        String className = GetClassName();
        String teacher = GetTeacher();
        String period = GetPeriod();
        subject.setTeacherName(teacher);
        subject.setSubject(className);
        subject.setPeriod(period);
        dataSet(subject).execute();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dynamoDBMapper.save(subject);
            }
        }).start();


        //Teacher t = new Teacher(className, teacher, period);

        Intent intentHome = new Intent(this, MainActivity.class);
        /*intentHome.putExtra("className", GetClassName());
        intentHome.putExtra("period", GetPeriod());
        intentHome.putExtra("teacherName", GetTeacher());*/
        startActivity(intentHome);
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