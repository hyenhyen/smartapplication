package com.monad.kpu.Retrofit;

import com.monad.kpu.Model.ResultModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by temp on 2017. 5. 25..
 */

public interface RetrofitService {
    @GET("/api/{score}")
    Call<List<ResultModel>> loadResult();

    @GET("/api?{data}")
    Call<List<ResultModel>> detailResult();
}
