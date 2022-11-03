package com.huybui.iztradingv1.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huybui.iztradingv1.API.OrderService;
import com.huybui.iztradingv1.Model.Order;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignalsViewModel extends ViewModel {

    public MutableLiveData<List<Order>> orders;
    public LiveData<List<Order>> getOrders() {
        if (orders == null) {
            orders = new MutableLiveData<>();
            loadOrders();
        }
        return orders;
    }

    public void  loadOrders() {
        OrderService.orderService.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                if (response.body() != null) {
                    orders.setValue((response.body()));

//                    System.out.println("before: "+orders.getValue());

                    Collections.reverse(Objects.requireNonNull(orders.getValue()));

//                    System.out.println("after: "+orders.getValue());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                System.out.println("get orders fail");
            }
        });
    }

}
