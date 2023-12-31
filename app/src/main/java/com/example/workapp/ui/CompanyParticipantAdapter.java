package com.example.workapp.ui;

import static com.example.workapp.R.layout.recruit_participant_status_item;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.data.model.ParticipantData;
import com.example.workapp.data.model.Recruit;
import com.example.workapp.data.model.Recruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyParticipantAdapter extends RecyclerView.Adapter<CompanyParticipantAdapter.ViewHolder> {
    private ArrayList<Recruit> mRecruits;
    private OnItemClickEventListener mItemClickListener;
    private DatabaseReference mDatabaseRef;
    private String mUserId;
    private Context mContext;

    public void setUserId(String _userId) {
        mUserId = _userId;
    }

    public interface OnItemClickEventListener {
        void onItemClick(View view, Recruit rec);
    }

    public void setOnItemClickListener(OnItemClickEventListener listener){
        mItemClickListener = listener;
    }

    public CompanyParticipantAdapter() {
        mRecruits = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void setRecruitListData(ArrayList<Recruit> Recruits){
        mRecruits = Recruits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompanyParticipantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(recruit_participant_status_item,parent,false);
        return new CompanyParticipantAdapter.ViewHolder(contactView, mItemClickListener);
        //UI 제작
    }
    @Override
    public void onBindViewHolder(@NonNull CompanyParticipantAdapter.ViewHolder holder, int position) {
        Recruit Recruit = mRecruits.get(position);
        TextView title = holder.titleTextView3;
        title.setText(Recruit.getTitle());
        //UI 내용물 채워넣기
    }

    @Override
    public int getItemCount() {
        return mRecruits.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView titleTextView3;
        protected Button recruitCancelButton;

        public ViewHolder(View itemView, final OnItemClickEventListener itemClickListener) {
            super(itemView);

            titleTextView3 = (TextView) itemView.findViewById(R.id.recruit_participant_title);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    final int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(view, mRecruits.get(position));
                    }
                }
            });

            recruitCancelButton = itemView.findViewById(R.id.recruit_cancel);
            recruitCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recruit recruit = mRecruits.get(getAdapterPosition());
                    // 기업에서의 공고 제거
                    mDatabaseRef.child("Company").child(mUserId).child("recruitList").child(recruit.getId()).removeValue();

                    // 기업 공고가 삭제되었으니, 관련 구직자들의 지원 현황에서도 찾아서 제거해줘야 함.
                    mDatabaseRef.child("Participant").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                ParticipantData participantData = ds.getValue(ParticipantData.class);
                                if (participantData != null) {
                                    HashMap<String, Recruit> map = participantData.getRecruitList();
                                    if (map != null) {
                                        for (Map.Entry<String, Recruit> item : map.entrySet()) {
                                            if (item.getKey().equals(recruit.getId())) {
                                                mDatabaseRef.child("Participant").child(ds.getKey()).child("recruitList").child(item.getKey()).removeValue();
                                            }
                                        }
                                    }
                                }
                            }

                            mRecruits.remove(recruit);
                            notifyItemRemoved(getAdapterPosition());
                            Toast.makeText(mContext, "공고가 삭제 되었습니다", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Firebase DB Error!", error.getMessage());
                        }
                    });
                }
            });
        }
    }
}
