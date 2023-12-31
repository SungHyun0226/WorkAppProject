package com.example.workapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InnerParticipant implements Parcelable {
    private String address;
    private String id;
    private String password;
    private String name;
    private String phone;
    private String birth;
    private String gender;
    private int rank;
    private int status;

    public InnerParticipant() {
    }

    protected InnerParticipant(Parcel in) {
        address = in.readString();
        id = in.readString();
        password = in.readString();
        name = in.readString();
        phone = in.readString();
        birth = in.readString();
        gender = in.readString();
        rank = in.readInt();
        status = in.readInt();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static final Creator<InnerParticipant> CREATOR = new Creator<InnerParticipant>() {
        @Override
        public InnerParticipant createFromParcel(Parcel in) {
            return new InnerParticipant(in);
        }

        @Override
        public InnerParticipant[] newArray(int size) {
            return new InnerParticipant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(id);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(birth);
        parcel.writeString(gender);
        parcel.writeInt(rank);
        parcel.writeInt(status);
    }
}
