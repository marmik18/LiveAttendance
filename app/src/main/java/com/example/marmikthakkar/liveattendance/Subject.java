package com.example.marmikthakkar.liveattendance;

import android.os.Parcel;
import android.os.Parcelable;

public class Subject implements Parcelable {

    private String imgURL;
    private String name;
    private String faculty;

    Subject(){}

    Subject(String imgURL, String name, String faculty){
        this.name = name;
        this.faculty = faculty;
        this.imgURL = imgURL;
    }

    public Subject(Parcel in){
        String[] data= new String[3];

        in.readStringArray(data);
        this.imgURL = data[0];
        this.name = data[1];
        this.faculty = data[2];
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getImgURL() {
        return imgURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name, this.faculty, this.imgURL});
    }

    public static final Parcelable.Creator<Subject> CREATOR= new Parcelable.Creator<Subject>() {

        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);  //using parcelable constructor
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

}