package com.example.marmikthakkar.liveattendance;


public class Attendance {
    private String icon;
    private String subjectName;
    private String facultyName;
    private int hoursAttended;
    private int hoursOccurred;
    private float attendancePercent;

    Attendance(){
        super();
    }

    Attendance(String icon, String subjectName, String facultyName, float attendancePercent, int hoursAttended, int hoursOccurred){
        super();
        this.icon = icon;
        this.subjectName = subjectName;
        this.facultyName = facultyName;
        this.attendancePercent = attendancePercent;
        this.hoursAttended = hoursAttended;
        this.hoursOccurred = hoursOccurred;
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

    public int getHoursAttended() {
        return hoursAttended;
    }

    public int getHoursOccurred() {
        return hoursOccurred;
    }
}
