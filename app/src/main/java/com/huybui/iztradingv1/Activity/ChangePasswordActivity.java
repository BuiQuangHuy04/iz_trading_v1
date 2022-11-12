package com.huybui.iztradingv1.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.huybui.iztradingv1.Fragment.SignalsFragment;
import com.huybui.iztradingv1.R;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText newPassEdt, reNewPassEdt;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPassEdt = findViewById(R.id.etxt_new_pass);
        reNewPassEdt = findViewById(R.id.etxt_new_pass_reenter);
        Button updatePassBtn = findViewById(R.id.btn_update_pass);

        newPassEdt.setOnClickListener (view -> {
            newPassEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            try {
                new Handler().postDelayed(()-> newPassEdt.setTransformationMethod(PasswordTransformationMethod.getInstance()),1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        reNewPassEdt.setOnClickListener (view -> {
            reNewPassEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            try {
                new Handler().postDelayed(()-> reNewPassEdt.setTransformationMethod(PasswordTransformationMethod.getInstance()),1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        updatePassBtn.setOnClickListener(view -> updatePass());
    }

    private void updatePass() {
        progressDialog = new ProgressDialog(this);

        String newPass1 = newPassEdt.getText().toString().trim();
        String newPass2 = reNewPassEdt.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            return;
        }

        if (newPass1.matches("") | newPass2.matches("")) {
            new SigninActivity().alert("Không được để trống!",this);
            return;
        }

        if (newPass1.length() < 6) {
            new SigninActivity().alert("Mật khẩu cần ít nhất 6 ký tự!",this);
            return;
        }

        if (!newPass1.equals(newPass2)) {
            new SigninActivity().alert("Mật khẩu nhập lại không đúng!",this);
            return;
        }

        progressDialog.show();
        user.updatePassword(newPass1).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ChangePasswordActivity.this,"Đổi mật khẩu thành công!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePasswordActivity.this,SigninActivity.class));
            } else {
                new SigninActivity().alert("Vui lòng đăng nhập lại",this);
                startActivity(new Intent(ChangePasswordActivity.this,SigninActivity.class));
            }
            progressDialog.dismiss();
        });

    }
}