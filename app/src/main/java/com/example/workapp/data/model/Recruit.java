package com.example.workapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Recruit implements Parcelable {
    private String id;
    private String companyName;
    private String title;
    private Long pay;
    private String date;
    private String address;
    private String content;
    private String rcContent;
    private String lastDate;
    private String job;
    private String companyId;
    private int status;
    private HashMap<String, InnerParticipant> participants;

    public Recruit() {
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Recruit(Parcel parcel){
        this.id = parcel.readString();
        this.companyName = parcel.readString();
        this.title = parcel.readString();
        this.pay = parcel.readLong();
        this.date = parcel.readString();
        this.address = parcel.readString();
        this.content = parcel.readString();
        this.rcContent = parcel.readString();
        this.lastDate = parcel.readString();
        this.job = parcel.readString();
        this.companyId = parcel.readString();
        this.participants = parcel.readHashMap(InnerParticipant.class.getClassLoader());
    }

    public String getCompanyName(){
        return companyName;
    }

    public HashMap<String, InnerParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(HashMap<String, InnerParticipant> participants) {
        this.participants = participants;
    }

    public void  setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPay() { return pay;}

    public void setPay(Long pay) { this.pay = pay;}

    public String getDate() { return date;}
    public void setDate(String date) { this.date = date;}

    public String getAddress() { return address;}
    public void setAddress(String address) { this.address = address;}

    public String getRcContent() {
        return rcContent;
    }

    public void setRcContent(String rcContent) {
        this.rcContent = rcContent;
    }

    public String getLastDate() { return lastDate;}
    public void setLastDate(String lastDate) { this.lastDate = lastDate;}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }




    public static final Parcelable.Creator<Recruit> CREATOR = new Parcelable.Creator<Recruit>(){
        @Override
        public Recruit createFromParcel(Parcel parcel){
            return new Recruit(parcel);
        }
        @Override
        public Recruit[] newArray(int size){
            return new Recruit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(companyName);
        parcel.writeString(title);
        parcel.writeLong(pay);
        parcel.writeString(date);
        parcel.writeString(address);
        parcel.writeString(content);
        parcel.writeString(rcContent);
        parcel.writeString(lastDate);
        parcel.writeString(job);
        parcel.writeString(companyId);
        parcel.writeMap(participants);
    }
}