package com.example.marmikthakkar.liveattendance;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User implements Parcelable{
    private String uid;
    private String pass;
    private String type;
    private String name;

    public User(){

    }

    public User(String uid, String pass, String type, String name){
        this.uid = uid;
        this.pass = pass;
        this.type = type;
        this.name = name;
    }

    public User(Parcel in){
        String[] data= new String[4];

        in.readStringArray(data);
        this.uid= data[0];
        this.pass= data[1];
        this.type= data[2];
        this.name= data[3];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.uid, this.pass, this.type, this.name});
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



