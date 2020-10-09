package com.example.news.network;

import com.example.news.model.News;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apis {

    @GET("/v2/top-headlines")
    Single<News> getNews(@Query("apiKey") String apiKey,@Query("country") String country,@Query("category") String category);
}