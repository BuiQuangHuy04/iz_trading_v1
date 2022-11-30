package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.huybui.iztradingv1.Model.News;
import com.huybui.iztradingv1.R;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.Date;

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
            TextView txtv_news_tag = findViewById(R.id.txtv_news_tag);
            TextView txtv_news_date = findViewById(R.id.txtv_news_date);
            TextView txtv_news_body = findViewById(R.id.txtv_news_body);
            ImageView iv_img_news = findViewById(R.id.iv_img_news);

            Timestamp ts = new Timestamp(Long.parseLong(news.getDate())*1000);

            Date date = new Date(ts.getTime());

            String sDate = String.valueOf(date).substring(0,20);

            Picasso.get()
                    .load(news.getThumbnail())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv_img_news);
            txtv_news_title.setText(news.getTitle());
            txtv_news_tag.setText(news.getTag());
            txtv_news_date.setText(sDate);
            txtv_news_body.setText(news.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}