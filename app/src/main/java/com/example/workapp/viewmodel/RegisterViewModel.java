package com.example.workapp.viewmodel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.ParticipantData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    public void registerParticipant(ParticipantData pd) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Participant");

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(pd.getId(), pd);
        myRef.updateChildren(map);
    }


    public void registerCompany(CompanyData cd) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Company");

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(cd.getId(), cd);
        myRef.updateChildren(map);
    }

}