package com.huybui.iztradingv1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huybui.iztradingv1.Fragment.NewsFragment;
import com.huybui.iztradingv1.Fragment.SettingsFragment;
import com.huybui.iztradingv1.Fragment.SignalsFragment;
import com.huybui.iztradingv1.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    SignalsFragment signalsFragment = new SignalsFragment();
    NewsFragment newsFragment = new NewsFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new SignalsFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) this);
        bottomNavigationView.setSelectedItemId(R.id.mitem_signals);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mitem_signals:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, signalsFragment).commit();
                return true;
            case R.id.mitem_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, newsFragment).commit();
                return true;
            case R.id.mitem_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                return true;
        }
        return false;    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}