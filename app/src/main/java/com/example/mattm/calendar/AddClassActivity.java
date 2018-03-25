package com.example.mattm.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
    }

    public void onClickAddClass(View view) {
        Intent intentHome = new Intent(this, MainActivity.class);
        intentHome.putExtra("className", getClassName());
        intentHome.putExtra("teacherName", getTeacher());
        intentHome.putExtra("period", getPeriod());
        startActivity(intentHome);
    }


    public String getClassName(){
        EditText className = findViewById(R.id.className);
        return className.getText().toString();
    }

    public String getTeacher() {
        EditText teacher = findViewById(R.id.teacherName);
        return teacher.getText().toString();
    }

    public String getPeriod() {
        EditText period = findViewById(R.id.period);
        return period.getText().toString();
    }


}
