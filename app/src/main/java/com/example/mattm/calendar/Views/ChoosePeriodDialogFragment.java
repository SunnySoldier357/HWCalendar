package com.example.mattm.calendar.Views;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ChoosePeriodDialogFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Using Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
        
        builder.setTitle("Select a Period")
                .setPositiveButton("Create Class", (dialog, which) ->
                {
                    // TODO: Go to add class page with the class name filled up already
                })
                .setNegativeButton("Cancel", (dialog, which) ->
                {
                    // Nothing is required here
                });
        
        return builder.create();
    }
}