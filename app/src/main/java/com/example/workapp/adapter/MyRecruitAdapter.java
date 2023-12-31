package com.example.workapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.LoginManager;
import com.example.workapp.R;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.ui.ParticipantStatus2Activity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyRecruitAdapter extends RecyclerView.Adapter<MyRecruitAdapter.MyRecruitVH> {
    private ArrayList<Recruit> mLstMyRecruit;
    private Context mContext;
    private DatabaseReference mDatabaseRef;
    private String mIdValue = "";

    public MyRecruitAdapter() {
        mLstMyRecruit = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void setIdValue(String _idValue) {
        mIdValue = _idValue;
    }

    public void setListInit(ArrayList<Recruit> _lstMyRecruit) {
        if (!mLstMyRecruit.isEmpty())
            mLstMyRecruit.clear();

        mLstMyRecruit.addAll(_lstMyRecruit);
    }

    @NonNull
    @Override
    public MyRecruitAdapter.MyRecruitVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MyRecruitVH(LayoutInflater.from(mContext).inflate(R.layout.recruit_status_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecruitAdapter.MyRecruitVH holder, int position) {
        Recruit recruit = mLstMyRecruit.get(position);
        holder.recruit_status_title.setText(recruit.getTitle());
        holder.recruit_status_company.setText(recruit.getCompanyName());
        if (recruit.getStatus() == 1) {
            holder.recruit_delete_button.setBackgroundColor(Color.green(255));
            holder.recruit_delete_button.setText("채용됨");
        }
    }

    @Override
    public int getItemCount() {
        return mLstMyRecruit.size();
    }

    public class MyRecruitVH extends RecyclerView.ViewHolder {
        protected TextView recruit_status_title, recruit_status_company;
        protected Button recruit_delete_button;
        public MyRecruitVH(@NonNull View itemView) {
            super(itemView);

            recruit_status_title = itemView.findViewById(R.id.recruit_status_title);
            recruit_status_company = itemView.findViewById(R.id.recruit_status_company);
            recruit_delete_button = itemView.findViewById(R.id.recruit_delete_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 공고 상세보기로 넘어가기
                    Recruit recruit = mLstMyRecruit.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, ParticipantStatus2Activity.class);
                    intent.putExtra("recruit", recruit);
                    mContext.startActivity(intent);
                }
            });

            recruit_delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 지원 취소 버튼
                    Recruit recruit = mLstMyRecruit.get(getAdapterPosition());
                    mDatabaseRef.child("Participant").child(mIdValue).child("recruitList").child(recruit.getId()).removeValue();
                    mDatabaseRef.child("Company").child(recruit.getCompanyId()).child("recruitList").child(recruit.getId()).child("participants").child(LoginManager.getInstance().getUserId()).removeValue();
                    mLstMyRecruit.remove(recruit);
                    notifyItemRemoved(getAdapterPosition());
                    Toast.makeText(mContext, "공고 지원이 취소 되었습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
