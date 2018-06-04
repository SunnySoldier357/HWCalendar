package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mattm.calendar.R;

public class SubjectDetailsDialogFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Using Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
    
        LayoutInflater inflater = getActivity().getLayoutInflater();
    
        View view = inflater.inflate(R.layout.fragment_subject_details_dialog, null);
        ((TextView) view.findViewById(R.id.subjectNameTextView))
                .setText(getArguments().getString("Subject"));
        ((TextView) view.findViewById(R.id.teacherNameTextView))
                .setText(getArguments().getString("TeacherName"));
        ((TextView) view.findViewById(R.id.periodTextView))
                .setText(getArguments().getString("Period"));
        
        builder.setTitle("Details")
                .setView(view)
                .setPositiveButton("Add Assignment", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        
                        Intent intent = new Intent(getActivity(), AddEventActivity.class);
                        intent.putExtra("ClassName",
                                mainActivity.subjectsAdapter.getItem(mainActivity.position));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Nothing is needed here
                    }
                });
    
        // Create the AlertDialog object and return it
        return builder.create();
    }
}