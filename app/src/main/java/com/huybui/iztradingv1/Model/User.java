package com.huybui.iztradingv1.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    protected String account;
    protected String password;
    protected String name;
    protected String email;
    protected String phone;

    public User() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public Map<String, String> toMap(User u) {

        HashMap<String, String> userHashMap = new HashMap<>();
        userHashMap.put("account", u.getAccount());
        userHashMap.put("password", u.getPassword());
        userHashMap.put("name", u.getName());
        userHashMap.put("phone", u.getPhone());
        userHashMap.put("email", u.getEmail());
        return userHashMap;
    }

}
