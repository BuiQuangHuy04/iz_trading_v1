package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.huybui.iztradingv1.Model.News;
import com.huybui.iztradingv1.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        News news = (News) bundle.get("news_detail");

        try {
            TextView txtv_news_title = findViewById(R.id.txtv_news_title);
            TextView txtv_user_acc = findViewById(R.id.txtv_user_acc);
            TextView txtv_news_date = findViewById(R.id.txtv_news_date);
            TextView txtv_news_body = findViewById(R.id.txtv_news_body);
            ImageView iv_img_news = findViewById(R.id.iv_img_news);

            Picasso.get()
                    .load(news.getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv_img_news);
            txtv_news_title.setText(news.getTitle());
            txtv_user_acc.setText(news.getUser_account());
            txtv_news_date.setText(news.getDate());
            txtv_news_body.setText(news.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}