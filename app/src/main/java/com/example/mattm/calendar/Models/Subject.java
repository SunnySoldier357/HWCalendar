package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-Classes")
public class Subject
{
    // Private Properties
    private String period;
    private String subject;
    private String teacherName;
    
    private List<String> enrolledStudents;
    
    // Constructors
    public Subject()
    {
        this("", "", "");
    }
    
    public Subject(String period, String subject, String teacherName)
    {
        this.period = period;
        this.subject = subject;
        this.teacherName = teacherName;
        enrolledStudents = new ArrayList<>();
    }
    
    // Accessors
    @DynamoDBRangeKey(attributeName = "period")
    @DynamoDBAttribute(attributeName = "period")
    public String getPeriod()
    {
        return period;
    }
    
    @DynamoDBAttribute(attributeName = "subject")
    public String getSubject()
    {
        return subject;
    }
    
    @DynamoDBHashKey(attributeName = "teacher")
    @DynamoDBAttribute(attributeName = "teacher")
    public String getTeacherName()
    {
        return teacherName;
    }

    @DynamoDBAttribute(attributeName = "enrolledStudents")
    public List<String> getEnrolledStudents()
    {
        return enrolledStudents;
    }
    
    // Mutators
    public void setPeriod(String period)
    {
        this.period = period;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }
    
    public void setEnrolledStudents(List<String> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
    }
    
    // Overridden Methods
    @Override
    public String toString()
    {
        return String.format("%s_%s_%s", getTeacherName(), getPeriod(), getSubject());
    }
}