package com.example.mattm.calendar.Models;

import java.sql.Timestamp;

public class Assignment {
    private Timestamp dueDate;
    private String assignmentName;
    private String subject;
    private String description;

    public Assignment(Timestamp dueDate, String assignmentName, String subject, String description) {
        this.dueDate = dueDate;
        this.assignmentName = assignmentName;
        this.subject = subject;
        this.description = description;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
