package com.dthealth.util;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Api {
    /**
     * for interaction platform
     * */
    @GET("user/findAllByPage")
    Call<ResponseBody> findAllByPage(@QueryMap Map<String, Integer> options);

    @GET("digitalUserModel/findDigitalUserModelByUserId")
    Call<ResponseBody> findDigitalUserModelByUserId(@QueryMap Map<String, String> map);

    /**
     * for system access platform
     * */
    @FormUrlEncoded
    @POST("do-sign-in")
    Call<ResponseBody> login(@Field("userAccount") String userAccount, @Field("password") String password);
}
