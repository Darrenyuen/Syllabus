package com.example.daidaijie.syllabusapplication.retrofitApi;

import com.example.daidaijie.syllabusapplication.bean.SmmsResult;

import okhttp3.MultipartBody;
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
    @Headers("user-agent: Syllabus/2.2.4")
    @Multipart
    @POST("api/upload")
    Observable<SmmsResult> pushImage(@Part MultipartBody.Part part);
}