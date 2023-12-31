package com.example.workapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.workapp.LoginManager;
import com.example.workapp.R;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.ParticipantData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    RadioGroup companyandparticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference companyUser = database.getReference("Users").child("Company");
        DatabaseReference participantUser = database.getReference("Users").child("Participant");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_admin = (Button) findViewById(R.id.button3);
        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent =new Intent(MainActivity.this, AdminMainActivity.class);
                startActivity(myIntent);
            }
        });

        companyandparticipant = (RadioGroup) findViewById(R.id.compa_group);

        Button button1 = (Button) findViewById(R.id.open_button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int compaorpart = companyandparticipant.getCheckedRadioButtonId();
                if (compaorpart == -1) {
                    //not clicked
                    Toast.makeText(getApplicationContext(), "사용자의 형태를 체크해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    EditText editText = (EditText) findViewById(R.id.user_id);
                    EditText editTextPassWord = (EditText) findViewById(R.id.editTextTextPassword);

                    if (compaorpart == R.id.company_button) {
                        companyUser.child(editText.getText().toString()).get().addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                CompanyData cd = task.getResult().getValue(CompanyData.class);
                                if (cd != null) {
                                    if (editTextPassWord.getText().toString().equals(cd.getPassword())) {

                                        LoginManager.getInstance().setCompanyData(cd);
                                        Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
                                        myIntent.putExtra("ID", LoginManager.getInstance().userId);
                                        myIntent.putExtra("Value", 1);
                                        startActivity(myIntent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "가입되지 않은 ID 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else if (compaorpart == R.id.Participant_button) {

                        participantUser.child(editText.getText().toString()).get().addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                ParticipantData pd = task.getResult().getValue(ParticipantData.class);
                                if (pd != null) {
                                    if (editTextPassWord.getText().toString().equals(pd.getPassword())) {

                                        LoginManager.getInstance().setParticipantData(pd);
                                        Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
                                        myIntent.putExtra("ID", LoginManager.getInstance().userId);
                                        myIntent.putExtra("Value", 2);
                                        startActivity(myIntent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "가입되지 않은 ID 입니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        Button button2 = (Button) findViewById(R.id.register_button);
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int compaorpart = companyandparticipant.getCheckedRadioButtonId();
                if (compaorpart == -1) {
                    //not clicked
                    Toast.makeText(getApplicationContext(), "사용자의 형태를 체크해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }   else {
                        if (compaorpart == R.id.company_button) {

                            Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                            myIntent.putExtra("Value",1);
                            startActivity(myIntent);
                        } else if (compaorpart == R.id.Participant_button) {

                            Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                            myIntent.putExtra("Value",2);
                            startActivity(myIntent);
                        }
                    }
                }
            });
        }
    }