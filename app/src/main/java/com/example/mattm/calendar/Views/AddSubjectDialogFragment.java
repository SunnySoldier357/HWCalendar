package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.Subject;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.mattm.calendar.Models.Subject.ConvertListToReadable;

public class AddSubjectDialogFragment extends DialogFragment
{
    // Private Properties
    private ArrayList<String> readableSubjects;
    
    private ArrayList<Subject> showSubjects;
    private ArrayList<Subject> subjects;
    
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
    
        // Temporary arrayList to show in the dialog
        showSubjects = subjects;
        readableSubjects = ConvertListToReadable(showSubjects);
        removePeriodDuplicates(readableSubjects, showSubjects);
        searchLoop();
        
        // TODO(Sandeep): Use the same adapter for both MainActivity and this fragment to save data
        final ArrayAdapter<String> subjectsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, readableSubjects);
        
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
                        awsConnection.addSubject(showSubjects.get(which)).execute();
                        Toast.makeText(getActivity(), String.format("'%s' class selected!", showSubjects.get(which)),
                                Toast.LENGTH_LONG).show();
    
                        ChoosePeriodDialogFragment chooseDialog = new ChoosePeriodDialogFragment();
                        chooseDialog.show(getActivity().getSupportFragmentManager(), "Dialog2");
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

    // Public Methods
    public void searchLoop()
    {
        int size = showSubjects.size();

        // TODO: Set this up with the edit text search bar and a thread (Mateo)
        String sample = "";
    
        // Removes classes that are not searched for
        for (int i = 0; i < size; i++)
        {
            if (!showSubjects.get(i).getSubject().contains(sample))
            {
                showSubjects.remove(i);
                i--;
                size--;
            }
        }
    }
    
    // Private Methods
    private void removePeriodDuplicates(ArrayList<String> duplicateStr, ArrayList<Subject> duplicateSub)
    {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i = 1; i < duplicateStr.size(); i++)
        {
            if (duplicateStr.get(i - 1).equals(duplicateStr.get(i)))
                toRemove.add(i);
        }
        
        Collections.reverse(toRemove);
        
        for (int index: toRemove)
        {
            duplicateStr.remove(index);
            duplicateSub.remove(index);
        }
    }
}