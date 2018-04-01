package com.example.mattm.calendar.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mattm.calendar.Models.Teacher;
import com.example.mattm.calendar.R;

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