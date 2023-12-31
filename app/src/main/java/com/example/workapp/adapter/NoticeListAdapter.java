package com.example.workapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workapp.R;
import com.example.workapp.data.model.NoticeData;
import com.example.workapp.ui.NoticeDetailActivity;


import java.util.ArrayList;


public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.CustomViewHolder> {

    private ArrayList<NoticeData> arrayList;
    private Context mContext;

    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.


    public NoticeListAdapter() {
        arrayList = new ArrayList<>();
    }

    public void setListInit(ArrayList<NoticeData> _lstNotice) {
        arrayList.addAll(_lstNotice);
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.notice_title.setText(arrayList.get(position).getTitle());
        holder.notice_date.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView notice_title;
        protected TextView notice_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.notice_title = itemView.findViewById(R.id.notice_title);
            this.notice_date = itemView.findViewById(R.id.notice_date);

            itemView.setOnClickListener(view -> {
                NoticeData noticeData = arrayList.get(getAdapterPosition());
                Intent intent = new Intent(mContext, NoticeDetailActivity.class);
                intent.putExtra("notice_data", noticeData);
                mContext.startActivity(intent);
            });
        }
    }

}










