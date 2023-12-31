package com.example.workapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.workapp.LoginManager;
import com.example.workapp.R;
import com.example.workapp.data.model.InnerParticipant;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.data.model.Recruit3;

import java.util.ArrayList;
import java.util.HashMap;

public class CompanyParticipantActivity2 extends AppCompatActivity implements View.OnClickListener {

    private CompanyParticipantAdapter2 mAdapter;
    private RecyclerView mRecruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_participant2);

        HashMap<String, InnerParticipant> list = (HashMap<String, InnerParticipant>) getIntent().getSerializableExtra("recruit");
        String date = getIntent().getStringExtra("date");
        String title = getIntent().getStringExtra("title");
        String recId = getIntent().getStringExtra("recId");
        String address = getIntent().getStringExtra("address");

        findViewById(R.id.btn_filter_address).setOnClickListener(this);
        findViewById(R.id.btn_filter_birth).setOnClickListener(this);
        findViewById(R.id.btn_filter_gender).setOnClickListener(this);
        findViewById(R.id.btn_filter_status).setOnClickListener(this);


        RadioGroup rgReport = findViewById(R.id.rg_report);
        rgReport.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_report_1) {
                    // 지원자 현황
                    mAdapter.setChangeList(true);
                    mAdapter.notifyDataSetChanged();
                } else {
                    // 채용자 현황
                    mAdapter.setChangeList(false);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        mRecruits = (RecyclerView) findViewById(R.id.participantRecyclerView);
        mAdapter = new CompanyParticipantAdapter2(recId, LoginManager.getInstance().getUserId());

        mRecruits.setAdapter(mAdapter);
        mRecruits.setLayoutManager(new LinearLayoutManager(this));

        if (list != null) {
            mAdapter.setParticipantDataListData(new ArrayList<>(list.values()));
            mAdapter.setChangeList(true);
        }

        TextView dateTextView = findViewById(R.id.tv_date);
        dateTextView.setText(date);

        TextView titleTextView = findViewById(R.id.tv_recruitTitle);
        titleTextView.setText(title);

        TextView addressTextView = findViewById(R.id.tv_address);
        addressTextView.setText(address);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_filter_gender:
                // 성별
                mAdapter.setFilterMode(0);
                break;

            case R.id.btn_filter_birth:
                // 나이
                mAdapter.setFilterMode(1);
                break;

            case R.id.btn_filter_address:
                // 주소
                mAdapter.setFilterMode(2);
                break;

            case R.id.btn_filter_status:
                // 경력
                mAdapter.setFilterMode(3);
                break;
        }
    }
}