package com.huybui.iztradingv1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    public List<Order> orders = getOrders();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
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
    }

    public List<Order> getOrders() {
        System.out.println("loading orders");
        List<Order> orders = new ArrayList<>();
        OrderService.orderService.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                if (response.body() != null) {
                    orders.addAll(response.body());

                    orders.removeIf(o->{
                        try {
                            return o.getClass().getDeclaredField("ticket").get(o) == null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    });

                    Collections.reverse(orders);
                } else
                    System.out.println("getting orders");;
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                System.out.println("get orders done");
            }
        });
        return orders;
    }
}