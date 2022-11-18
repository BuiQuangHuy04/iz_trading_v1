package com.huybui.iztradingv1.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.huybui.iztradingv1.Fragment.NewsFragment;
import com.huybui.iztradingv1.Fragment.SettingsFragment;
import com.huybui.iztradingv1.Fragment.SignalsFragment;
import com.huybui.iztradingv1.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;

    protected BottomNavigationView bottomNavigationView;
    protected SignalsFragment signalsFragment = new SignalsFragment(this);
    protected NewsFragment newsFragment = new NewsFragment();
    protected SettingsFragment settingsFragment = new SettingsFragment(this);

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK) {
            Intent intent = result.getData();
            if (intent == null) {
                return;
            }
            Uri uri = intent.getData();
            settingsFragment.setUri(uri);

            CircleImageView imageView = findViewById(R.id.img_user_ava);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user == null) {
                return;
            }

            Picasso.get()
                .load(uri)
                .placeholder(R.mipmap.user_ava_default_round)
                .into(imageView);
        }
    });

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initUi();
        initUiListener();
    }

    private void initUi() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @SuppressLint("NonConstantResourceId")
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
                            getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, settingsFragment).commit();
                        return true;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fpageview, signalsFragment).commit();
                }
                return false;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AtomicBoolean checkGrant = new AtomicBoolean(false);

        Arrays.stream(grantResults).forEach( i -> {
            if(i == PackageManager.PERMISSION_GRANTED) {
                checkGrant.set(true);
            }
        });

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && checkGrant.get()) {
                openGallery();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"Chọn ảnh"));
    }
}