package com.huybui.iztradingv1.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huybui.iztradingv1.API.NewsService;
import com.huybui.iztradingv1.Model.News;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public MutableLiveData<List<News>> newsList;
    public LiveData<List<News>> getNews() {
        if (newsList == null) {
            newsList = new MutableLiveData<>();
            loadNews();
        }
        return newsList;
    }

    public void  loadNews() {
        NewsService.newsService.getALlNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.body() != null) {
                    newsList.setValue((response.body()));

//                    System.out.println("before: "+newsList.getValue());

                    Collections.reverse(Objects.requireNonNull(newsList.getValue()));

//                    System.out.println("after: "+newsList.getValue());
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                System.out.println("get news fail");
            }
        });
    }

}
