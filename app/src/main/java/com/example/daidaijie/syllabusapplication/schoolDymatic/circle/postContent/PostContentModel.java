package com.example.daidaijie.syllabusapplication.schoolDymatic.circle.postContent;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;
import com.example.daidaijie.syllabusapplication.bean.PhotoInfo;
import com.example.daidaijie.syllabusapplication.bean.PostContent;
import com.example.daidaijie.syllabusapplication.bean.SmmsResult;
import com.example.daidaijie.syllabusapplication.retrofitApi.PushImageToSmmsApi;
import com.example.daidaijie.syllabusapplication.retrofitApi.PushPostApi;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.GsonUtil;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class PostContentModel implements IPostContentModel {

    private String TAG = this.getClass().getSimpleName();

    private List<String> mPhotoImgs;

    private IUserModel mIUserModel;

    PushPostApi pushPostApi;

    PushImageToSmmsApi pushImageToSmmsApi;

    public PostContentModel(IUserModel IUserModel, PushPostApi pushPostApi, PushImageToSmmsApi pushImageToSmmsApi) {
        mIUserModel = IUserModel;
        this.pushPostApi = pushPostApi;
        mPhotoImgs = new ArrayList<>();
        this.pushImageToSmmsApi = pushImageToSmmsApi;
    }

    @Override
    public List<String> getPhotoImgs() {
        return mPhotoImgs;
    }

    @Override
    public void postPhotoToSmms(final OnPostPhotoCallBack onPostPhotoCallBack) {
        //如果没有照片，那么直接成功、返回
        if (mPhotoImgs.size() == 0) {
            onPostPhotoCallBack.onSuccess(null);
            return;
        }

        //图片上传到sm.ms
        Observable.from(mPhotoImgs)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, File>() {
                    //将文件路径转换成文件类型
                    @Override
                    public File call(String s) {
                        return new File(s.substring("file://".length(), s.length()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    PhotoInfo photoInfo = new PhotoInfo();

                    @Override
                    public void onStart() {
                        super.onStart();
                        //初始化
                        photoInfo.setPhoto_list(new ArrayList<PhotoInfo.PhotoListBean>());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        onPostPhotoCallBack.onFail("图片上传失败!");
                    }

                    @Override
                    public void onNext(File file) {
                        //将图片上传到sm.ms
                        Log.d(TAG, "postPhotoToSmms: " + file.getName());
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("smfile", file.getName(), requestFile);
                        pushImageToSmmsApi.pushImage(body)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<SmmsResult>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        onPostPhotoCallBack.onFail("图片上传失败!");
                                    }

                                    @Override
                                    public void onNext(SmmsResult smmsResult) {
                                        if (smmsResult.getStatus().equals("success")) {
                                            PhotoInfo.PhotoListBean photoListBean = new PhotoInfo.PhotoListBean();
                                            photoListBean.setSize_big(smmsResult.getData().getPicUrl());
                                            photoListBean.setSize_small(smmsResult.getData().getPicUrl());
                                            photoInfo.getPhoto_list().add(photoListBean);
                                            if (photoInfo.getPhoto_list().size() == mPhotoImgs.size()) {
                                                //将json post到服务器
                                                String photoListJsonString = GsonUtil.getDefault().toJson(photoInfo);
                                                onPostPhotoCallBack.onSuccess(photoListJsonString);
                                            }
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<Void> pushContent(@Nullable String photoListJson, String content, String
            source) {
        PostContent postContent = new PostContent();
        postContent.content = content;
        postContent.description = "None";
        postContent.post_type = PushPostApi.POST_TYPE_TOPIC;
        postContent.photo_list_json = photoListJson;
        postContent.source = source;
        if (source.contains("匿名")) {
            postContent.token = "000000";
            postContent.uid = -1;//匿名
        } else {
            postContent.token = mIUserModel.getUserInfoNormal().getToken();
            postContent.uid = mIUserModel.getUserInfoNormal().getUser_id();
        }

        return pushPostApi.post(postContent)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpResult<Void>, Observable<Void>>() {
                    @Override
                    public Observable<Void> call(HttpResult<Void> voidHttpResult) {
                        if (voidHttpResult.getCode() == 201) {
                            return Observable.just(voidHttpResult.getData());
                        }
                        return Observable.error(new Throwable(voidHttpResult.getMessage()));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }
}
