package com.example.workapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workapp.LoginManager;
import com.example.workapp.R;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent myintent = getIntent();

        int Value = myintent.getExtras().getInt("Value");
        String IdValue = myintent.getExtras().getString("ID");
        TextView title = findViewById(R.id.participantmain_textview);
        if (Value == 1) {
            title.setText("기업 메인화면");
            Button button1 = (Button) findViewById(R.id.P_mypage);
            button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, MyPageActivity.class);
                myIntent.putExtra("Value", 1);
                myIntent.putExtra("ID", LoginManager.getInstance().userId);
                startActivity(myIntent);
        }}
            );
        Button button2 = (Button) findViewById(R.id.P_joblist);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, JobListActivity.class);
                myIntent.putExtra("Value", 1);
                myIntent.putExtra("ID",IdValue);
                startActivity(myIntent);
            }
        });

        Button button3 = (Button) findViewById(R.id.P_participant);
        button3.setText("지원자 현황");
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, CompanyParticipantActivity.class);
                myIntent.putExtra("ID",IdValue);;
                startActivity(myIntent);
            }
        });

        Button button4 = (Button) findViewById(R.id.P_notice);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, NoticeActivity.class);
                startActivity(myIntent);
            }
        });
        Button logout = (Button) findViewById(R.id.btn_Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
        } else if
            (Value == 2) {
            title.setText("구직자 메인화면");
        Button button1 = (Button) findViewById(R.id.P_mypage);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, MyPageActivity.class);
                myIntent.putExtra("Value", 2);
                myIntent.putExtra("ID", LoginManager.getInstance().userId);
                startActivity(myIntent);
            }
        });

        Button button2 = (Button) findViewById(R.id.P_joblist);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, JobListActivity.class);
                myIntent.putExtra("Value", 2);
                myIntent.putExtra("ID",IdValue);;
                startActivity(myIntent);
            }
        });

        Button button3 = (Button) findViewById(R.id.P_participant);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, ParticipantStatusActivity.class);
                myIntent.putExtra("ID",IdValue);
                startActivity(myIntent);
            }
        });

        Button button4 = (Button) findViewById(R.id.P_notice);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity2.this, NoticeActivity.class);
                startActivity(myIntent);
            }
        });
            Button logout = (Button) findViewById(R.id.btn_Logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(myIntent);
                }
            });}
    }
}