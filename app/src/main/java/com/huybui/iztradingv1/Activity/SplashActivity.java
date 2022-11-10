package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.huybui.iztradingv1.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> nextActivity(),2000L);
    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            //Chua login
            startActivity(new Intent(SplashActivity.this, SigninActivity.class));
        } else
            //Da login
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}

