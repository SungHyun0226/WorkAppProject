package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workapp.R;
import com.example.workapp.data.model.NoticeData;

public class NoticeDetailActivity extends AppCompatActivity {

    TextView ntitle, ndate, ncontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Intent intent = getIntent();
        NoticeData noticeData = null;
        if (intent.getExtras() != null) {
            noticeData = (NoticeData) intent.getSerializableExtra("notice_data");
        }

        ntitle = findViewById(R.id.ntitle);
        ndate = findViewById(R.id.ndate);
        ncontent = findViewById(R.id.ncontent);

        if (noticeData != null) {
            ntitle.setText(noticeData.getTitle());
            ncontent.setText(noticeData.getContent());
            ndate.setText(noticeData.getDate());
        }
    }
}