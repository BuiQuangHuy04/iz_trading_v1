package com.huybui.iztradingv1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.Activity.MainActivity;
import com.huybui.iztradingv1.Adapter.OrderAdapter;
import com.huybui.iztradingv1.R;

public class SignalsFragment extends Fragment {

    public SignalsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_signals, container, false);

        RecyclerView rcvOrder = rootview.findViewById(R.id.recycler_item);

        rcvOrder.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        System.out.println("Signals fragment is loading data");

        MainActivity mainActivity = (MainActivity) getActivity();


        OrderAdapter mOrderAdapter = new OrderAdapter(rootview.getContext());

        mOrderAdapter.setData(mainActivity.orders);
        rcvOrder.setAdapter(mOrderAdapter);

        return rootview;
    }

}