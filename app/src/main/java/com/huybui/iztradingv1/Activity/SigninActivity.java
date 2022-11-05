package com.huybui.iztradingv1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.huybui.iztradingv1.API.UserService;
import com.huybui.iztradingv1.Model.User;
import com.huybui.iztradingv1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private EditText mAccountEdittxt, mPasswordEdittxt;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAccountEdittxt = findViewById(R.id.etxtAccount);
        mPasswordEdittxt = findViewById(R.id.etxtPassword);
        Button mSigninBtn = findViewById(R.id.btnSignIn);
        Button mSignupBtn = findViewById(R.id.btnSignUp);
        ImageButton mShowPassImgbtn = findViewById(R.id.ibtnShowPassword);
        ImageButton mHidePassImgbtn = findViewById(R.id.ibtnHidePassword);

        mHidePassImgbtn.setVisibility(View.INVISIBLE);

        UserService.userService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null)
                    userList.addAll(response.body());
                else
                    Toast.makeText(SigninActivity.this, "response.body() is null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "get users fail", Toast.LENGTH_SHORT).show();
            }
        });

        mShowPassImgbtn.setOnClickListener(view -> {
            mShowPassImgbtn.setVisibility(View.INVISIBLE);
            mHidePassImgbtn.setVisibility(View.VISIBLE);
            mPasswordEdittxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        });

        mHidePassImgbtn.setOnClickListener(view -> {
            mHidePassImgbtn.setVisibility(View.INVISIBLE);
            mShowPassImgbtn.setVisibility(View.VISIBLE);
            mPasswordEdittxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        });

        mSigninBtn.setOnClickListener(view -> Signin());

        mSignupBtn.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, SignupActivity.class)));
    }

//    private List<User> getListUsers() {
//        List<User> list = new ArrayList<>();
//        UserService.userService.getUsers().enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.body() != null)
//                    list.addAll(response.body());
//                else
//                    Toast.makeText(SigninActivity.this, "response.body() is null", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(SigninActivity.this, "get users fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return list;
//    }

    private void Signin() {
        if (mAccountEdittxt.getText().toString().matches("") || mPasswordEdittxt.getText().toString().matches("")){
            Toast.makeText(SigninActivity.this, "Không được để trống tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
        } else {
            int count = 0;

            for (User u: userList) {
                if (mAccountEdittxt.getText().toString().equals(u.getAccount())) {
                    Toast.makeText(SigninActivity.this, "Tài khoản " + mAccountEdittxt.getText().toString() + " đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SigninActivity.this, MainActivity.class));
                    break;
                } else count++;
            }
            if (count != 0 && SigninActivity.this.getWindow().getDecorView().getRootView().isFocused())
                Toast.makeText(SigninActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
        }
    }
}