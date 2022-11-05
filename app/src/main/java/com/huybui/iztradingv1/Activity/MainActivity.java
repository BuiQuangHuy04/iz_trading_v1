package com.huybui.iztradingv1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.SurfaceControl;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.huybui.iztradingv1.Fragment.NewsFragment;
import com.huybui.iztradingv1.Fragment.SettingsFragment;
import com.huybui.iztradingv1.Fragment.SignalsFragment;
import com.huybui.iztradingv1.Model.News;
import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.Model.User;
import com.huybui.iztradingv1.R;
import com.huybui.iztradingv1.Service.NotificationService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SignalsFragment signalsFragment = new SignalsFragment(this);
    NewsFragment newsFragment = new NewsFragment();
    SettingsFragment settingsFragment = new SettingsFragment(this);

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        initUiListener();
    }

    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
    }

    private void initUiListener() {
        try {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.mitem_signals:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
                        return true;
                    case R.id.mitem_news:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, newsFragment).commit();
                        return true;
                    case R.id.mitem_settings:
                        try {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, settingsFragment).commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
                }
                return false;
            });

//            Bundle bundle = getIntent().getExtras();
//            if (bundle == null) return;
//
//            Order order = (Order) bundle.getSerializable("signal");
//
//            NotificationService notificationService = new NotificationService(this);
//
//            String title = order.getType() + " " + order.getPair() + " " + order.getPrice();
//
//            String body = "SL: " + order.getSl() + "\nTP: " + order.getTp();
//
//            notificationService.sendNoti(title, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}