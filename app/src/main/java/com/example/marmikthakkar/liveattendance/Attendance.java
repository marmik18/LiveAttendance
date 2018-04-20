package com.example.marmikthakkar.liveattendance;


public class Attendance {
    private String icon;
    private String subjectName;
    private String facultyName;
    private float attendancePercent;

    Attendance(){
        super();
    }

    Attendance(String icon, String subjectName, String facultyName, float attendancePercent){
        super();
        this.icon = icon;
        this.subjectName = subjectName;
        this.facultyName = facultyName;
        this.attendancePercent = attendancePercent;
    }

    public float getAttendancePercent() {
        return attendancePercent;
    }

    public String getIcon() {
        return icon;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getSubjectName() {
        return subjectName;
    }

}
