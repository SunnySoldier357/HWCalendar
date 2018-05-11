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
    private List<String> enrolledStudents;
    
    private String period;
    private String subject;
    private String teacherName;
    
    // Constructors
    public Subject()
    {
        this("", "", "", new ArrayList<String>());
    }
    
    public Subject(String subject, String teacherName, String period)
    {
        this(subject, teacherName, period, new ArrayList<String>());
    }
    
    public Subject(String subject, String teacherName, String period, ArrayList<String> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
        this.period = period;
        this.subject = subject;
        this.teacherName = teacherName;
    }
    
    // Accessors
    @DynamoDBAttribute(attributeName = "enrolledStudents")
    public List<String> getEnrolledStudents()
    {
        return enrolledStudents;
    }
    
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
    
    // Mutators
    public void setEnrolledStudents(List<String> enrolledStudents)
    {
        this.enrolledStudents = enrolledStudents;
    }
    
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

    // Overridden Methods
    @Override
    public String toString()
    {
        return String.format("%s_%s_%s", getTeacherName(), getPeriod(), getSubject());
    }
}