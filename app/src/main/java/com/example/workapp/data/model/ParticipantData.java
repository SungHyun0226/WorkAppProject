package com.example.workapp.data.model;

import java.io.Serializable;
import java.util.HashMap;

public class ParticipantData implements Serializable {
    private String id;
    private String password;
    private String name;
    private String birth;
    private String phone;
    private String address;
    private int status;
    private String gender;
    private int rank;

    private HashMap<String, Recruit> recruitList;

    public ParticipantData() {

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

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public ParticipantData(String id, String pa, String name, String birth, String phone, String address, String gender, int rank) {
        this.id = id;
        this.password = pa;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.rank = rank;
    }
}
