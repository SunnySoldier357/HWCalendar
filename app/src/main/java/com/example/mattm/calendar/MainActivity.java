package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //for sample purposes
        String[] periods = {"King", "Fowler", "Reinsch"};
        ArrayAdapter<String> periodsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, periods);
        ListView periodsListView = (ListView)findViewById(R.id.periodsList);
        periodsListView.setAdapter(periodsAdapter);

        String[] events = {"Read crucible", "IB questions"};
        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, events);
        ListView eventsListView = (ListView)findViewById(R.id.eventsList);
        eventsListView.setAdapter(eventsAdapter);


        // DEBUG PURPOSES
        Toast.makeText(this, "hello KMS friends", Toast.LENGTH_SHORT).show();
    }



    public void addClassButton_Clicked(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }
}
