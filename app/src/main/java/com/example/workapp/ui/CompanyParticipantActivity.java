package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.viewmodel.CompanyParticipantViewModel;
import com.example.workapp.viewmodel.JobListViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CompanyParticipantActivity extends AppCompatActivity {

    private CompanyParticipantAdapter mAdapter;
    private RecyclerView mRecruits;

    private CompanyParticipantViewModel vm; //뷰 모델 불러오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_participant);

        String userId = getIntent().getExtras().getString("ID");

        mRecruits = findViewById(R.id.recruit_rcview_CPA);

        vm = new ViewModelProvider(this).get(CompanyParticipantViewModel.class);

        vm.recruitListData.observe(this, recruitListView -> {
            //liveData의 변경 값에 따라 뷰의 상태 변경
            mAdapter.setRecruitListData((ArrayList<Recruit>) recruitListView);
        });

        mAdapter = new CompanyParticipantAdapter();
        mAdapter.setUserId(userId);

        mAdapter.setOnItemClickListener(new CompanyParticipantAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view,Recruit rec) {
                Intent myIntent = new Intent(CompanyParticipantActivity.this, CompanyParticipantActivity2.class);
                myIntent.putExtra("title", rec.getTitle());
                myIntent.putExtra("date", rec.getDate());
                myIntent.putExtra("recruit", rec.getParticipants());
                myIntent.putExtra("recId", rec.getId());
                myIntent.putExtra("address",rec.getAddress());
                startActivity(myIntent);
            }
        });
        EditText recruitSearch = findViewById(R.id.et_recruitsearch_CPA);
        recruitSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("edittextdebugging", s.toString());
                String searchContent = s.toString();
//                RecruitList4.clear();
//                RecruitList3.forEach(item -> {
//                    if (item.getTitle().contains(searchContent)) {
//                        RecruitList4.add(item);
//                    }
//                });
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mRecruits.setAdapter(mAdapter);
        mRecruits.setLayoutManager(new LinearLayoutManager(this));

        vm.getParticipantList(userId);
    }
}