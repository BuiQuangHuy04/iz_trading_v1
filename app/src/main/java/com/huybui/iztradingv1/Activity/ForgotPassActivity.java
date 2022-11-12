package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.huybui.iztradingv1.R;

public class ForgotPassActivity extends AppCompatActivity {

    private TextView emailEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailEdt = findViewById(R.id.etxt_email);
        Button confirmBtn = findViewById(R.id.btn_confirm);

        confirmBtn.setOnClickListener(view -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = emailEdt.getText().toString().trim();

            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Kiểm tra email!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, SigninActivity.class));
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra! Thử lại", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}