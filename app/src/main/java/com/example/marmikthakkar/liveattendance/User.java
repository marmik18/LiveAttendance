package com.example.marmikthakkar.liveattendance;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User implements Parcelable{
    private String uid;
    private String pass;
    private String type;
    private String name;
    private String course;
    private String programme;
    private String sem;

    public User(){

    }

    public User(String uid, String pass, String type, String name, String course, String programme, String sem){
        this.uid = uid;
        this.pass = pass;
        this.type = type;
        this.name = name;
        this.course = course;
        this.programme = programme;
        this.sem = sem;
    }

    public User(Parcel in){
        String[] data= new String[7];

        in.readStringArray(data);
        this.uid = data[0];
        this.pass = data[1];
        this.type = data[2];
        this.name = data[3];
        this.course = data[4];
        this.programme = data[5];
        this.sem = data[6];
    }

    public String getUid() {
        return uid;
    }

    public String getPass() {
        return pass;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSem() {
        return sem;
    }

    public String getCourse() {
        return course;
    }

    public String getProgramme() {
        return programme;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.uid, this.pass, this.type, this.name, this.course, this.programme, this.sem});
    }

    public static final Parcelable.Creator<User> CREATOR= new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);  //using parcelable constructor
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}



