package com.example.mattm.calendar.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mattm.calendar.R;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    // Event Handlers
    public void aboutOnClick(View view)
    {
        Toast.makeText(this, "\n Developed by KMS \n", Toast.LENGTH_SHORT).show();
    }
}