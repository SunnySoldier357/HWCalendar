package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    EditText input;



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


        final Builder builder = new Builder(getActivity());
        input =  new EditText(builder.getContext());


        // Temporary arrayList to show in the dialog
        showSubjects = subjects;

        createLists();
        searchTextEntered();

        // TODO(Sandeep): Use the same adapter for both MainActivity and this fragment to save data
        final ArrayAdapter<String> subjectsAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, readableSubjects);

        // Using Builder class for convenient dialog construction

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


    //----------------------------------------------------------------------------------------refresh dialog / listView method needed



    // Public Methods

    public void searchTextEntered(){                        //method that detects changes in the search bar
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchLoop(s.toString());       //goes to the search algorithm


                /*
                if(s.toString().equals("")){            //todo: get the algorithm to work so it doesn't just remove items but also adds them back as the user deletes characters
                    initList();   //resets listVew
                }
                else {
                    //Toast.makeText(getActivity(), s.toString(), Toast.LENGTH_SHORT).show();  //testing purposes:
                    searchLoop(s.toString());
                } */
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initList() {    //resets the listView to all original / available subjects
        showSubjects = subjects;
    }


    public void searchLoop(String s)    //algorithm that performs the search function
    {
        int size = showSubjects.size();

        String sample = s;        //for testing purposes

        // Removes classes that are not searched for
        for (int i = 0; i < size; i++)
        {
            if (!showSubjects.get(i).getSubject().contains(sample))
            {
                Log.d("TEMP", showSubjects.toString());
                showSubjects.remove(i);
                i--;
                size--;
            }
        }

        createLists();
    }


    public void createLists(){          //converts lists to readable format

        readableSubjects = ConvertListToReadable(showSubjects);
        removePeriodDuplicates(readableSubjects, showSubjects);

        //for testing purposes - do not delete - shows that listViews change in the log, but are not displayed
        Log.d("TEMP", "\n");                                                    //todo: make it so the listView updates!!!
        Log.d("TEMP", showSubjects.toString() + "final");
        Log.d("TEMP", readableSubjects.toString() + "final");
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