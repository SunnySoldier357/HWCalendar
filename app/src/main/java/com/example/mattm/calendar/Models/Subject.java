package com.example.mattm.calendar.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "calendar-mobilehub-934323895-Classes")

public class Subject {
    private String teacherName;
    private String period;
    private String subject;
    @DynamoDBHashKey(attributeName = "teacher")
    @DynamoDBAttribute(attributeName = "teacher")
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    @DynamoDBRangeKey(attributeName = "period")
    @DynamoDBAttribute(attributeName = "period")
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
    @DynamoDBAttribute(attributeName = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String toString(){
        return this.teacherName + "_" + this.period + "_" + this.subject;
    }
}
