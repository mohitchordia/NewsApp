package com.occapp.newsdigest.network;


import com.occapp.newsdigest.AppApplication;
import com.occapp.newsdigest.network.interceptors.ErrorHandlerInterceptor;
import com.occapp.newsdigest.network.interceptors.HeaderModifierInterceptor;

import javax.inject.Inject;

import butterknife.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient { private NewsApi apiService;

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
                .baseUrl(NewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        apiService = restAdapter.create(NewsApi.class);
    }


    //double checked locking singleTon Design.
    public NewsApi getApiService() {
        if (apiService == null) {
            synchronized (RestClient.class) {
                if (apiService == null)
                    new RestClient();
            }
        }
        return apiService;
    }

}
