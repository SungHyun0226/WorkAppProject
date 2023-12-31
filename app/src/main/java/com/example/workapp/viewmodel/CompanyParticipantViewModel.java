package com.example.workapp.viewmodel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.Recruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyParticipantViewModel extends ViewModel {

    private MutableLiveData<List<Recruit>> _recruitListData = new MutableLiveData<>();
    public LiveData<List<Recruit>> recruitListData = _recruitListData;

    public void getParticipantList(String userId) {

        if (userId == null) return;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Company").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, CompanyData>> t = new GenericTypeIndicator<HashMap<String, CompanyData>>() {

                };

                CompanyData cd = snapshot.getValue(CompanyData.class);
                ArrayList<Recruit> items = new ArrayList<>();
                items.addAll(cd.getRecruitList().values());

                _recruitListData.setValue(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("test", "onCancelled");
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}