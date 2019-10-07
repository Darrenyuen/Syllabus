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
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;
import com.example.daidaijie.syllabusapplication.util.RetrofitUtil;

import java.io.File;

import javax.inject.Inject;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;
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
        }

        //将头像传到smms
        Observable.just(headImage)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    @Override
                    public File call(String s) {
                        return new File(s);
                    }
                })
                .map(new Func1<File, SmmsResult>() {
                    SmmsResult mSmmsResult;
                    @Override
                    public SmmsResult call(File file) {
                        Log.d(TAG, "call: " + file.getName());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

                        pushImageToSmmsApi.pushImage(body)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<SmmsResult>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.d(TAG, "onError: ");
                                    }

                                    @Override
                                    public void onNext(SmmsResult smmsResult) {
                                        Log.d(TAG, "onNext: " + smmsResult.isSuccess());
                                        mSmmsResult = smmsResult;
                                    }
                                });
                        return mSmmsResult;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SmmsResult>() {

                    String url;

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + "last");
                        onPostPhotoCallBack.onFail("图片上传失败");
                    }

                    @Override
                    public void onNext(SmmsResult smmsResult) {
                        Log.d(TAG, "onNext: " + smmsResult.isSuccess());
                        if (smmsResult.isSuccess()) {
                            SmmsResult.Data data = smmsResult.data;
                            url = data.getUrl();
                            Log.d(TAG, "onNext: " + url);
                            onPostPhotoCallBack.onSuccess(url);
                        }
//                        final BmobFile bmobFile = new BmobFile(file);
//                        bmobFile.uploadblock(new UploadFileListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if (e == null) {
//                                    url = bmobFile.getFileUrl();
//                                    onPostPhotoCallBack.onSuccess(url);
//                                }
//                            }
//                        });

                    }
                });

    }
}
