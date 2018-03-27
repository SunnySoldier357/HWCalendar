package com.example.mattm.calendar;

public class Teacher extends MainActivity
{
    public String className;
    public String teacherName;
    public String period;

    public Teacher(String className, String teacherName, String period) {
        this.className = className;
        this.teacherName = teacherName;
        this.period = period;

        //buildList(className);
        periods.add(className);
    }

    @Override
    public String toString() {
        return className;
    }
}
