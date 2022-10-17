package com.huybui.iztradingv1.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huybui.iztradingv1.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderService {
    Gson gson = new GsonBuilder().setDateFormat("HH:mm:ss").create();

    //https://bot-trade-mt4-v1.herokuapp.com/api/orders/getOne/634050c06bfdb73d6cfb62ea
    OrderService orderService = new Retrofit.Builder()
            .baseUrl("https://bot-trade-mt4-v1.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(OrderService.class);

    @GET("api/orders/getAll")
    Call<List<User>> getOrders();

    @GET("api/orders/getOne/{id}")
    Call<User> getOrderByID(@Path("id") String id);

}
