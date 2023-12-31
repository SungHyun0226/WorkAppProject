package com.example.workapp.viewmodel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.workapp.LoginManager;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.NoticeData;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.data.model.Recruit2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class NoticeViewModel extends ViewModel {
    private MutableLiveData<List<NoticeData>> _participantStatusListData = new MutableLiveData<List<NoticeData>>();
    public LiveData<List<NoticeData>> participantStatusListData = _participantStatusListData;

    public void getStatus() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Notice");

        myRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                GenericTypeIndicator<HashMap<String, NoticeData>> t = new GenericTypeIndicator<HashMap<String, NoticeData>>() {

                };
                HashMap<String, NoticeData> map = task.getResult().getValue(t);
                List<String> keyList = new ArrayList<>(map.keySet());
                ArrayList<NoticeData> items = new ArrayList<>();
                keyList.sort((s1, s2) -> s1.compareTo(s2));
                for (int i = 0; i < keyList.size(); i++) {
                    //items.add(map.get(keyList.get(i)));
                    String curKey = keyList.get(i);
                    NoticeData rec = map.get(curKey);
                    rec.setKey(curKey);
                    items.add(rec);
                }
                _participantStatusListData.setValue(items);
            }
        });
    }
}
