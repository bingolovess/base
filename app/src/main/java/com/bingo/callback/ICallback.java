package com.bingo.callback;
import com.bingo.bean.BodyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 * 重新包装callback类
 */

public abstract class ICallback<T> implements Callback<BodyResponse<T>> {
    @Override
    public void onResponse(Call<BodyResponse<T>> call, Response<BodyResponse<T>> response) {
        BodyResponse<T> result = response.body();
        if (result != null) {
            if (result.code == BodyResponse.CODE_SUCCESS) {
                //返回成功结果
                onSuccess(result, result.data);
            } else {
                //解析错误码
                onFail("错误码:" + result.code);
            }
        } else {
            onFail("网络异常！");
        }
    }

    @Override
    public void onFailure(Call<BodyResponse<T>> call, Throwable t) {
        onFail(t.getMessage());
    }

    protected abstract void onSuccess(BodyResponse<T> result, T data);

    protected abstract void onFail(String errMsg);
}
