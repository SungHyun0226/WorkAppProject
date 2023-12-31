package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workapp.R;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.ParticipantData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * 마이 페이지 수정 화면
 */
public class EditMyPageActivity extends AppCompatActivity implements View.OnClickListener {
    private int userType;
    private String idValue;

    private DatabaseReference mDatabaseRef;

    private TextView mTvMutable1, mTvMutable2, mTvMutable3;
    private EditText mEtId, mEtPassword, mEtName, mEtBirthday, mEtPhone, mEtActiveYear;
    private Spinner spinnerAddress;

    // 기존 서버에 저장되어 있는 데이터들
    private CompanyData mOldCompanyData;
    private ParticipantData mOldParticipantData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_page);
        setInitialize();
    }

    private void setInitialize() {
        findViewById(R.id.btn_edit_complete).setOnClickListener(this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        LinearLayout linearActiveYear = findViewById(R.id.linear_active_year);
        // 기업, 구직자 타입에 따라 변경될 텍스트
        mTvMutable1 = findViewById(R.id.tv_mutable_1);
        mTvMutable2 = findViewById(R.id.tv_mutable_2);
        mTvMutable3 = findViewById(R.id.tv_mutable_3);

        mEtId = findViewById(R.id.et_id);
        mEtPassword = findViewById(R.id.et_pwd);
        mEtName = findViewById(R.id.et_name);
        mEtBirthday = findViewById(R.id.et_birthday);
        mEtPhone = findViewById(R.id.et_phone);
        mEtActiveYear = findViewById(R.id.et_active_year);
        spinnerAddress = findViewById(R.id.spinner_address);

        userType = 0;          // 1 : 기업,  2 : 구직자
        idValue = "";    // Firebase Key 값이 될 수 있는 계정명

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            userType = intent.getIntExtra("value", -1);
            idValue = intent.getStringExtra("id_value");
        }

        if (userType == 1) {
            // 기업 마이페이지
            mTvMutable1.setText("기업명");
            mTvMutable2.setText("기업대표");
            mTvMutable3.setText("전화번호");
            linearActiveYear.setVisibility(View.INVISIBLE);
            mDatabaseRef.child("Company").child(idValue).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CompanyData companyData = snapshot.getValue(CompanyData.class);
                    if (companyData != null) {
                        mOldCompanyData = companyData;

                        mEtId.setText(companyData.getId());
                        mEtPassword.setText(companyData.getPassword());
                        mEtName.setText(companyData.getCompanyName());
                        mEtPhone.setText(companyData.getPhone());
                        mEtBirthday.setText(companyData.getCeo());

                        // 시 단위 순회하여 같은 address 스피너 select
                        for (int i = 0; i < 26; i++) {
                            if (getResources().getStringArray(R.array.address)[i].equals(companyData.getAddress()))
                                spinnerAddress.setSelection(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase DB Error!", error.getMessage());
                }
            });
        } else if (userType == 2) {
            // 구직자 마이페이지
            mTvMutable1.setText("이름");
            mTvMutable2.setText("생년월일");
            mTvMutable3.setText("휴대폰");
            linearActiveYear.setVisibility(View.INVISIBLE);
            mDatabaseRef.child("Participant").child(idValue).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ParticipantData participantData = snapshot.getValue(ParticipantData.class);
                    if (participantData != null) {
                        mOldParticipantData = participantData;

                        mEtId.setText(participantData.getId());
                        mEtPassword.setText(participantData.getPassword());
                        mEtName.setText(participantData.getName());
                        mEtBirthday.setText(participantData.getBirth());
                        mEtPhone.setText(participantData.getPhone());
                        mEtActiveYear.setText(String.valueOf(participantData.getRank()));

                        // 시 단위 순회하여 같은 address 스피너 select
                        for (int i = 0; i < 26; i++) {
                            if (getResources().getStringArray(R.array.address)[i].equals(participantData.getAddress()))
                                spinnerAddress.setSelection(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase DB Error!", error.getMessage());
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_complete:
                // 수정 완료 버튼
                String inputId = mEtId.getText().toString();
                String inputPwd = mEtPassword.getText().toString();
                String inputName = mEtName.getText().toString();
                String inputBirth = mEtBirthday.getText().toString();
                String inputPhone = mEtPhone.getText().toString();
                String inputActiveYear = mEtActiveYear.getText().toString();
                String inputAddress = spinnerAddress.getSelectedItem().toString();

                // empty value check
                if (TextUtils.isEmpty(inputId) || TextUtils.isEmpty(inputPwd) || TextUtils.isEmpty(inputName) || TextUtils.isEmpty(inputBirth) || TextUtils.isEmpty(inputPhone) || TextUtils.isEmpty(inputAddress)) {
                    Toast.makeText(this, "비어 있는 입력 값이 있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                if (userType == 1) {
                    // 기업
                    mOldCompanyData.setId(inputId);
                    mOldCompanyData.setPassword(inputPwd);
                    mOldCompanyData.setCeo(inputBirth);
                    mOldCompanyData.setPhone(inputPhone);
                    mOldCompanyData.setAddress(inputAddress);
                    mOldCompanyData.setCompanyName(inputName);

                    mDatabaseRef.child("Company").child(idValue).setValue(mOldCompanyData);
                    intent.putExtra("company", mOldCompanyData);

                } else if (userType == 2) {
                    // 구직자
                    if (TextUtils.isEmpty(inputActiveYear)) {
                        Toast.makeText(this, "비어 있는 입력 값이 있습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mOldParticipantData.setId(inputId);
                    mOldParticipantData.setPassword(inputPwd);
                    mOldParticipantData.setName(inputName);
                    mOldParticipantData.setPhone(inputPhone);
                    mOldParticipantData.setBirth(inputBirth);
                    mOldParticipantData.setAddress(inputAddress);

                    mDatabaseRef.child("Participant").child(idValue).setValue(mOldParticipantData);
                    intent.putExtra("participant", mOldParticipantData);
                }
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}