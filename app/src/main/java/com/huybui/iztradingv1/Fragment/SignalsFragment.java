package com.huybui.iztradingv1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huybui.iztradingv1.API.OrderService;
import com.huybui.iztradingv1.Adapter.OrderAdapter;
import com.huybui.iztradingv1.Model.Order;
import com.huybui.iztradingv1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignalsFragment extends Fragment {

    private final List<Order> orderList = new ArrayList<>();

    public SignalsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootview = inflater.inflate(R.layout.fragment_signals, container, false);

        RecyclerView rcvOrder = rootview.findViewById(R.id.recycler_item);

        rcvOrder.setLayoutManager(new GridLayoutManager(rootview.getContext(),1));

        OrderService.orderService.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                if (response.body() != null) {
                    orderList.addAll(response.body());

                    orderList.removeIf(o->{
                        try {
                            return o.getClass().getDeclaredField("ticket").get(o) == null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    });

                    Collections.reverse(orderList);
                } else
                    Toast.makeText(rootview.getContext(), "response.body() is null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Toast.makeText(rootview.getContext(), "get orders fail", Toast.LENGTH_SHORT).show();
            }
        });

        OrderAdapter mOrderAdapter = new OrderAdapter(rootview.getContext());

        mOrderAdapter.setData(orderList);
        rcvOrder.setAdapter(mOrderAdapter);

        return rootview;
    }

}