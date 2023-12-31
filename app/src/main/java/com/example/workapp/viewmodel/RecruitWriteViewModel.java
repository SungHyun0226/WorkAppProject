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
import java.util.HashMap;
import java.util.List;

public class RecruitWriteViewModel extends ViewModel {

    private MutableLiveData<Boolean> _isUpdateRecruit = new MutableLiveData<>();
    public LiveData<Boolean> isUpdateRecruit = _isUpdateRecruit;

    public void writeRecruit(Recruit rec) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Company").child(rec.getId());

        myRef.get().addOnCompleteListener(task -> {
             if (!task.isSuccessful()) {
                 Log.e("firebase", "Error getting data", task.getException());
             } else {

                 CompanyData cd = task.getResult().getValue(CompanyData.class);

                 Integer size = 0;

                 if (cd.getRecruitList() != null ) {
                     size = cd.getRecruitList().size();
                 }

                 //키값을 직접 세팅해서 넣는 방법
                 HashMap<String, Object> pushMap = new HashMap<String, Object>();
                 HashMap<String, Recruit> pushRec = new HashMap<>();

                 if (cd.getRecruitList() != null) {
                     pushRec = cd.getRecruitList();
                 }

                 rec.setId(rec.getId() + size);

                 pushRec.put(rec.getId(), rec);
                 pushMap.put("recruitList", pushRec);
                 myRef.updateChildren(pushMap);

                 _isUpdateRecruit.setValue(true);
             }
         });

    }

}
