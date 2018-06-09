package com.example.mattm.calendar.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.R;

import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity
{
    // Private Properties
    private AWSConnection awsConnection;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String year;
    private String month;
    private String day;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppTheme_Dark);
        setContentView(R.layout.activity_add_event);

        initializeDate();
        showDate();
        setUpDatePicker();

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

    }
    
    // Event Handlers
    public void classButton_Clicked(View view)
    {
        // Get Information
        awsConnection.storeAssignment(
                getIntent().getStringExtra("ClassName"),
                String.format("%s-%s-%s", GetYear(), GetMonth(), GetDay()),
                GetEventName(),
                GetDescription()
        ).execute();

        Intent intentHome = new Intent(this, MainActivity.class);
        startActivity(intentHome);
    }
    
    // Private Methods
    private void initializeDate() {
        final Calendar c = Calendar.getInstance();
        year = String.valueOf(c.YEAR);
        month = String.valueOf(c.MONTH + 1);
        day = String.valueOf(c.DAY_OF_MONTH);
    }

    private void setUpDatePicker() {

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddEventActivity.this,
                        android.R.style.Theme_Holo,     //theme can be adjusted     //holoDialog is full screen
                        mDateSetListener,
                        year, month, day);
                //sets the background transparent (unecessary)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i_year, int i_month, int i_day) {    //the month starts at 0
                i_month++;
                year = String.valueOf(i_year);
                month = String.valueOf(i_month);
                day = String.valueOf(i_day);
                showDate();
            }
        };
    }

    private void showDate() {
        mDisplayDate = findViewById(R.id.tvDate);
        String date = month + "/" + day + "/" + year;
        mDisplayDate.setText(date);
    }


     //Accessors
    public String GetDay()
    {
        return day;
    }

    public String GetMonth()        //should return integer for month
    {
        return month;
    }

    public String GetYear()
    {
        return year;
    }
    
    public String GetDescription()
    {
        return ((EditText) findViewById(R.id.descriptionText)).getText().toString();
    }
    
    public String GetEventName()
    {
        return ((EditText) findViewById(R.id.eventName)).getText().toString();
    }

}