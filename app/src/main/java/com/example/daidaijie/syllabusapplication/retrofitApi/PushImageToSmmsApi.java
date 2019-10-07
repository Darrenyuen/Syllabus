package com.example.daidaijie.syllabusapplication.retrofitApi;

import com.example.daidaijie.syllabusapplication.bean.SmmsResult;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jarvis yuen
 * Date: 2019/10/7
 */
public interface PushImageToSmmsApi {
    @Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("api/upload")
    Observable<SmmsResult> pushImage(@Part MultipartBody.Part file);
}