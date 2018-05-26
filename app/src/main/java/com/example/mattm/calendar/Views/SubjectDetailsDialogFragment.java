package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.mattm.calendar.R;

public class SubjectDetailsDialogFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Using Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
        
        builder.setTitle("Details")
                .setView(R.layout.fragment_subject_details_dialog)
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