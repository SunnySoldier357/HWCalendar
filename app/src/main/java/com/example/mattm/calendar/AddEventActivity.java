package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void onClickAddEvent(View view) {
        Intent intentHome = new Intent(this, MainActivity.class);
        intentHome.putExtra("eventName", getEventName());
        intentHome.putExtra("date", getDate());
        intentHome.putExtra("startTime", getTimeStart());
        intentHome.putExtra("endTime", getTimeEnd());
        startActivity(intentHome);
    }


    public String getEventName(){
        EditText name = findViewById(R.id.eventName);
        return name.getText().toString();
    }

    public String getDate() {
        EditText date = findViewById(R.id.date);
        return date.getText().toString();
    }

    public String getTimeStart() {
        EditText startTime = findViewById(R.id.startTime);
        return startTime.getText().toString();
    }

    public String getTimeEnd() {
        EditText endTime = findViewById(R.id.endTime);
        return endTime.getText().toString();
    }
}
