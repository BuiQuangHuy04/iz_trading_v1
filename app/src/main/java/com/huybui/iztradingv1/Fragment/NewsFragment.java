package com.huybui.iztradingv1.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huybui.iztradingv1.Adapter.NewsAdapter;
import com.huybui.iztradingv1.Model.News;
import com.huybui.iztradingv1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NewsFragment extends Fragment {

    private final List<News> newsList = new ArrayList<>();

    protected NewsAdapter mNewsAdapter;

    public NewsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView rcvNews = rootview.findViewById(R.id.rcv_news);

        rcvNews.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        mNewsAdapter = new NewsAdapter(rootview.getContext());

        getNewsList(mNewsAdapter, container);

        mNewsAdapter.setData(newsList);

        rcvNews.setAdapter(mNewsAdapter);

        return rootview;
    }

    private void getNewsList(NewsAdapter adapter, ViewGroup viewGroup) {
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("news");
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    newsList.add(dataSnapshot.getValue(News.class));
                }

                Collections.reverse(Objects.requireNonNull(newsList));

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}