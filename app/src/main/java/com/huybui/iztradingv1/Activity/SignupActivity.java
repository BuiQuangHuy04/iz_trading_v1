package com.huybui.iztradingv1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.huybui.iztradingv1.R;

public class SignupActivity extends AppCompatActivity {

    private EditText edtMail, edtPass;
    private Button mSignupBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtMail = findViewById(R.id.etxtEmail);
        edtPass = findViewById(R.id.etxtPassword);
        mSignupBtn = findViewById(R.id.btn_sign_up);

        edtPass.setOnClickListener (view -> {
            edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            try {
                new Handler().postDelayed(()-> edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance()),1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        progressDialog = new ProgressDialog(this);
        mSignupBtn.setOnClickListener(view -> onClickSignup());
    }

    private void onClickSignup() {
        try {
            String strEmail = edtMail.getText().toString().trim();
            String strPass = edtPass.getText().toString().trim();
            if (strEmail.matches("") ||
                    strPass.matches("")) {
                new SigninActivity().alert("Không được để trống thông tin!", this);
            } else {
                if (strPass.length() < 6) {
                    new SigninActivity().alert("Mật khẩu cần ít nhất 6 ký tự!", this);
                }
                if (!strEmail.contains("@")) {
                    new SigninActivity().alert("Không đúng định dạng mail!", this);
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();

                progressDialog.show();

                auth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                        finishAffinity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}