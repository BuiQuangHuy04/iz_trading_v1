package com.huybui.iztradingv1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Activity.NewsDetailActivity;
import com.huybui.iztradingv1.Model.News;
import com.huybui.iztradingv1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewholder> {

    private final Context mContext;
    private List<News> mNewsList;

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<News> list) {
        this.mNewsList = list;
        notifyDataSetChanged(); //load n build data
    }

    @NonNull
    @Override
    public NewsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewholder holder, int position) {
        News news = mNewsList.get(position);

        if (news == null) return;

        Picasso.get()
                .load(news.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.iv_thumbnail_news);

        holder.txtv_title_news.setText(news.getTitle());

        holder.layout_news_container.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("news_detail", news);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mNewsList != null) {
            return mNewsList.size();
        }
        return 0;
    }

    public static class NewsViewholder extends RecyclerView.ViewHolder {

        private final CardView layout_news_container;
        private final TextView txtv_title_news;
        private final ImageView iv_thumbnail_news;

        public NewsViewholder(@NonNull View itemView) {
            super(itemView);

            layout_news_container = itemView.findViewById(R.id.layout_news_container);
            txtv_title_news = itemView.findViewById(R.id.txtv_title_news);
            iv_thumbnail_news = itemView.findViewById(R.id.iv_thumbnail_news);
        }
    }
}
