package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-User")
public class User
{
    // Private Properties
    private ArrayList<String> classes;
    
    private String userId;
    
    // Constructors
    public User()
    {
        this(new ArrayList<String>(), "");
    }
    
    public User(String userId)
    {
        this (new ArrayList<String>(), userId);
    }
    
    public User(ArrayList<String> classes, String userId)
    {
        this.classes = classes;
        this.userId = userId;
    }
    
    // Accessors
    @DynamoDBAttribute(attributeName = "classes")
    public ArrayList<String> GetClasses()
    {
        return classes;
    }
    
    @DynamoDBHashKey(attributeName = "userId")
    public String GetUserId()
    {
        return userId;
    }

    // Mutators
    public void SetClasses(ArrayList<String> classes)
    {
        this.classes = classes;
    }
    
    public void SetUserId(String userId)
    {
        this.userId = userId;
    }
}