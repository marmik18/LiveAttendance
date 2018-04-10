package com.example.marmikthakkar.liveattendance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttendanceArrayAdapter extends ArrayAdapter<Attendance>{
    Context context;
    int layoutResourceId;
    Attendance data[] = null;

    public AttendanceArrayAdapter(Context context, int layoutResourceId, Attendance data[]) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AttendanceHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AttendanceHolder();
            holder.imgIcon = row.findViewById(R.id.icon);
            holder.facultyNameTextView = row.findViewById(R.id.facultyName);
            holder.subjectNameTextView = row.findViewById(R.id.subjectName);
            holder.percentTextView = row.findViewById(R.id.attendancePercent);

            row.setTag(holder);
        }
        else
        {
            holder = (AttendanceHolder) row.getTag();
        }

        Attendance attendance = data[position];
        holder.subjectNameTextView.setText(attendance.subjectName);
        holder.facultyNameTextView.setText(attendance.facultyName);
        holder.percentTextView.setText(String.valueOf(attendance.attendancePercent)+"%");
        holder.imgIcon.setImageResource(attendance.icon);

        return row;
    }

    static class AttendanceHolder
    {
        ImageView imgIcon;
        TextView subjectNameTextView;
        TextView facultyNameTextView;
        TextView percentTextView;
    }
}

