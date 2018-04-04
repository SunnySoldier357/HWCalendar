package com.example.mattm.calendar.Models;

import java.util.ArrayList;
import java.util.Collections;

public class User extends TableData
{
    // Private Properties
    private ArrayList<Class> _Classes;
    
    private String UserName;
    private String FirstName;
    private String LastName;
    
    // Constructor
    public User()
    {
        // TODO: Initialise class
    }
    
    // Accessors
    public ArrayList<Class> GetClasses()
    {
        return _Classes;
    }
    
    public String GetUserName()
    {
        return UserName;
    }
    
    public String GetFirstName()
    {
        return FirstName;
    }
    
    public String GetLastName()
    {
        return LastName;
    }
    
    // Mutators
    public void AddClass(Class c)
    {
        if (!_Classes.contains(c))
            _Classes.add(c);
    }
    
    public void ReplaceClasses(Class[] classes)
    {
        _Classes.clear();
        Collections.addAll(_Classes, classes);
    }
    
    public void SetUserName(String userName)
    {
        UserName = userName;
    }
    
    public void SetFirstName(String firstName)
    {
        FirstName = formatName(firstName);
    }
    
    public void SetLastName(String lastName)
    {
        LastName = formatName(lastName);
    }
    
    // Private Methods
    private String formatName(String name)
    {
        StringBuilder result = new StringBuilder();
        String cleanedString = name.trim().replaceAll(" +", " ");
        
        for (String item : cleanedString.split(" "))
            result.append(item.substring(0, 1).toUpperCase())
                    .append(item.substring(1).toLowerCase())
                    .append(" ");
        
        return result.toString().trim();
    }
}