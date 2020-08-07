package com.occapp.newsdigest.network.interceptors;

import android.content.Context;
import android.util.Log;


import com.occapp.newsdigest.Utils;
import com.occapp.newsdigest.network.NoConnectivityException;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ErrorHandlerInterceptor implements Interceptor {
    private static final String TAG = ErrorHandlerInterceptor.class.getName();
    private final Context context;

    public ErrorHandlerInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (!Utils.isNetworkAvailable(context)) {
            Log.d(TAG, "errorHandler:internet issue");
            EventBus.getDefault().post(ErrorCode.INTERNET_ERROR);
            throw new NoConnectivityException(context);
        }

        Response response = chain.proceed(originalRequest);
        Boolean isSuccess = response.code() / 100 == 2 || response.code() / 100 == 3;
        if (isSuccess) {
            return response;
        }

        Log.d(TAG, "responseCode:" + response.code());
        EventBus.getDefault().post(response.code());

        return response;
    }
}
