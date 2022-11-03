package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.huybui.iztradingv1.Model.News;
import com.huybui.iztradingv1.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView txtv_news_title, txtv_user_acc, txtv_news_date, txtv_news_body;
    private ImageView iv_img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        News news = (News) bundle.get("news_detail");
//        System.out.println(news);

        try {
            txtv_news_title = findViewById(R.id.txtv_news_title);
            txtv_user_acc = findViewById(R.id.txtv_user_acc);
            txtv_news_date = findViewById(R.id.txtv_news_date);
            txtv_news_body = findViewById(R.id.txtv_news_body);
            iv_img_news = findViewById(R.id.iv_img_news);

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