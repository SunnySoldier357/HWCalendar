package com.example.mattm.calendar;

public class Teacher extends TableData
{
    private String className;
    private String teacherName;
    private String period;

    public Teacher(String className, String teacherName, String period)
    {
        this.className = className;
        this.teacherName = teacherName;
        this.period = period;
    }
}
