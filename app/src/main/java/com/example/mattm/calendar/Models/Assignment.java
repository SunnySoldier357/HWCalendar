package com.example.mattm.calendar.Models;

public class Assignment {
    private int dueDate; // NOTE: THIS SHOULD LATER BE TURNED INTO TIMESTAMP FORMAT
    private String assignmentName;
    private String subject;

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
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
}
