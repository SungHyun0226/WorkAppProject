package com.example.workapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.workapp.LoginManager;
import com.example.workapp.R;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.viewmodel.RecruitViewModel;
import com.example.workapp.viewmodel.RecruitWriteViewModel;

public class RecruitWriteActivity extends AppCompatActivity {

    private RecruitWriteViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_write);

        vm = new ViewModelProvider(this).get(RecruitWriteViewModel.class);

        EditText WriteTitle = (EditText) findViewById(R.id.WriteTitle);
        EditText WritePay = (EditText) findViewById(R.id.WritePay);
        EditText WriteDate = (EditText) findViewById(R.id.WriteDate);
        Spinner spinnerAddress = findViewById(R.id.spinner_address);
        Spinner spinnerJob = findViewById(R.id.spinner_job);
//        EditText WriteAddress = (EditText) findViewById(R.id.WriteAddress);
        EditText WriteContent = (EditText) findViewById(R.id.WriteContent);
        EditText WriteLastDate = (EditText) findViewById(R.id.WriteLastDate);


        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(v -> {
            Recruit re = new Recruit();
            re.setId(LoginManager.getInstance().userId);
            re.setTitle(WriteTitle.getText().toString());
            re.setAddress(spinnerAddress.getSelectedItem().toString());
            re.setJob(spinnerJob.getSelectedItem().toString());
//            re.setAddress(WriteAddress.getText().toString());
            re.setDate(WriteDate.getText().toString());
            re.setPay(Long.parseLong(WritePay.getText().toString()));
            re.setLastDate(WriteLastDate.getText().toString());
            re.setRcContent(WriteContent.getText().toString());

            vm.writeRecruit(re);
        });

        vm = new ViewModelProvider(this).get(RecruitWriteViewModel.class);
        vm.isUpdateRecruit.observe(this, isUpdated -> {
            // liveData의 변경 값에 따라 뷰의 상태 변경
            if (isUpdated)
                finish();
        });
    }
}