package com.example.workapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recruit3 implements Parcelable {
    private String title;
    private String pay;
    private String date;
    private String area;
    private String rccontent;

    public Recruit3(Parcel parcel){
        this.title = parcel.readString();
        this.pay = parcel.readString();
        this.date = parcel.readString();
        this.area = parcel.readString();
        this.rccontent = parcel.readString();
    }

    public Recruit3(String title, String pay, String date, String area, String rccontent){
        this.title = title;
        this.pay = pay;
        this.date = date;
        this.area = area;
        this.rccontent = rccontent;
    }

    public Recruit3(){
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPay() { return pay;}
    public void setPay(String pay) { this.pay = pay;}

    public String getDate() { return date;}
    public void setDate(String date) { this.date = date;}

    public String getArea() { return area;}
    public void setArea(String area) { this.area = area;}

    public String getRcContent() { return rccontent;}
    public void setRcContent(String rccontent) { this.rccontent = rccontent;}

    public static final Parcelable.Creator<Recruit3> CREATOR = new Parcelable.Creator<Recruit3>(){
        @Override
        public Recruit3 createFromParcel(Parcel parcel){
            return new Recruit3(parcel);
        }
        @Override
        public Recruit3[] newArray(int size){
            return new Recruit3[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(pay);
        parcel.writeString(date);
        parcel.writeString(area);
        parcel.writeString(rccontent);
    }
}
