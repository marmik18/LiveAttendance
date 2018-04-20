package com.example.marmikthakkar.liveattendance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

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
        holder.subjectNameTextView.setText(attendance.getSubjectName());
        holder.facultyNameTextView.setText(attendance.getFacultyName());
        holder.percentTextView.setText(String.valueOf(attendance.getAttendancePercent())+"%");
        new DownLoadImageTask(holder.imgIcon).execute(attendance.getIcon());
//        holder.imgIcon.setImageResource(attendance.getIcon());

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

class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
    ImageView imageView;

    public DownLoadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}


