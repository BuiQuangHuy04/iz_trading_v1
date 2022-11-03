package com.huybui.iztradingv1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Adapter.NewsAdapter;
import com.huybui.iztradingv1.R;
import com.huybui.iztradingv1.ViewModel.NewsViewModel;

public class NewsFragment extends Fragment {

    private NewsAdapter mNewsAdapter;

    public NewsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_news, container, false);

        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        RecyclerView rcvNews = rootview.findViewById(R.id.rcv_news);

        rcvNews.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        mNewsAdapter = new NewsAdapter(rootview.getContext());

        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> mNewsAdapter.setData(news));

        rcvNews.setAdapter(mNewsAdapter);

        return rootview;
    }
}