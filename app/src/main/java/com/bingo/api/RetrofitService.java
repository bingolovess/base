package com.bingo.api;

import com.bingo.bean.BodyResponse;
import com.bingo.bean.User;
import com.bingo.bean.User2;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 */

public interface RetrofitService {
    //单纯使用retrofit接口定义
    @GET("/")
    Call<BodyResponse<List<User2>>> getUser();

    @GET("/")
    Call<User> getUser2();
    @GET("/")
    Call<BodyResponse<List<User2>>> getUser3();
    //使用retrofit+RxAndroid的接口定义
   // @GET("news/latest")
   // Observable<ZhiHuDaily> getZhihuDaily();

}
