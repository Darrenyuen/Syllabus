package com.example.daidaijie.syllabusapplication.schoolDymatic.personal;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.bean.SmmsResult;
import com.example.daidaijie.syllabusapplication.bean.UpdateUserBody;
import com.example.daidaijie.syllabusapplication.retrofitApi.PushImageToSmmsApi;
import com.example.daidaijie.syllabusapplication.retrofitApi.PushPostApi;
import com.example.daidaijie.syllabusapplication.retrofitApi.UpdateUserApi;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.RetrofitUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/22.
 */

public class PersonalModel implements IPersonalModel {

    private String TAG = this.getClass().getSimpleName();

    UpdateUserApi mUpdateUserApi;

    IUserModel mIUserModel;

    PushPostApi pushPostApi;

    PushImageToSmmsApi pushImageToSmmsApi;

    public PersonalModel(UpdateUserApi updateUserApi, PushPostApi pushPostApi, IUserModel userModel, PushImageToSmmsApi pushImageToSmmsApi) {
        mUpdateUserApi = updateUserApi;
        this.pushPostApi = pushPostApi;
        mIUserModel = userModel;
        this.pushImageToSmmsApi = pushImageToSmmsApi;
    }


    @Override
    public Observable<Void> updateUserInfo(String nickName, String profile, @Nullable String img) {
        UpdateUserBody updateUserBody = new UpdateUserBody();
        updateUserBody.id = mIUserModel.getUserInfoNormal().getUser_id();
        updateUserBody.uid = mIUserModel.getUserInfoNormal().getUser_id();
        updateUserBody.token = mIUserModel.getUserInfoNormal().getToken();
        updateUserBody.nickname = nickName;
        updateUserBody.profile = profile;
        if (img != null) {
            updateUserBody.image = img;
        }


        return mUpdateUserApi.update(updateUserBody)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpResult<Void>, Observable<Void>>() {
                    @Override
                    public Observable<Void> call(HttpResult<Void> voidHttpResult) {
                        if (RetrofitUtil.isSuccessful(voidHttpResult)) {
                            return Observable.just(voidHttpResult.getData());
                        } else {
                            return Observable.error(new Throwable(voidHttpResult.getMessage()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void postPhotoToSmms(@Nullable String headImage, final OnPostPhotoCallBack onPostPhotoCallBack) {
        //如果没有选择头像，那么直接返回
        if (headImage == null) {
            onPostPhotoCallBack.onSuccess(null);
            return;
        } else {
            File file = new File(headImage);
            Log.d(TAG, "postPhotoToSmms: " + file.getName());
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("smfile", file.getName(), requestFile);
            pushImageToSmmsApi.pushImage(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SmmsResult>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            onPostPhotoCallBack.onFail("图片上传失败");
                        }

                        @Override
                        public void onNext(SmmsResult smmsResult) {
                            Log.d(TAG, "onNext: " + smmsResult.getStatus());
                            if (smmsResult.getStatus().equals("success")){
                                Log.d(TAG, "onNext: " + smmsResult.getData().getPicUrl());
                                onPostPhotoCallBack.onSuccess(smmsResult.getData().getPicUrl());
                            }
                        }
                    });
        }
    }
}
