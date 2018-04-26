package com.example.mattm.calendar.ViewModels;

import android.widget.ArrayAdapter;

import com.example.mattm.calendar.Models.AWSConnection;
import com.example.mattm.calendar.Models.User;

public class UserViewModel
{
    // Private Properties
    AWSConnection awsConnection;
    
    // Public Properties
    public ArrayAdapter<User> Users;
    
    // Constructor
    
    public UserViewModel()
    {
        awsConnection = new AWSConnection();
        
        Users = awsConnection.GetUsers();
    }
    
    // TODO: Use AWSConnection class to encapsulate getting and adding Users
}