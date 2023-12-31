package com.example.workapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workapp.R;
import com.example.workapp.data.model.CompanyData;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.viewmodel.RecruitWriteViewModel;
import com.example.workapp.viewmodel.RegisterViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText idView;
    EditText paView;
    EditText nameView;
    EditText birthOrCEOView;
    EditText phoneView;
    EditText addressView;
    RadioGroup genderGroup;
    TextView nameTextView;
    TextView birthOrCEOTextView;
    TextView phoneTextView;
    TextView titleTextView;

    private RegisterViewModel vm;


    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent myintent = getIntent();

        int Value = myintent.getExtras().getInt("Value");

        vm = new ViewModelProvider(this).get(RegisterViewModel.class);

        idView = (EditText) findViewById(R.id.id_editview);
        paView = (EditText) findViewById(R.id.password_editview);
        nameView = (EditText) findViewById(R.id.name_editview);
        birthOrCEOView = (EditText) findViewById(R.id.birth_editview);
        phoneView = (EditText) findViewById(R.id.phone_editview);
        addressView = (EditText) findViewById(R.id.address_editview);

        nameTextView = (TextView) findViewById(R.id.name_textview);
        birthOrCEOTextView = (TextView) findViewById(R.id.birth_textview);
        phoneTextView = (TextView) findViewById(R.id.phone_textview);
        titleTextView = (TextView) findViewById(R.id.textView5);

        genderGroup = (RadioGroup) findViewById(R.id.gender_group);
        if (Value == 2) {
            genderGroup.setVisibility(View.VISIBLE);
            genderGroup.setEnabled(true);
        } else {
            genderGroup.setVisibility(View.INVISIBLE);
            genderGroup.setEnabled(false);
        }
            Button registerButton = (Button) findViewById(R.id.register);
            if (Value == 2) {
                titleTextView.setText("구직자 회원가입");
                nameTextView.setText("이름");
                birthOrCEOTextView.setText("생년월일");
                phoneTextView.setText("휴대폰");
                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = String.valueOf(idView.getText());
                        String pa = String.valueOf(paView.getText());
                        String name = String.valueOf(nameView.getText());
                        String birth = String.valueOf(birthOrCEOView.getText());
                        String phone = String.valueOf(phoneView.getText());
                        String address = String.valueOf(addressView.getText());
                        String gender = ((RadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString();

                        vm.registerParticipant(new ParticipantData(id, pa, name, birth, phone, address, gender, 0));

                        if (id.length() < 8) {
                            Toast.makeText(getApplicationContext(), "아이디는 8자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!validatePassword(pa)) {
                            Toast.makeText(getApplicationContext(), "비밀번호에 소문자, 대문자, 특수문자, 숫자를 적어도 하나를 포함해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (name.length() < 1) {
                            Toast.makeText(getApplicationContext(), "이름을 입력하여주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (birth.length() != 8) {
                            Toast.makeText(getApplicationContext(), "생년월일을 8자리로 입력하여주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (phone.length() != 11) {
                            Toast.makeText(getApplicationContext(), "휴대폰 번호는 -를 뺀 11자리를 입력하여주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (address.length() < 1) {
                            Toast.makeText(getApplicationContext(), "주소를 입력하여주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        finish();
                    }
                });
            } else {
                titleTextView.setText("기업 회원가입");
                nameTextView.setText("기업명");
                birthOrCEOTextView.setText("기업대표");
                phoneTextView.setText("전화번호");
                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = String.valueOf(idView.getText());
                        String pa = String.valueOf(paView.getText());
                        String name = String.valueOf(nameView.getText());
                        String ceo = String.valueOf(birthOrCEOView.getText());
                        String phone = String.valueOf(phoneView.getText());
                        String address = String.valueOf(addressView.getText());

                        vm.registerCompany(new CompanyData(id, pa, name, ceo, phone, address));


                        if (id.length() < 8) {
                            Toast.makeText(getApplicationContext(), "아이디는 8자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!validatePassword(pa)) {
                            Toast.makeText(getApplicationContext(), "비밀번호에 소문자, 대문자, 특수문자, 숫자를 적어도 하나를 포함해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (name.length() < 1) {
                            Toast.makeText(getApplicationContext(), "기업명을 입력하여주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (ceo.length() < 1) {
                            Toast.makeText(getApplicationContext(), "대표명을 입력하여주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (phone.length() != 11) {
                            Toast.makeText(getApplicationContext(), "-를 뺀 전화번호를 올바르게 입력하여주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (address.length() < 1) {
                            Toast.makeText(getApplicationContext(), "주소를 입력하여주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        finish();

                    }
                });
            }

        }
    private boolean validatePassword(String pa) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pa);

        return matcher.matches();
    }
}