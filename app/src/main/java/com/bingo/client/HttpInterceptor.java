package com.bingo.client;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 */

public class HttpInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Request request;
        HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                //.addQueryParameter("token", "")
                //.addQueryParameter("userId", "")
                .build();
        request = originalRequest.newBuilder().url(modifiedUrl).build();
        return chain.proceed(request);
       // return response;
    }
}
