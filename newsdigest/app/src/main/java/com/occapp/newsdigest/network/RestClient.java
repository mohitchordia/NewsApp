package com.occapp.newsdigest.network;


import com.occapp.newsdigest.AppApplication;
import com.occapp.newsdigest.BuildConfig;
import com.occapp.newsdigest.network.interceptors.ErrorHandlerInterceptor;
import com.occapp.newsdigest.network.interceptors.HeaderModifierInterceptor;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1. created RestClient to setup once, which will improve performance and help to make a server call;
 * 2. added LogInterceptor only into debug mode;
 * 3. added ErrorHandlerInterceptor
 * 4. added HeaderModifier
 */

public class RestClient {
    private NewsApiService apiService;

    @Inject
    public RestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.interceptors().add(new HeaderModifierInterceptor());
        httpClient.interceptors().add(new ErrorHandlerInterceptor(AppApplication.getContext()));
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(logging);
        }

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(NewsApiService.BASE_URL)//passing API_URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())//passing OkHttpClient object
                .build();
        apiService = restAdapter.create(NewsApiService.class);
    }


    //double checked locking singleTon Design.
    public NewsApiService getApiService() {
        if (apiService == null) {
            synchronized (RestClient.class) {
                if (apiService == null)
                    new RestClient();
            }
        }
        return apiService;
    }

}
