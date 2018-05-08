package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-Assignment")
public class Assignment
{
    // Private Properties
    private String assignmentName;
    private String description;
    private String dueDate;
    private String userID;
    
    // Constructors
    public Assignment()
    {
        this ("", "", "", "");
    }
    
    public Assignment(String assignmentName, String description, String dueDate, String userID)
    {
        this.assignmentName = assignmentName;
        this.description = description;
        this.dueDate = dueDate;
        this.userID = userID;
    }
    
    // Accessors
    @DynamoDBAttribute(attributeName = "assignment")
    public String getAssignmentName()
    {
        return assignmentName;
    }
    
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription()
    {
        return description;
    }
    
    @DynamoDBRangeKey(attributeName = "dueDate")
    @DynamoDBAttribute(attributeName = "dueDate")
    public String getDueDate()
    {
        return dueDate;
    }
    
    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserID()
    {
        return userID;
    }

    // Mutators
    public void setAssignmentName(String assignmentName)
    {
        this.assignmentName = assignmentName;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void setDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }
    
    public void setUserID(String userID)
    {
        this.userID = userID;
    }
}