package com.example.workapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.Recruit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class JobListViewModel extends ViewModel {

    private MutableLiveData<List<Recruit>> _recruitListData = new MutableLiveData<List<Recruit>>();
    public LiveData<List<Recruit>> recruitListData = _recruitListData;

    public void getJobList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Company");

        myRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                GenericTypeIndicator<HashMap<String, CompanyData>> t = new GenericTypeIndicator<HashMap<String, CompanyData>>() {

                };

                HashMap<String, CompanyData> map = task.getResult().getValue(t);
                ArrayList<Recruit> items = new ArrayList<>();
                List<String> keyList = new ArrayList<>(map.keySet());

                for (int i = 0; i < keyList.size(); i++) {
                    String curKey = keyList.get(i);
                    if (map.get(curKey).getRecruitList() != null) {

                        Collection<Recruit> cd = map.get(curKey).getRecruitList().values();
                        cd.forEach(new Consumer<Recruit>() {
                            @Override
                            public void accept(Recruit recruit) {
                                recruit.setCompanyName(map.get(curKey).getCompanyName());
                                recruit.setCompanyId(map.get(curKey).getId());
                            }
                        });

                        items.addAll(cd);
                    }
                }

                _recruitListData.setValue(items);
            }
        });
    }

}
