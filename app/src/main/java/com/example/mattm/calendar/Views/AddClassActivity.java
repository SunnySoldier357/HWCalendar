package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.mattm.calendar.Models.Subject;
import com.example.mattm.calendar.Models.Teacher;
import com.example.mattm.calendar.Models.User;
import com.example.mattm.calendar.R;

import java.util.ArrayList;
import java.util.List;

public class AddClassActivity extends MainActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        initAWS();
        initDynamoDBMapper();
    }
    public void initDynamoDBMapper(){
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
    }
    public void initAWS(){
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
    }
    public void userDynamoClassAdd(final Subject subject){
        new Thread(new Runnable() {
            @Override
            public void run() {
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(), // Context
                        "us-west-2:b63ba028-3e34-42f1-9b9b-6d90f70c6ac7", // Identity Pool ID
                        Regions.US_WEST_2 // Region
                );
                String identityId = credentialsProvider.getIdentityId();
                Log.d("LogTag", "my ID is " + identityId);
                loadUser(identityId, subject);

            }
        }).start();
    }
    public void loadUser(final String userId, final Subject subject) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> dataCollector = new ArrayList<>();
                User oldUser = dynamoDBMapper.load(
                        User.class,
                        userId);
                User user = new User();
                if(oldUser == null){
                    user.setUserId(userId);
                    dataCollector.add(subject.toString());
                }else{
                    user.setUserId(userId);
                    dataCollector = oldUser.getClasses();
                    dataCollector.add(subject.toString());
                }
                user.setClasses(dataCollector);
                dynamoDBMapper.save(user);

                // Item read
                // Log.d("News Item:", newsItem.toString());
            }
        }).start();
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

        userDynamoClassAdd(subject);

        new Thread(new Runnable() {
            @Override
            public void run() {
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