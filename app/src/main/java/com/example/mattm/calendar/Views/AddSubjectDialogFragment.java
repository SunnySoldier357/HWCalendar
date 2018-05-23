package com.example.mattm.calendar.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;

import java.util.List;

import static com.example.mattm.calendar.Models.Subject.ConvertListToReadable;

public class AddSubjectDialogFragment extends DialogFragment
{
    // Private Properties
    private List<Subject> subjects;
    
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
        
        // TODO(Sandeep): Use the same adapter for both MainActivity and this fragment to save data
        final ArrayAdapter<String> subjectsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ConvertListToReadable(subjects));
        
        // Using Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    
        // Sets the EditText (search bar)
        final EditText input =  new EditText(builder.getContext());
        input.setHint("Search for a class");

        builder.setTitle("Classes")
                .setView(input)
                .setAdapter(subjectsAdapter, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Do Something if the class is clicked
                        awsConnection.addSubject(subjects.get(which)).execute();
                        Toast.makeText(getActivity(), String.format("'%s' class selected!", subjects.get(which)),
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Create Class", new DialogInterface.OnClickListener()
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
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
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
}