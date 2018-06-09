package com.example.mattm.calendar.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel
{
    // Public Properties
    public MutableLiveData<String> UserName;
    
    // Constructors
    public MainActivityViewModel()
    {
        UserName = new MutableLiveData<>();
        UserName.setValue("Mateo");
    }
}