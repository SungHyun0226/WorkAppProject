package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.adapter.MyRecruitAdapter;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.data.model.Recruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ParticipantStatusActivity extends AppCompatActivity {
    private ArrayList<Recruit> mLstRecruit;
    private DatabaseReference mDatabaseRef;
    private MyRecruitAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_status);

        String idValue = "";
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            idValue = intent.getStringExtra("ID");
        }

        mLstRecruit = new ArrayList<>();
        RecyclerView rvMyRecruit = findViewById(R.id.recruit_rcview3);
        rvMyRecruit.setHasFixedSize(true);
        mAdapter = new MyRecruitAdapter();
        mAdapter.setIdValue(idValue);
        rvMyRecruit.setAdapter(mAdapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child("Participant").child(idValue);
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ParticipantData participantData = snapshot.getValue(ParticipantData.class);
                if (participantData != null) {
                    ArrayList<Recruit> lstConfirmRecruit = new ArrayList<>(); // 지원한 공고가 채용 확정된 지원 공고 리스트

                    HashMap<String, Recruit> map = participantData.getRecruitList();
                    if (map != null) {
                        for (Map.Entry<String, Recruit> item : map.entrySet()) {
                            if (item != null) {
                                Recruit recruit = item.getValue();
                                if (recruit.getStatus() == 1) {
                                    // 기업에서 채용버튼 누른 상황이기에 가장 우선순위로 정렬해주기 위해 다른 리스트에 따로 담아둔다
                                    lstConfirmRecruit.add(recruit);
                                    continue;
                                }
                                mLstRecruit.add(recruit);
                            }
                        }
                        // 구직자 본인이 채용된 공고 목록 맨 위에 띄워야하기에 리스트에 역순으로 add all
                        if (lstConfirmRecruit.size() != 0) {
                            mLstRecruit.addAll(0, lstConfirmRecruit);
                            lstConfirmRecruit.clear();
                        }
                    }

                    mAdapter.setListInit(mLstRecruit);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase DB Error!", error.getMessage());
            }
        });

    }
}
