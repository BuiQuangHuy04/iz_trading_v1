package com.huybui.iztradingv1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huybui.iztradingv1.Fragment.NewsFragment;
import com.huybui.iztradingv1.Fragment.SettingsFragment;
import com.huybui.iztradingv1.Fragment.SignalsFragment;
import com.huybui.iztradingv1.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    SignalsFragment signalsFragment = new SignalsFragment();
    NewsFragment newsFragment = new NewsFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();

//        String uri = "mongodb+srv://root:root@cluster0.71wmt.mongodb.net/?retryWrites=true&w=majority";
////        String uri = "mongodb+srv://root:root@cluster0.71wmt.mongodb.net/botSignalForex?authSource=admin&replicaSet=atlas-hpl7wz-shard-0&readPreference=primary&ssl=true";
//
//        MongoClientURI mongoUri  = new MongoClientURI(uri);
//        MongoClient mongoClient = new MongoClient(mongoUri);
//        DB db = mongoClient.getDB("botSignalForex");
//        DBCollection collection = db.getCollection("orders");
//        System.out.println(collection.find().toArray());


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mitem_signals:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
                    return true;
                case R.id.mitem_news:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, newsFragment).commit();
                    return true;
                case R.id.mitem_settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, settingsFragment).commit();
                    return true;
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
            }
            return false;
        });

        bottomNavigationView.setOnItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mitem_news:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, newsFragment).commit();
                    return;
                case R.id.mitem_settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, settingsFragment).commit();
                    break;
                case R.id.mitem_signals:
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
            }
        });
    }
}