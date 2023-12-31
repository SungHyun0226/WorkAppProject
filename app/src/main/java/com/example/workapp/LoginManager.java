package com.example.workapp;

import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.ParticipantData;

public class LoginManager {

    // Static variable reference of single_instance
    // of type Singleton
    private static LoginManager instance = null;

    // Declaring a variable of type String
    public String userId;
    public String password = "";
    public String phone = "";
    public String address = "";

    //회사만 필요한 정보
    public String companyName = "";
    public String ceo = "";

    //구직자만 필요한 정보
    private String name;
    private String birth;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    private Integer rank;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private Integer status;
    private String gender;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private LoginManager()
    {

    }

    // Static method
    // Static method to create instance of Singleton class
    public static LoginManager getInstance()
    {
        if (instance == null)
            instance = new LoginManager();

        return instance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCompanyData(CompanyData cd) {

        this.userId = cd.getId();
        this.ceo = cd.getCeo();
        this.address = cd.getAddress();
        this.companyName = cd.getCompanyName();
    }

    public void setParticipantData(ParticipantData pd) {
        this.userId = pd.getId();
        this.address = pd.getAddress();
        this.birth = pd.getBirth();
        this.gender = pd.getGender();
        this.name = pd.getName();
        this.phone = pd.getPhone();
        this.rank = pd.getRank();
    }

}
