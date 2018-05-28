package com.example.mattm.calendar.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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

public class SettingsActivity extends AppCompatActivity implements OnCheckedChangeListener
{
    // Private Properties
    private boolean darkTheme;
    
    private Switch themeSwitch;
    
    private TextView dark;
    private TextView light;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    
        dark = findViewById(R.id.dark);
        light = findViewById(R.id.light);
        
        themeSwitch = findViewById(R.id.colorThemeSwitch);
        themeSwitch.setOnCheckedChangeListener(this);

        String read = readFromFile(this, "color_theme");
        darkTheme = read.equals("true");

        themeSwitch.setChecked(darkTheme);
        adjustText(darkTheme);
    }

    // Event Handlers
    public void aboutOnClick(View view)
    {
        // If the user taps on the text that says "about"
        Toast.makeText(this, "\n Developed by KMS \n", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        // If the user changes the switch from light theme to dark theme
        adjustText(isChecked);
    }
    
    // Public Methods
    public String readFromFile(Context context, String fileName)
    {
        String ret = "";
    
        try
        {
            InputStream inputStream = context.openFileInput(fileName);
        
            if (null != inputStream)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
            
                while (null != (receiveString = bufferedReader.readLine()))
                    stringBuilder.append(receiveString);
            
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages
            Log.e("login activity", "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    
        return ret;
    }

    // Private Methods
    private void adjustText(boolean isChecked)
    {
        // TODO: Fix this (Mateo)
        darkTheme = isChecked;
        (isChecked ? dark : light).setTextColor(getResources().getColor(R.color.colorFontDark));
        (isChecked ? light : dark).setTextColor(getResources().getColor(R.color.colorFontReg));
        dark.setTypeface(null, isChecked ? Typeface.BOLD : Typeface.NORMAL);
        
        writeToFile(String.valueOf(darkTheme), this, "color_theme");
    }
    
    /**
     * File I/O to save the theme to a text file
     * @param data
     * @param context
     * @param fileName
     */
    private void writeToFile(String data,Context context, String fileName)
    {
        try
        {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            // TODO: Remove when done testing
            Log.e("Exception", "File write failed: " + e.toString());
            
            Toast.makeText(context, "Data save failed", Toast.LENGTH_SHORT).show();
        }
    }
}