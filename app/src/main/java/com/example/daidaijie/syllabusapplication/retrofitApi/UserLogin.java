package com.example.daidaijie.syllabusapplication.retrofitApi;

import com.example.daidaijie.syllabusapplication.bean.Login;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jarvis yuen
 * Date: 2019/9/28
 */
public interface UserLogin {
    @FormUrlEncoded
    @POST("/credit/api/v2.1/auth")
    Observable<Login> userLogin(@Field("username") String username, @Field("password") String password);
}
