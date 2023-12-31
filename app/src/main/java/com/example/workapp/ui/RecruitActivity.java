package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.workapp.R;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.viewmodel.RecruitViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecruitActivity extends AppCompatActivity {

    private RecruitViewModel vm; //뷰 모델 불러오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        Intent myintent = getIntent();
        Recruit rec = myintent.getParcelableExtra("Recruit");
        int Value = myintent.getExtras().getInt("Value");
        Button button = findViewById(R.id.button);

        TextView textView = findViewById(R.id.tv_companyname);
        textView.setText(rec.getCompanyName());
        TextView titleTextView = findViewById(R.id.tv_recruitTitle);
        titleTextView.setText(rec.getTitle());
        TextView areaTextView = findViewById(R.id.tv_area);
        areaTextView.setText(rec.getAddress());
        TextView payTextView = findViewById(R.id.tv_pay);
        payTextView.setText("일급 : " + rec.getPay());
        TextView dateTextView = findViewById(R.id.tv_date);
        dateTextView.setText(rec.getDate());
        TextView closingDateTextView = findViewById(R.id.tv_closingDate);
        closingDateTextView.setText(rec.getLastDate());
        TextView contentTextView = findViewById(R.id.tv_content);
        contentTextView.setText(rec.getRcContent());
        TextView jobNameTextView = findViewById(R.id.tv_job_name);
        jobNameTextView.setText(rec.getJob());

        vm = new ViewModelProvider(this).get(RecruitViewModel.class);
        vm.isUpdateRecruit.observe(this, isUpdated -> {
            // liveData의 변경 값에 따라 뷰의 상태 변경
            if (isUpdated)
                finish();
        });

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.applyRecruit(rec);
            }
        });
        if (Value == 2) {
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        } else if (Value == 1) {
            button.setVisibility(View.INVISIBLE);
            button.setEnabled(false);
        }
    }
}








