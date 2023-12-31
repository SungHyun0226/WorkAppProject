package com.example.workapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.workapp.R;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.data.model.Recruit2;

public class ParticipantStatus2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_status2);

        Recruit recruit = getIntent().getParcelableExtra("recruit");

        TextView textView = findViewById(R.id.tv_companyname);
        textView.setText(recruit.getCompanyName());

        TextView titleTextView = findViewById(R.id.tv_recruitTitle);
        titleTextView.setText(recruit.getTitle());

        TextView payTextView = findViewById(R.id.tv_pay);
        payTextView.setText("일급: "+ recruit.getPay());

        TextView dateTextView = findViewById(R.id.tv_date);
        dateTextView.setText(recruit.getDate());

        TextView areaTextView = findViewById(R.id.tv_area);
        areaTextView.setText(recruit.getAddress());

        TextView contentTextView = findViewById(R.id.tv_content);
        contentTextView.setText(recruit.getRcContent());

        TextView closeTextView = findViewById(R.id.tv_closingDate);
        closeTextView.setText(recruit.getLastDate());
    }
}