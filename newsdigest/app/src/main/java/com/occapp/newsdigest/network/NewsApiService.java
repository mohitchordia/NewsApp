package com.occapp.newsdigest.network;

import com.occapp.newsdigest.network.model.NewsModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    String KEY = "f35ebeee93bd47e5b2dea8ee349a0d23";
    String BASE_URL = "https://newsapi.org";
    String ARTICLES_URL = "/v2/top-headlines?country=in&category=business&apiKey=" + KEY;

    @GET(ARTICLES_URL)
    Call<NewsModel> getAllArticles(@Query("page") int pageIndex);
}
