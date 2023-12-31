package com.example.workapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workapp.data.model.Recruit2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantStatusViewModel extends ViewModel {
    private MutableLiveData<List<Recruit2>> _participantStatusListData = new MutableLiveData<List<Recruit2>>();
    public LiveData<List<Recruit2>> participantStatusListData = _participantStatusListData;

    public void getStatus() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("JobList");

        myRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                GenericTypeIndicator<HashMap<String, Recruit2>> t = new GenericTypeIndicator<HashMap<String, Recruit2>>() {

                };
                HashMap<String, Recruit2> map = task.getResult().getValue(t);
                List<String> keyList = new ArrayList<>(map.keySet());
                ArrayList<Recruit2> items = new ArrayList<>();
                keyList.sort((s1, s2) -> s1.compareTo(s2));
                for (int i = 0; i < keyList.size(); i++) {
                    //items.add(map.get(keyList.get(i)));
                    String curKey = keyList.get(i);
                    Recruit2 rec = map.get(curKey);
                    rec.setKey(curKey);
                    items.add(rec);
                }
                _participantStatusListData.setValue(items);
            }
        });
    }
}