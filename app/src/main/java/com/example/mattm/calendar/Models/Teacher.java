package com.example.mattm.calendar.Models;

import com.example.mattm.calendar.Models.TableData;

public class Teacher extends TableData
{
    private String className;
    private String teacherName;
    private String period;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Teacher(String className, String teacherName, String period)
    {
        this.className = className;
        this.teacherName = teacherName;
        this.period = period;
    }
}
