package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DEBUG PURPOSES
        Toast.makeText(this, "hello KMS friends", Toast.LENGTH_SHORT).show();
    }
    
    public void addClassButton_Clicked(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }
}
