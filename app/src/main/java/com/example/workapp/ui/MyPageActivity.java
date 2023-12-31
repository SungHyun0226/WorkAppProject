package com.example.workapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workapp.R;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.ParticipantData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {
    private int Value;
    private String IdValue;
    private TextView IdDataView,  nameDataView, addressDataView, passwordDataView, birthOrCeoDataView, phoneDataView, rankDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        findViewById(R.id.button5).setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Company");
        DatabaseReference myParticipant = database.getReference("Users").child("Participant");

        TextView titleTextView = findViewById(R.id.CP_title_textview);
        TextView nameTextView = findViewById(R.id.textView38);
        TextView birthOrCeoTextView = findViewById(R.id.textView39);
        TextView phoneTextView = findViewById(R.id.textView40);
        TextView statusTextView = findViewById(R.id.statusTextView);

        //DataBase
        nameDataView = findViewById(R.id.name_dataView);
        IdDataView = findViewById(R.id.ID_dataView);
        addressDataView = findViewById(R.id.address_dataView);
        passwordDataView = findViewById(R.id.passowrd_dataView);
        birthOrCeoDataView = findViewById(R.id.birthOrCEO_dataView);
        phoneDataView = findViewById(R.id.phone_dataView);
        rankDataView = findViewById(R.id.rank_dataView);

        Intent myintent = getIntent();
        Value = myintent.getExtras().getInt("Value");
        IdValue = myintent.getExtras().getString("ID");
        if (Value == 1) {
            titleTextView.setText("기업 마이페이지");
            nameTextView.setText("기업명");
            birthOrCeoTextView.setText("기업대표");
            phoneTextView.setText("전화번호");
            statusTextView.setVisibility(View.INVISIBLE);
            statusTextView.setEnabled(false);
            rankDataView.setVisibility(View.INVISIBLE);
            rankDataView.setEnabled(false);
            myRef.child(IdValue).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        task.getResult().getChildren();
                        String companyName = task.getResult().child("companyName").getValue(String.class);
                        nameDataView.setText(companyName);
                        String ID = task.getResult().child("id").getValue(String.class);
                        IdDataView.setText(ID);
                        String address = task.getResult().child("address").getValue(String.class);
                        addressDataView.setText(address);
                        String password = task.getResult().child("password").getValue(String.class);
                        passwordDataView.setText(password);
                        String CEO = task.getResult().child("ceo").getValue(String.class);
                        birthOrCeoDataView.setText(CEO);
                        String phone = task.getResult().child("phone").getValue(String.class);
                        phoneDataView.setText(phone);
                    }
                }
            });

        } else if (Value == 2) {
            titleTextView.setText("구직자 마이페이지");
            nameTextView.setText("이름");
            birthOrCeoTextView.setText("생년월일");
            phoneTextView.setText("휴대폰");
            myParticipant.child(IdValue).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        task.getResult().getChildren();
                        String name = task.getResult().child("name").getValue(String.class);
                        nameDataView.setText(name);
                        String ID = task.getResult().child("id").getValue(String.class);
                        IdDataView.setText(ID);
                        String address = task.getResult().child("address").getValue(String.class);
                        addressDataView.setText(address);
                        String password = task.getResult().child("password").getValue(String.class);
                        passwordDataView.setText(password);
                        String birth = task.getResult().child("birth").getValue(String.class);
                        birthOrCeoDataView.setText(birth);
                        String phone = task.getResult().child("phone").getValue(String.class);
                        phoneDataView.setText(phone);
                        rankDataView.setText(task.getResult().child("rank").getValue(Integer.class).toString());
                    }
                }
            });
        }
    }

    // 액티비티 결과 받아오는 리스너
    private final ActivityResultLauncher<Intent> getEditMyPageResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        if (result.getData().getExtras() != null) {
                            ParticipantData participantData = result.getData().getParcelableExtra("participant");
                            CompanyData companyData = result.getData().getParcelableExtra("company");

                            if (companyData != null) {
                                // 기업 마이 페이지 수정완료 case
                                IdDataView.setText(companyData.getId());
                                nameDataView.setText(companyData.getCompanyName());
                                phoneDataView.setText(companyData.getPhone());
                                passwordDataView.setText(companyData.getPassword());
                                addressDataView.setText(companyData.getAddress());
                                birthOrCeoDataView.setText(companyData.getCeo());

                            } else if (participantData != null) {
                                // 구직자 마이 페이지 수정완료 case
                                IdDataView.setText(participantData.getId());
                                passwordDataView.setText(participantData.getPassword());
                                nameDataView.setText(participantData.getName());
                                addressDataView.setText(participantData.getAddress());
                                birthOrCeoDataView.setText(participantData.getBirth());
                                phoneDataView.setText(participantData.getPhone());
                                rankDataView.setText(String.valueOf(participantData.getRank()));
                            }

                            Toast.makeText(this, "수정 완료 되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button5:
                // 수정 버튼 클릭
                Intent editMyPageIntent = new Intent(this, EditMyPageActivity.class);
                editMyPageIntent.putExtra("value", Value);
                editMyPageIntent.putExtra("id_value", IdValue);
                getEditMyPageResult.launch(editMyPageIntent);
                break;
        }
    }
}