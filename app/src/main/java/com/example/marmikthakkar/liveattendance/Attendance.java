package com.example.marmikthakkar.liveattendance;


public class Attendance {
    public int icon;
    String subjectName;
    String facultyName;
    float attendancePercent;

    Attendance(){
        super();
    }

    Attendance(int icon, String subjectName, String facultyName, float attendancePercent){
        super();
        this.icon = icon;
        this.subjectName = subjectName;
        this.facultyName = facultyName;
        this.attendancePercent = attendancePercent;
    }

}
