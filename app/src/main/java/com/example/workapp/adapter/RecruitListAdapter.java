package com.example.workapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.data.model.Recruit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecruitListAdapter extends RecyclerView.Adapter<RecruitListAdapter.ViewHolder> {
    public ArrayList<Recruit> mRecruits = new ArrayList<Recruit>();
    private OnItemClickEventListener mItemClickListener;

    public void setListInit(ArrayList<Recruit> _lstRecruit) {
        if (!mRecruits.isEmpty())
            mRecruits.clear();
        mRecruits.addAll(_lstRecruit);
    }

    public interface OnItemClickEventListener {
        void onItemClick(View view, Recruit rec);
    }

    public void setOnItemClickListener(OnItemClickEventListener listener) {
        mItemClickListener = listener;
    }

    public void setRecruitListData(ArrayList<Recruit> recruits) {
        mRecruits = recruits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecruitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.recruit_item, parent, false);

        return new ViewHolder(contactView, mItemClickListener);
        //UI 제작
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitListAdapter.ViewHolder holder, int position) {
        Recruit recruit = mRecruits.get(position);

        TextView title = holder.titleTextView;
        TextView company = holder.companyTextview;

        title.setText(recruit.getTitle());
        company.setText(recruit.getCompanyName());
        // UI 내용물 채워넣기
    }

    @Override
    public int getItemCount() {
        return mRecruits.size();
    }

    public void sortList(boolean isCompany) {
        Collections.sort(mRecruits, new Comparator<Recruit>() {
            public int compare(Recruit obj1, Recruit obj2) {
                if (isCompany) {
                    return obj1.getCompanyName().compareToIgnoreCase(obj2.getCompanyName());
                } else {
                    return obj1.getTitle().compareToIgnoreCase(obj2.getTitle());
                }
            }
        });
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView companyTextview;

        public ViewHolder(View itemView, final OnItemClickEventListener itemClickListener) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.recruit_title);
            companyTextview = itemView.findViewById(R.id.recruit_company);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(view, mRecruits.get(position));
                    }
                }
            });
        }
    }
}
