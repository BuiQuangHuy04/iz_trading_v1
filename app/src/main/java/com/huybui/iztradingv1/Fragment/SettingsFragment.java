package com.huybui.iztradingv1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.huybui.iztradingv1.Activity.MainActivity;
import com.huybui.iztradingv1.Activity.SplashActivity;
import com.huybui.iztradingv1.Model.User;
import com.huybui.iztradingv1.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private Context mContext;
    public CircleImageView userAvaImg;
    public EditText userNameEdt, userMailEdt, userPhoneEdt;
    public Button logoutBtn, changeInfoBtn;

    public SettingsFragment(Context context){
        this.mContext = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.fragment_settings, container, false);

        try {
            userAvaImg = rootview.findViewById(R.id.img_user_ava);
            userNameEdt = rootview.findViewById(R.id.etxt_username);
            userMailEdt = rootview.findViewById(R.id.etxt_usermail);
            userPhoneEdt = rootview.findViewById(R.id.etxt_userphone);
            changeInfoBtn = rootview.findViewById(R.id.btn_change_info);
            logoutBtn = rootview.findViewById(R.id.btn_logout);

//            userNameEdt.setEnabled(false);
//            userMailEdt.setEnabled(false);
//            userPhoneEdt.setEnabled(false);

            disableEditText(userNameEdt);
            disableEditText(userMailEdt);
            disableEditText(userPhoneEdt);

            getUser();

            changeInfoBtn.setOnClickListener(view -> changeInfo());

            logoutBtn.setOnClickListener(view -> logOut());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootview;
    }

    private void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            System.out.println("user is null");
            return;
        }

        System.out.println(user);

        String name = user.getDisplayName();
        String email = user.getEmail();
        String phone = user.getPhoneNumber();
        Uri photoUrl = user.getPhotoUrl();

        System.out.println("name: " + name);
        System.out.println("email: " + email);
        System.out.println("phone: " + phone);
        System.out.println("photoUrl: " + photoUrl);

        userNameEdt.setText(name);
        userMailEdt.setText(email);
        userPhoneEdt.setText(phone);

    }

    private void changeInfo() {
        enableEditText(userNameEdt);
        enableEditText(userMailEdt);
        enableEditText(userPhoneEdt);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(mContext, SplashActivity.class));
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

}