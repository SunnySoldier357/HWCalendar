package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.mattm.calendar.Models.Subject.ConvertListToReadable;

public class AddSubjectDialogFragment extends DialogFragment
{
    // Private Properties
    private ArrayList<Subject> subjects;
    private ArrayList<Subject> availableSubjects;
    private AWSConnection awsConnection;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        try
        {
            awsConnection = AWSConnection.getCurrentInstance(null);
            subjects = awsConnection.getDialogSubject().execute().get();
        }
        catch (Exception e)
        {
            // TODO: UI - Show error message to User in a way they will understand for different error messages
            e.printStackTrace();
        }

        searchLoop();


        // TODO(Sandeep): Use the same adapter for both MainActivity and this fragment to save data
        final ArrayAdapter<String> subjectsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ConvertListToReadable(availableSubjects));
        
        // Using Builder class for convenient dialog construction
        final Builder builder = new Builder(getActivity());
    
        // Sets the EditText (search bar)
        final EditText input =  new EditText(builder.getContext());
        input.setHint("Search for a class");

        builder.setTitle("Classes")
                .setView(input)
                .setAdapter(subjectsAdapter, new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Do Something if the class is clicked
                        awsConnection.addSubject(subjects.get(which)).execute();
                        Toast.makeText(getActivity(), String.format("'%s' class selected!", availableSubjects.get(which)),
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Create Class", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO: use this to search for classes, recommend using a thread - kenneth
                        String search = input.getText().toString();

                        Intent intent = new Intent(getActivity(), AddSubjectActivity.class);
                        getActivity().startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Nothing is required here
                        // TODO: Remove when done testing
                        Toast.makeText(getActivity(), "Dialog Canceled", Toast.LENGTH_LONG).show();
                    }
                });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void searchLoop() {
        availableSubjects = subjects;


            String sample = "SS";
            Log.d("TEMP", subjects.toString() + "");
            Log.d("TEMP", availableSubjects.toString() + "");

            final int size = availableSubjects.size();

            for (int i=0; i<size; i++){
                if(!availableSubjects.get(i).toString().contains(sample)){
                    availableSubjects.remove(i);
                }

            }



    }


}