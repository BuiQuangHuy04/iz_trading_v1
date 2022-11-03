package com.huybui.iztradingv1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Adapter.SignalsAdapter;
import com.huybui.iztradingv1.R;
import com.huybui.iztradingv1.ViewModel.SignalsViewModel;

public class SignalsFragment extends Fragment {

    public SignalsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_signals, container, false);

        SignalsViewModel signalsViewModel = new ViewModelProvider(this).get(SignalsViewModel.class);

        RecyclerView rcvOrder = rootview.findViewById(R.id.item_signal);

        rcvOrder.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        SignalsAdapter mSignalsAdapter = new SignalsAdapter(rootview.getContext());

        signalsViewModel.getOrders().observe(getViewLifecycleOwner(), mSignalsAdapter::setData);

        rcvOrder.setAdapter(mSignalsAdapter);

        return rootview;
    }
}