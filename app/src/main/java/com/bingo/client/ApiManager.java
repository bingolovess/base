package com.bingo.client;

import com.bingo.Constants;
import com.bingo.api.RetrofitService;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 */

public class ApiManager {
    private RetrofitService service;
    private static ApiManager sApiManager;
    private OkHttpClient client;

    //获取ApiManager的单例
    public static ApiManager getInstence() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    private ApiManager() {
        //使用拦截器
        client = new OkHttpClient.Builder()
                //添加应用拦截器
                .addInterceptor(new HttpInterceptor())
                //添加网络拦截器
                //.addNetworkInterceptor(new HttpInterceptor())
                .retryOnConnectionFailure(true)//设置重连
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    //将client与retrofit关联
                    .client(client)
                    //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //到这一步创建完成
            service = retrofit.create(RetrofitService.class);
        }
    }

    public RetrofitService getService() {
        return service;
    }
}
