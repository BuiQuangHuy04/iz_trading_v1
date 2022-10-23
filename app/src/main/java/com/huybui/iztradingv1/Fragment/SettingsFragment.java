package com.huybui.iztradingv1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.huybui.iztradingv1.R;

public class SettingsFragment extends Fragment {

    public SettingsFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}