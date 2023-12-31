package com.example.workapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.workapp.R;
import com.example.workapp.adapter.NoticeListAdapter;
import com.example.workapp.data.model.NoticeData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoticeListAdapter adapter;
    private ArrayList<NoticeData> arrayList;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.notice_rcview);
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new NoticeListAdapter();
        recyclerView.setAdapter(adapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Notice");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeData notice = snapshot.getValue(NoticeData.class);
                    arrayList.add(notice);
                }
                adapter.setListInit(arrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("NoticeActivity", String.valueOf(error.toException()));
            }
        });
    }
}



