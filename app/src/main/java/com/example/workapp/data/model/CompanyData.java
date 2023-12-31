package com.example.workapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class CompanyData implements Parcelable {

    private String id = "";
    private String password = "";
    private String companyName = "";
    private String ceo = "";
    private String phone = "";
    private String address = "";

    private HashMap<String, Recruit> recruitList;

    public CompanyData(String id, String password, String companyName, String ceo, String phone, String address) {
        this.id = id;
        this.password = password;
        this.companyName = companyName;
        this.ceo = ceo;
        this.phone = phone;
        this.address = address;
    }

    public CompanyData() {

    }

    public HashMap<String, Recruit> getRecruitList() {
        return recruitList;
    }

    public void setRecruitList(HashMap<String, Recruit> recruitList) {
        this.recruitList = recruitList;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static final Creator<CompanyData> CREATOR = new Creator<CompanyData>() {
        @Override
        public CompanyData createFromParcel(Parcel in) {
            return new CompanyData(in);
        }

        @Override
        public CompanyData[] newArray(int size) {
            return new CompanyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(password);
        parcel.writeString(companyName);
        parcel.writeString(ceo);
        parcel.writeString(phone);
        parcel.writeString(address);
    }

    protected CompanyData(Parcel in) {
        id = in.readString();
        password = in.readString();
        companyName = in.readString();
        ceo = in.readString();
        phone = in.readString();
        address = in.readString();
    }
}


