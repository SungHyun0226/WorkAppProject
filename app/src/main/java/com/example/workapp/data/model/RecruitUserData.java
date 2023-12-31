package com.example.workapp.data.model;

public class RecruitUserData extends MyPageData {
    private String id;
    private String password;
    private String name;
    private String birth;
    private String phoneNumber;
    private String address;

    public String getAddress() {
        return address;
    }

    public RecruitUserData(
            String id,
            String password,
            String name,
            String birth,
            String phoneNumber,
            String address
    ) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
