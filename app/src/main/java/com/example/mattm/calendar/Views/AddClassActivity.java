package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.mattm.calendar.Models.Subject;
import com.example.mattm.calendar.Models.Teacher;
import com.example.mattm.calendar.R;

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
        Bundle bundle = new Bundle();
        bundle.putString("Teacher", teacher);
        bundle.putString("ClassName", className);
        bundle.putString("Period", period);
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(subject);
            }
        }).start();


        //Teacher t = new Teacher(className, teacher, period);

        Intent intentHome = new Intent(this, MainActivity.class);
        intentHome.putExtras(bundle);
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