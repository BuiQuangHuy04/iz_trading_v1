package com.huybui.iztradingv1.Service;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huybui.iztradingv1.Activity.SignupActivity;
import com.huybui.iztradingv1.Model.User;


public class UserService {

    public void pushNewUser(User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child(user.getAccount()).setValue(user);
        Toast.makeText(new SignupActivity().getBaseContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
    }

}
