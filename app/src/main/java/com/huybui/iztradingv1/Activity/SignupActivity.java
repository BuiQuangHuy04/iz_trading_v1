package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huybui.iztradingv1.API.UserService;
import com.huybui.iztradingv1.Model.User;
import com.huybui.iztradingv1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText mAccountEdittxt, mPasswordEdittxt, mNameEdittxt,mPhoneEdittxt,mEmailEdittxt;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAccountEdittxt = findViewById(R.id.etxtAccount);
        mPasswordEdittxt = findViewById(R.id.etxtPassword);
        mNameEdittxt = findViewById(R.id.etxtName);
        mPhoneEdittxt = findViewById(R.id.etxtPhone);
        mEmailEdittxt = findViewById(R.id.etxtEmail);
        Button mSignupBtn = findViewById(R.id.btnSignUp);

        UserService.userService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null)
                    userList.addAll(response.body());
                else
                    Toast.makeText(SignupActivity.this, "response.body() is null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "get users fail", Toast.LENGTH_SHORT).show();
            }
        });

        mSignupBtn.setOnClickListener(view -> Signup());
    }

    private void Signup() {
        User user = new User();
        user.setName(mNameEdittxt.getText().toString());
        user.setAccount(mAccountEdittxt.getText().toString());
        user.setPassword(mPasswordEdittxt.getText().toString());
        user.setPhone(mPhoneEdittxt.getText().toString());
        user.setEmail(mEmailEdittxt.getText().toString());

        for (User u: userList) {
            if (u.getAccount().equals(user.getAccount())) {
                Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
            } else if (u.getEmail().equals(user.getEmail())) {
                Toast.makeText(SignupActivity.this, "Email đăng ký đã được sử dụng!", Toast.LENGTH_SHORT).show();
            } else if (u.getPhone().equals(user.getPhone())) {
                Toast.makeText(SignupActivity.this, "Số điện thoại đã được sử dụng!", Toast.LENGTH_SHORT).show();
            } else {
                UserService.userService.createUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(SignupActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                break;
            }
        }

    }
}