package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.adapter.RecruitListAdapter;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.viewmodel.JobListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobListActivity extends AppCompatActivity {
    private RecruitListAdapter mAdapter;
    private RecyclerView mRecruits; //RecyclerView 에 ListAdapter 에서 정의한 mRecruits 를 넣는것
    private JobListViewModel vm; //뷰 모델 불러오기

    private Spinner spinnerAddress, spinnerJobs;
    private ArrayList<Recruit> mLstRecruit;
    private String currentSelectAddress, currentSelectJob = "전체"; // 현재 스피너로 선택 중인 주소, 직종
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joblist);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child("Company");

        spinnerAddress = findViewById(R.id.spinner_address);
        spinnerJobs = findViewById(R.id.spinner_job);
        mLstRecruit = new ArrayList<>();

        // 주소 스피너
        spinnerAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelectAddress = adapterView.getSelectedItem().toString();
                setModifyListFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 직종 스피너
        spinnerJobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelectJob = adapterView.getSelectedItem().toString();
                setModifyListFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent myintent = getIntent();
        int Value = myintent.getExtras().getInt("Value");

        Button button4 = findViewById(R.id.button4);
        mRecruits = findViewById(R.id.recruit_rcview);

        if (Value == 1) {
            button4.setVisibility(View.VISIBLE);
            button4.setEnabled(true);
        } else {
            button4.setVisibility(View.INVISIBLE);
            button4.setEnabled(false);
        } // 여기까지는 들어온 값에 따라 구인공고에 공고 작성 버튼 on off 하는 기능

//        vm = new ViewModelProvider(this).get(JobListViewModel.class);
//        initView();
//        vm.recruitListData.observe(this, recruitListView -> {
//            //liveData의 변경 값에 따라 뷰의 상태 변경
//            mAdapter.setRecruitListData((ArrayList<Recruit>) recruitListView);
//        });

        //화면이 열리면 필요한 데이터 가져오는 함수
//        vm.getJobList();

        mAdapter = new RecruitListAdapter();
        mAdapter.setOnItemClickListener(new RecruitListAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, Recruit rec) {
                Intent myIntent = new Intent(JobListActivity.this, RecruitActivity.class);
                if (Value == 1) {
                    myIntent.putExtra("Value", 1);
                } else if (Value == 2) {
                    myIntent.putExtra("Value", 2);
                } else if (Value == 3) {
                    myIntent.putExtra("Value", 1);
                }
                myIntent.putExtra("Recruit", rec);
                startActivity(myIntent);
            }

        }); // 해당 공고를 누르면 상세보기로 이동하는 코드

        mRecruits.setAdapter(mAdapter);

        setModifyListFilter();

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(JobListActivity.this, RecruitWriteActivity.class);
                String IdValue = myintent.getExtras().getString("ID");
                myIntent.putExtra("ID", IdValue);
                startActivity(myIntent);
            }
        });
    }

    private void setModifyListFilter() {
        // 주소, 직종 필터 리스트를 추려서 다시 리사이클러뷰를 update 하는 함수이다
        mLstRecruit.clear();
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CompanyData companyData = ds.getValue(CompanyData.class);
                    if (companyData != null) {
                        HashMap<String, Recruit> recruitMap = companyData.getRecruitList();
                        for (Map.Entry<String, Recruit> item : recruitMap.entrySet()) {
                            Recruit recruit = item.getValue();
                            recruit.setCompanyName(companyData.getCompanyName());
                            recruit.setCompanyId(companyData.getId());
                            if (currentSelectAddress.equals("전체") && currentSelectJob.equals("전체")) {
                                mLstRecruit.add(recruit);
                                continue;
                            }

                            if (currentSelectAddress.equals("서울 전지역")) {
                                if (recruit.getAddress().contains("서울")) {
                                    mLstRecruit.add(recruit);
                                    continue;
                                }
                            } else if (currentSelectAddress.equals("경기 전지역")) {
                                if (recruit.getAddress().contains("경기")) {
                                    mLstRecruit.add(recruit);
                                    continue;
                                }
                            } else {
                                if (currentSelectAddress.equals(recruit.getAddress())) {
                                    mLstRecruit.add(recruit);
                                    continue;
                                }
                            }
                        }
                    }
                }

                // 2차로 직종을 체크하여 걸러내기 위한 별도 리스트 생성
                ArrayList<Recruit> lstFinalRecruit = new ArrayList<>();
                if (mLstRecruit.size() != 0) {
                    if (currentSelectJob.equals("전체")) {
                        mAdapter.setListInit(mLstRecruit);
                        mAdapter.notifyDataSetChanged();
                        return;
                    }

                    for (Recruit item : mLstRecruit) {
                        if (currentSelectJob.equals(item.getJob())) {
                            lstFinalRecruit.add(item);
                        }
                    }

                } else {
                    Toast.makeText(JobListActivity.this, "조회 결과가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                }
                mAdapter.setListInit(lstFinalRecruit);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase DB Error!", error.getMessage());
            }
        });
    }

    private void initView() {
       /* EditText recruitSearch = findViewById(R.id.et_recruitsearch);

        recruitSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("edittextdebugging", s.toString());
                String searchContent = s.toString();
                RecruitList2.clear();
                RecruitList.forEach(item -> {
                    if (item.getTitle().contains(searchContent)) {
                        RecruitList2.add(item);
                    }
                });
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
         */
    }

}