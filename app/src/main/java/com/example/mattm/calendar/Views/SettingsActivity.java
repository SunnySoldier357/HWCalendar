package com.example.mattm.calendar.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mattm.calendar.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch themeSwitch;
    private TextView light;
    private TextView dark;
    private boolean darkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        light = findViewById(R.id.light);
        dark = findViewById(R.id.dark);

        themeSwitch = (Switch) findViewById(R.id.colorThemeSwitch);
        themeSwitch.setOnCheckedChangeListener(this);

        String read = readFromFile(this, "color_theme");
        darkTheme = read.equals("true");

        themeSwitch.setChecked(darkTheme);
        adjustText(darkTheme);
    }

    // Event Handlers
    public void aboutOnClick(View view) //if the user taps on the text that says "about"
    {
        Toast.makeText(this, "\n Developed by KMS \n", Toast.LENGTH_SHORT).show();
    }

    @Override           //if the user changes the switch from light theme to dark theme
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        adjustText(isChecked);
    }

    private void adjustText(boolean isChecked){
        darkTheme = isChecked;

        if (isChecked)
        {
            darkTheme = true;
            dark.setTextColor(getResources().getColor(R.color.colorFontDark));
            dark.setTypeface(null, Typeface.BOLD);
            light.setTextColor(getResources().getColor(R.color.colorFontReg));  //todo: fix this matt
        }
        else
        {
            darkTheme = false;
            light.setTextColor(getResources().getColor(R.color.colorFontDark));
            dark.setTextColor(getResources().getColor(R.color.colorFontReg));
            dark.setTypeface(null, Typeface.NORMAL);
        }
        writeToFile(String.valueOf(darkTheme), this, "color_theme");
    }



    //File IO to save the theme to a text file:
    private void writeToFile(String data,Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Toast.makeText(context, "Data save failed", Toast.LENGTH_SHORT).show();
        }
    }

    public String readFromFile(Context context, String fileName) {
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }



}