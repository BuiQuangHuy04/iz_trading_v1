package com.huybui.iztradingv1.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huybui.iztradingv1.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    Gson gson = new GsonBuilder().setDateFormat("DD-MM-YYYY HH:mm:ss").create();

    //https://bot-trade-mt4-v1.herokuapp.com/api/users/getOne/634bc3db41b2d0f452c0fad6
    UserService userService = new Retrofit.Builder()
            .baseUrl("https://bot-trade-mt4-v1.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(UserService.class);

    @GET("api/users/getAll")
    Call<List<User>> getUsers();

    @GET("api/users/getOne/{id}")
    Call<User> getUserByID(@Path("id") String id);

    @POST("api/users/postOne")
    Call<User> createUser(@Body User user);
}
