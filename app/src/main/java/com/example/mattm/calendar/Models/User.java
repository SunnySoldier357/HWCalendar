package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-User")
public class User
{
    // Private Properties
    private ArrayList<String> classes;
    
    private String userId;
    
    // Accessors
    @DynamoDBAttribute(attributeName = "classes")
    public ArrayList<String> getClasses()
    {
        return classes;
    }
    
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId()
    {
        return userId;
    }

    // Mutators
    public void setClasses(ArrayList<String> classes)
    {
        this.classes = classes;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}