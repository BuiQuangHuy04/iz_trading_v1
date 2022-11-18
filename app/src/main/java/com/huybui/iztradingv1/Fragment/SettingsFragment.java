package com.huybui.iztradingv1.Fragment;

import static com.huybui.iztradingv1.Activity.MainActivity.REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.huybui.iztradingv1.Activity.ChangePasswordActivity;
import com.huybui.iztradingv1.Activity.MainActivity;
import com.huybui.iztradingv1.Activity.SigninActivity;
import com.huybui.iztradingv1.Activity.SplashActivity;
import com.huybui.iztradingv1.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private Uri uri;

    private final Context mContext;
    private CircleImageView userAvaImg;
    private EditText userNameEdt, userMailEdt, userPhoneEdt;
    private Button changeInfoBtn, saveInfoBtn, cancelChangeBtn, changePassBtn, logoutBtn;
    protected ProgressDialog progressDialog;

    public SettingsFragment(Context context) {
        this.mContext = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_settings, container, false);


        userAvaImg = rootview.findViewById(R.id.img_user_ava);
        userNameEdt = rootview.findViewById(R.id.etxt_username);
        userMailEdt = rootview.findViewById(R.id.etxt_usermail);
        userPhoneEdt = rootview.findViewById(R.id.etxt_userphone);
        changeInfoBtn = rootview.findViewById(R.id.btn_change_info);
        saveInfoBtn = rootview.findViewById(R.id.btn_save_info);
        cancelChangeBtn = rootview.findViewById(R.id.btn_cancel);
        changePassBtn = rootview.findViewById(R.id.btn_change_pass);
        logoutBtn = rootview.findViewById(R.id.btn_logout);

        disableEdt();
        saveInfoBtn.setVisibility(View.GONE);
        cancelChangeBtn.setVisibility(View.GONE);

        getUser();

        rootview.findViewById(R.id.cardview_phone).setVisibility(View.GONE);

        changeInfoBtn.setOnClickListener(view -> changeInfo());
        changePassBtn.setOnClickListener(view -> startActivity(new Intent(mContext, ChangePasswordActivity.class)));
        logoutBtn.setOnClickListener(view -> logOut());

        return rootview;
    }

    public void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photourl = user.getPhotoUrl();
        if (name == null) {
            userNameEdt.setVisibility(View.GONE);
        } else {
            userNameEdt.setVisibility(View.VISIBLE);
            userNameEdt.setText(name);
            userMailEdt.setText(email);
            Picasso.get()
                .load(photourl)
                .placeholder(R.mipmap.user_ava_default_round)
                .into(userAvaImg);
        }
    }

    private void changeInfo() {
        updateUiBeforeChange();

        userAvaImg.setOnClickListener(view -> requestPermission());


        saveInfoBtn.setOnClickListener(view -> {
            disableEdt();
            updateUiAfterChange();
            updateUserInfo();
        });

        cancelChangeBtn.setOnClickListener(view -> {
            disableEdt();
            updateUiAfterChange();
            getUser();
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    public void requestPermission() {
        MainActivity activity = (MainActivity) getActivity();

        if (activity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            activity.openGallery();
            return;
        }

        if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            activity.openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
            requireActivity().requestPermissions(permissions, REQUEST_CODE);
        }
    }

    private void disableEdt() {
        userNameEdt.setEnabled(false);
        userPhoneEdt.setEnabled(false);
        userMailEdt.setEnabled(false);
        userAvaImg.setClickable(false);
    }

    private void updateUiBeforeChange() {
        changePassBtn.setEnabled(false);
        logoutBtn.setEnabled(false);
        userNameEdt.setEnabled(true);
        userMailEdt.setEnabled(true);
        userPhoneEdt.setEnabled(true);
        saveInfoBtn.setVisibility(View.VISIBLE);
        cancelChangeBtn.setVisibility(View.VISIBLE);
        changeInfoBtn.setVisibility(View.INVISIBLE);
    }

    private void updateUiAfterChange() {
        changePassBtn.setEnabled(true);
        logoutBtn.setEnabled(true);
        userNameEdt.setEnabled(true);
        userMailEdt.setEnabled(true);
        userPhoneEdt.setEnabled(true);
        saveInfoBtn.setVisibility(View.GONE);
        cancelChangeBtn.setVisibility(View.GONE);
        changeInfoBtn.setVisibility(View.VISIBLE);
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private void updateUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(mContext);

        if (user == null) {
            return;
        }

        progressDialog.show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
            .setDisplayName(userNameEdt.getText().toString().trim())
            .setPhotoUri(uri)
            .build();

        user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext,"Cập nhật thông tin thành công!",Toast.LENGTH_SHORT).show();
                getUser();
                progressDialog.dismiss();
            } else {
                new SigninActivity().alert("Vui lòng đăng nhập lại",mContext);
                startActivity(new Intent(mContext,SigninActivity.class));
            }
        });

        String newEmail = userMailEdt.getText().toString().trim();
        String oldEmail = user.getEmail();

        if (!Objects.equals(oldEmail, newEmail)) {
            user.updateEmail(newEmail).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(mContext,"Cập nhật email thành công!",Toast.LENGTH_SHORT).show();
                    getUser();
                    progressDialog.dismiss();
                } else {
                    new SigninActivity().alert("Vui lòng đăng nhập lại",mContext);
                    startActivity(new Intent(mContext,SigninActivity.class));
                }
            });
        }
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(mContext, SplashActivity.class));
    }
}