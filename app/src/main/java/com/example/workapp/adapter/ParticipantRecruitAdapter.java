package com.example.workapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.data.model.Recruit2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParticipantRecruitAdapter extends RecyclerView.Adapter<ParticipantRecruitAdapter.ViewHolder> {

    private ArrayList<Recruit2> mRecruits2;
    private OnItemClickEventListener mItemClickListener;
    private DatabaseReference mDatabaseRef;

    public interface OnItemClickEventListener {
        void onItemClick(View view, int pos);
//    void onDeleteClick(View view, int pos);
    }

    public void setOnItemClickListener(ParticipantRecruitAdapter.OnItemClickEventListener listener) {
        this.mItemClickListener = listener;
    }

    public ParticipantRecruitAdapter(ArrayList<Recruit2> recruits2) {
        mRecruits2 = recruits2;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    @NonNull
    @Override
    public ParticipantRecruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recruit_status_item, parent, false);
        return new ParticipantRecruitAdapter.ViewHolder(contactView, mItemClickListener);
        //UI 제작
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantRecruitAdapter.ViewHolder holder, int position) {
        Recruit2 recruit2 = mRecruits2.get(position);

        TextView title = holder.titleTextView2;
        TextView company = holder.companyTextview2;

        title.setText(recruit2.getTitle());
        company.setText(recruit2.getCompanyName());
        //UI 내용물 채워넣기
    }

    @Override
    public int getItemCount() {
        return mRecruits2.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView2;
        public TextView companyTextview2;
        public Button btnDelete;

        public ViewHolder(View itemView, final ParticipantRecruitAdapter.OnItemClickEventListener itemClickListener) {
            super(itemView);

            titleTextView2 = (TextView) itemView.findViewById(R.id.recruit_status_title);
            companyTextview2 = (TextView) itemView.findViewById(R.id.recruit_status_company);
            btnDelete = itemView.findViewById(R.id.recruit_delete_button);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(view, position);
                    }
                }
            });


            // 삭제
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recruit2 recruit2 = mRecruits2.get(getAdapterPosition());
                    mDatabaseRef.child("Company").child(recruit2.getCompanyName()).child("recruitList").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    mRecruits2.remove(getAdapterPosition());
                }
            });
        }
    }



}
