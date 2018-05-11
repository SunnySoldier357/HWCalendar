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
    private List<String> assignments;
    private List<String> descriptions;
    
    private String dueDate;
    private String userID;
    
    // Constructors
    public Assignment()
    {
        this("", "", new ArrayList<String>(), new ArrayList<String>());
    }
    
    public Assignment(String userID, String dueDate)
    {
        this(userID, dueDate, new ArrayList<String>(), new ArrayList<String>());
    }
    
    public Assignment(String userID, String dueDate, List<String> assignments, List<String> descriptions)
    {
        this.assignments = assignments;
        this.descriptions = descriptions;
        this.dueDate = dueDate;
        this.userID = userID;
    }
    
    // Public Methods
    public void AddAssignment(String assignmentName, String description)
    {
        assignments.add(assignmentName);
        descriptions.add(description);
    }
    
    // Accessors
    @DynamoDBAttribute(attributeName = "assignmentName")
    public List<String> GetAssignments()
    {
        return assignments;
    }
    
    @DynamoDBAttribute(attributeName = "description")
    public List<String> GetDescriptions()
    {
        return descriptions;
    }
    
    @DynamoDBRangeKey(attributeName = "dueDate")
    public String GetDueDate()
    {
        return dueDate;
    }
    
    @DynamoDBHashKey(attributeName = "userId")
    public String GetUserID()
    {
        return userID;
    }
    
    // Mutators
    public void SetAssignments(List<String> assignments)
    {
        this.assignments = assignments;
    }

    public void SetDescriptions(List<String> descriptions)
    {
        this.descriptions = descriptions;
    }
    
    public void SetDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }
    
    public void SetUserID(String userID)
    {
        this.userID = userID;
    }
}