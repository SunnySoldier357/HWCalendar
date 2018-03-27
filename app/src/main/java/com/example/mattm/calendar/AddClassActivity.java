package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddClassActivity extends MainActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
    }

    // Event Handlers
    public void addClassButton_Clicked(View view)
    {
        String className = GetClassName();
        String teacher = GetTeacher();
        String period = GetPeriod();

        Teacher t = new Teacher(className, teacher, period);

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