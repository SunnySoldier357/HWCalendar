package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-Assignment")
public class Assignment
{
    // Private Properties
    private List<String> assignmentName;
    private List<String> description;
    private String dueDate;
    private String userID;


    @DynamoDBHashKey(attributeName = "userId")
    public String getUserID()
    {
        return userID;
    }
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    @DynamoDBAttribute(attributeName = "assignmentName")
    public List<String> getAssignmentName()
    {
        return assignmentName;
    }
    public void setAssignmentName(List<String> assignmentName)
    {
        this.assignmentName = assignmentName;
    }
    @DynamoDBAttribute(attributeName = "description")
    public List<String> getDescription()
    {
        return description;
    }
    public void setDescription(List<String> description)
    {
        this.description = description;
    }
    
    @DynamoDBRangeKey(attributeName = "dueDate")
    public String getDueDate()
    {
        return dueDate;

    }
    public void setDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }


    // Mutators

    

    

}