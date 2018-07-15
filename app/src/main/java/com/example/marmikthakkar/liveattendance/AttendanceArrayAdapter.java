package com.example.marmikthakkar.liveattendance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import com.example.marmikthakkar.liveattendance.animation.*;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            holder.progressBar = row.findViewById(R.id.horizontal_progress_bar);
            holder.lectureCount = row.findViewById(R.id.lectureCount);

            row.setTag(holder);
        }
        else
        {
            holder = (AttendanceHolder) row.getTag();
        }

        Attendance attendance = data[position];
        final AttendanceHolder finalHolder = holder;

        holder.subjectNameTextView.setText(attendance.getSubjectName());
        holder.facultyNameTextView.setText(attendance.getFacultyName());
        holder.percentTextView.setText(String.valueOf(attendance.getAttendancePercent())+"%");
        holder.progressBar.setMax(attendance.getHoursOccurred());
//        ProgressBarAnimation anim = new ProgressBarAnimation(holder.progressBar, 0, attendance.getHoursAttended());
//        anim.setDuration(1000);
//        holder.progressBar.startAnimation(anim);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.progressBar.setProgress(attendance.getHoursAttended(), true);
        }
        holder.lectureCount.setText(attendance.getHoursAttended()+"/"+attendance.getHoursOccurred());



        Picasso.get()
                .load(getItem(position).getIcon())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imgIcon, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(getItem(position).getIcon())
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_background)
                                .into(finalHolder.imgIcon);
                    }
                });
        return row;
    }

    static class AttendanceHolder
    {
        ImageView imgIcon;
        TextView subjectNameTextView;
        TextView facultyNameTextView;
        TextView percentTextView;
        TextView lectureCount;
        ProgressBar progressBar;
    }
}



