package com.example.mattm.calendar.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class AddSubjectDialogViewModel extends ViewModel
{
    // Public Properties
    public MutableLiveData<ArrayList<String>> SearchSubjects;
    
    // Constructors
    public AddSubjectDialogViewModel()
    {
        SearchSubjects = new MutableLiveData<>();
        SearchSubjects.setValue(new ArrayList<>());
    }
}