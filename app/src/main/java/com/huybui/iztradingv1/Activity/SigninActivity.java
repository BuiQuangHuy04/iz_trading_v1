package com.huybui.iztradingv1.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.huybui.iztradingv1.R;

public class SigninActivity extends AppCompatActivity {

    private EditText edtMail, edtPass;
    private Button btnSignin;
    private TextView tvSignup, tvForgotPass;
    private ImageButton ibtnShowPass;
    private ImageButton ibtnHidePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtMail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        tvForgotPass = findViewById(R.id.btn_forgot_pass);
        btnSignin = findViewById(R.id.btn_sign_in);
        tvSignup = findViewById(R.id.btn_sign_up);
        ibtnShowPass = findViewById(R.id.ibtn_show_password);
        ibtnHidePass = findViewById(R.id.ibtn_hide_password);

        ibtnHidePass.setVisibility(View.INVISIBLE);

        tvForgotPass.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, ForgotPassActivity.class)));

        ibtnShowPass.setOnClickListener(view -> {
            ibtnShowPass.setVisibility(View.INVISIBLE);
            ibtnHidePass.setVisibility(View.VISIBLE);
            edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        });

        ibtnHidePass.setOnClickListener(view -> {
            ibtnHidePass.setVisibility(View.INVISIBLE);
            ibtnShowPass.setVisibility(View.VISIBLE);
            edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        });

        btnSignin.setOnClickListener(view -> Signin());

        tvSignup.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, SignupActivity.class)));
    }

    private void Signin() {
        try {
            ProgressDialog progressDialog = new ProgressDialog(this);
            String strEmail = edtMail.getText().toString().trim();
            String strPass = edtPass.getText().toString().trim();

            if (strEmail.equals("") | strPass.equals("")) {
                alert("Không được để trống thông tin!", this);
            } else {
                progressDialog.show();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SigninActivity.this, "Authentication successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Toast.makeText(SigninActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alert(String strErr, Context context) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

        dlgAlert.setMessage(strErr);
        dlgAlert.setTitle("Cảnh báo");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }


}