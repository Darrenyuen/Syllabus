package com.example.daidaijie.syllabusapplication.retrofitApi;

import com.example.daidaijie.syllabusapplication.bean.AuthLogin;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * yuan
 * 2019/10/25
 **/
public interface AuthLoginApi {
    @FormUrlEncoded
    @POST("/credit/api/v2.1/auth")
    Observable<AuthLogin> authLogin(@Field("username") String username, @Field("password") String password);
}
