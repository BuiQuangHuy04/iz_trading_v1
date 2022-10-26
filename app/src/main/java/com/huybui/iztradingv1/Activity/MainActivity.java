package com.huybui.iztradingv1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.huybui.iztradingv1.API.OrderService;
import com.huybui.iztradingv1.Fragment.NewsFragment;
import com.huybui.iztradingv1.Fragment.SettingsFragment;
import com.huybui.iztradingv1.Fragment.SignalsFragment;
import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    SignalsFragment signalsFragment = new SignalsFragment();
    NewsFragment newsFragment = new NewsFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, new SignalsFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

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
            }
            return false;
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mitem_news:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, newsFragment).commit();
                    break;
                case R.id.mitem_settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, settingsFragment).commit();
                    break;
                case R.id.mitem_signals:
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
            }
        });

//        bottomNavigationView.setSelectedItemId(R.id.mitem_signals);
    }

//    public List<Order> getSignals() {
//        List<Order> orderList = new ArrayList<>();
//        OrderService.orderService.getOrders().enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
//                if (response.body() != null) {
//                    orderList.addAll(response.body());
//
//                    orderList.removeIf(o->{
//                        try {
//                            return o.getClass().getDeclaredField("ticket").get(o) == null;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return false;
//                    });
//
//                    Collections.reverse(orderList);
//                } else
//                    Toast.makeText(MainActivity.this, "response.body() is null", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
//                Toast.makeText(MainActivity.this, "get orders fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return orderList;
//    }
}