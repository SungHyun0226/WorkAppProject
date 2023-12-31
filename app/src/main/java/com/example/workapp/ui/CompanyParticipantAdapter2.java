package com.example.workapp.ui;

import static com.example.workapp.R.layout.list_item_company_participant_detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.data.model.InnerParticipant;
import com.example.workapp.data.model.Recruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CompanyParticipantAdapter2 extends RecyclerView.Adapter<CompanyParticipantAdapter2.ViewHolder> {
    private ArrayList<InnerParticipant> mParticipantDatas; // 전체 리스트
    private ArrayList<InnerParticipant> mLstReport1; // 지원자 현황 리스트
    private ArrayList<InnerParticipant> mLstReport2; // 채용자 현황 리스트

    private boolean isVolunteer = false; // 지원자 보기 모드인지 아닌지 판단하는 불리언 변수

    private CompanyParticipantAdapter.OnItemClickEventListener mItemClickListener;
    private String recruitId;
    private String companyId;

    // 필터 모드를 분류 하는 상수
    private final int FILTER_MODE_GENDER  = 0;  // 성별
    private final int FILTER_MODE_AGE     = 1;  // 나이
    private final int FILTER_MODE_ADDRESS = 2;  // 주소
    private final int FILTER_MODE_RANK    = 3;  // 경력
    
    public CompanyParticipantAdapter2(String recruitId, String companyId){
        mParticipantDatas = new ArrayList<>();
        mLstReport1 = new ArrayList<>();
        mLstReport2 = new ArrayList<>();

        this.recruitId = recruitId;
        this.companyId = companyId;
    }

    public void setParticipantDataListData(ArrayList<InnerParticipant> ParticipantDatas){
        mParticipantDatas = ParticipantDatas;
        setInitList();
        notifyDataSetChanged();
    }

    public void setInitList() {
        if (!mLstReport1.isEmpty() || !mLstReport2.isEmpty()) {
            mLstReport1.clear();
            mLstReport2.clear();
        }

        // for 지원자 현황
        if (mParticipantDatas.size() != 0) {
            for (InnerParticipant item : mParticipantDatas) {
                if (item.getStatus() == 1) // 채용자 리스트에 추가
                    mLstReport2.add(item);
                else
                    mLstReport1.add(item); // 지원자 리스트에 추가
            }
        }
    }

    public void setChangeList(boolean _isVolunteer) {
        // true는 지원자 현황 보기 모드, false는 채용자 현황 보기 모드
        isVolunteer = _isVolunteer;
        setInitList();
    }

    public void setFilterMode(int _mode) {
        switch (_mode) {
            case FILTER_MODE_GENDER:
                // 성별
                mParticipantDatas.sort((o1, o2) -> {
                    if (o1.getGender().equals("남") && o2.getGender().equals("여"))
                        return -1;
                    else
                        return 0;
                });
                break;

            case FILTER_MODE_AGE:
                // 나이
                mParticipantDatas.sort((o1, o2) -> {
                    if (Integer.parseInt(o1.getBirth()) > Integer.parseInt(o2.getBirth()))
                        return -1;
                    else
                        return 0;
                });
                break;

            case FILTER_MODE_ADDRESS:
                // 주소
                mParticipantDatas.sort((o1, o2) -> {
                    if (o1.getAddress().compareTo(o2.getAddress()) < 0)
                        return -1;
                    else
                        return 0;
                });
                break;

            case FILTER_MODE_RANK:
                // 경력
                mParticipantDatas.sort((o1, o2) -> {
                    if (o1.getRank() > o2.getRank())
                        return -1;
                    else
                        return 0;
                });
                break;
        }
        setInitList();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView addressTextView;
        public TextView birthTextView;
        public TextView nameTextView;
        public TextView passwordTextView;
        public TextView phoneTextView;
        public TextView genderTextView;
        public FrameLayout confirmBtn;
        public FrameLayout noShowBtn;

        public ViewHolder(View itemView, final CompanyParticipantAdapter.OnItemClickEventListener itemClickListener) {
            super(itemView);

            addressTextView = (TextView) itemView.findViewById(R.id.tv_address);
            birthTextView = (TextView) itemView.findViewById(R.id.tv_birth);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
            passwordTextView = (TextView) itemView.findViewById(R.id.tv_password);
            phoneTextView = (TextView) itemView.findViewById(R.id.tv_phone);
            genderTextView = (TextView) itemView.findViewById(R.id.tv_gender) ;
            confirmBtn = itemView.findViewById(R.id.btn_confirm);
            noShowBtn = itemView.findViewById(R.id.btn_noshow);
        }
    }

    @NonNull
    @Override
    public CompanyParticipantAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(list_item_company_participant_detail,parent,false);
        return new CompanyParticipantAdapter2.ViewHolder(contactView, mItemClickListener);
        //UI 제작
    }
    @Override
    public void onBindViewHolder(@NonNull CompanyParticipantAdapter2.ViewHolder holder, int position) {
        InnerParticipant participantData;
        if (isVolunteer) {
            participantData = mLstReport1.get(position);
        } else {
            participantData = mLstReport2.get(position);
        }

        holder.addressTextView.setText(participantData.getAddress());

        if (participantData.getBirth() != null)
            holder.birthTextView.setText(participantData.getBirth());

        holder.nameTextView.setText(participantData.getName());


        holder.passwordTextView.setText(participantData.getRank()+ " 회");

        holder.phoneTextView.setText(participantData.getPhone());
        holder.genderTextView.setText(participantData.getGender());

        InnerParticipant finalParticipantData = participantData;
        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRankData(false, finalParticipantData.getId());
//                setButtonStatus(holder, finalParticipantData.getStatus());
            }
        });

        if (participantData.getStatus() == 1) {
            holder.noShowBtn.setVisibility(View.VISIBLE);
            holder.confirmBtn.setVisibility(View.GONE);
        } else if (participantData.getStatus() == 2) {
            holder.noShowBtn.setVisibility(View.GONE);
            holder.confirmBtn.setVisibility(View.VISIBLE);
        }

        holder.noShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRankData(true, finalParticipantData.getId());
//                setButtonStatus(holder, finalParticipantData.getStatus());
            }
        });

        //UI 내용물 채워넣기
    }

    private void setButtonStatus(@NonNull ViewHolder holder, Integer status) {
        holder.confirmBtn.setEnabled(false);
        holder.noShowBtn.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        if (isVolunteer) {
            return mLstReport1.size();
        } else {
            return mLstReport2.size();
        }
    }

    private void setRankData(Boolean isNoShow, String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("Participant").child(userId).child("rank");

        myRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Integer rank = task.getResult().getValue(Integer.class);

                if (isNoShow) {
                    myRef.setValue(rank - 2);
                } else {
                    myRef.setValue(rank + 1);
                }
            }
        });

        database.getReference("Users").child("Participant").child(userId).child("recruitList").child(recruitId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recruit recruit = snapshot.getValue(Recruit.class);
                if (recruit != null) {
                    if (isNoShow) {
                        setUserStatus(recruit, 2);
                        for (InnerParticipant item : mParticipantDatas) {
                            if (userId.equals(item.getId())) {
                                item.setStatus(2);
                                break;
                            }
                        }
                    } else {
                        setUserStatus(recruit, 1);
                        for (InnerParticipant item : mParticipantDatas) {
                            if (userId.equals(item.getId())) {
                                item.setStatus(1);
                                break;
                            }
                        }
                    }
                    setInitList();
                    notifyDataSetChanged();

                    database.getReference("Users").child("Participant").child(userId).child("recruitList").child(recruitId).setValue(recruit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        userRecruitRef.get().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.e("firebase", "Error getting data", task.getException());
//            } else {
//                Recruit recruit = task.getResult().getValue(Recruit.class);
//
//                if (isNoShow) {
//                    setUserStatus(recruit,  2);
//                } else {
//                    setUserStatus(recruit,  1);
//                }
//                userRecruitRef.setValue(recruit);
//            }
//        });

        DatabaseReference companyRecruitRef = database.getReference("Users")
                .child("Company")
                .child(companyId)
                .child("recruitList")
                .child(recruitId);

        companyRecruitRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Recruit recruit = task.getResult().getValue(Recruit.class);

                if (isNoShow) {
                    setParticipantStatus(recruit, userId, 2);
                } else {
                    setParticipantStatus(recruit, userId, 1);
                }
                companyRecruitRef.setValue(recruit);
            }
        });
    }

    private void setParticipantStatus(Recruit recruit, String userId, int status) {
        InnerParticipant ret = recruit.getParticipants().get(userId);
        ret.setStatus(status);
        recruit.getParticipants().replace(userId, ret);
    }

    private void setUserStatus(Recruit recruit, int status) {
        recruit.setStatus(status);
    }
}
