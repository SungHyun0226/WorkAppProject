package com.example.workapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workapp.LoginManager;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.InnerParticipant;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.data.model.Recruit;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.HashMap;

public class RecruitViewModel extends ViewModel {

    private MutableLiveData<Boolean> _isUpdateRecruit = new MutableLiveData<>();
    public LiveData<Boolean> isUpdateRecruit = _isUpdateRecruit;

    public void applyRecruit(Recruit rec) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference recruitRef = database.getReference("Users").child("Company")
                .child(rec.getCompanyId()).child("recruitList").child(rec.getId());


        recruitRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {

                GenericTypeIndicator<Recruit> t = new GenericTypeIndicator<Recruit>() {

                };

                HashMap<String, InnerParticipant> map;
                map = task.getResult().getValue(t).getParticipants();
                if (map == null) {
                    map = new HashMap<>();
                }

                /*
                    지운다면
                    map.remove("userID");
                    map.put("participants", map);
                    recruitRef.updateChildren(map);
                 */


                InnerParticipant pushData = new InnerParticipant();
                pushData.setStatus(0);
                pushData.setId(LoginManager.getInstance().userId);
                pushData.setPassword(LoginManager.getInstance().getPassword());
                pushData.setAddress(LoginManager.getInstance().getAddress());
                pushData.setName(LoginManager.getInstance().getName());
                pushData.setPhone(LoginManager.getInstance().getPhone());
                pushData.setGender(LoginManager.getInstance().getGender());
                pushData.setBirth(LoginManager.getInstance().getBirth());
                pushData.setRank(LoginManager.getInstance().getRank());

                map.put(pushData.getId(), pushData);

                HashMap<String, Object> pushMap = new HashMap<String, Object>();
                pushMap.put("participants", map);
                recruitRef.updateChildren(pushMap);
            }
        });

        DatabaseReference participantRef = database.getReference("Users").child("Participant").child(LoginManager.getInstance().getUserId()).child("recruitList");


        HashMap<String, Object> pushMap = new HashMap<String, Object>();
        rec.setParticipants(null);
        rec.setStatus(0);
        pushMap.put(rec.getId(), rec);
        participantRef.updateChildren(pushMap);

        _isUpdateRecruit.setValue(true);

        //participantRef.child("공고key값").removeValue();
    }

}
