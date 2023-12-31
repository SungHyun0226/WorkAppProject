package com.example.workapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.workapp.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Button pa_management = (Button) findViewById(R.id.participant_management);
        pa_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminMainActivity.this, AdminParticipantActivity.class);
                startActivity(myIntent);
            }
        });
        Button cp_management = (Button) findViewById(R.id.company_management);
        cp_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminMainActivity.this, AdminCompanyActivity.class);
                startActivity(myIntent);
            }
        });
        Button jobList = (Button) findViewById(R.id.btn_jobList);
        jobList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminMainActivity.this, JobListActivity.class);
                myIntent.putExtra("Value", 3);
                startActivity(myIntent);
            }
        });
        Button notice = (Button) findViewById(R.id.P_notice);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminMainActivity.this, NoticeActivity.class);
                startActivity(myIntent);
            }
        });
    }
}