package com.example.daidaijie.syllabusapplication.schoolDymatic.circle.postContent;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.App;
import com.example.daidaijie.syllabusapplication.R;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import id.zelory.compressor.Compressor;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by daidaijie on 2016/10/21.
 */

public class PostContentPresenter implements PostContentContract.presenter {

    private String TAG = this.getClass().getSimpleName();

    IPostContentModel mIPostContentModel;

    PostContentContract.view mView;

    @Inject
    @PerActivity
    public PostContentPresenter(IPostContentModel IPostContentModel, PostContentContract.view view) {
        mIPostContentModel = IPostContentModel;
        mView = view;
    }

    @Override
    public void start() {
        mView.setUpFlow(mIPostContentModel.getPhotoImgs());
    }

    @Override
    public void selectPhoto() {
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(1)
                .setEnableCamera(false)
                .setEnableEdit(false)
                .setEnableCrop(false)
                .setEnableRotate(true)
                .setEnablePreview(false)
                .setCropReplaceSource(false)//配置裁剪图片时是否替换原始图片，默认不替换
                .setForceCrop(false)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
                .build();

        //选择一张照片
        GalleryFinal.openGallerySingle(200, functionConfig, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                for (PhotoInfo photoInfo : resultList) {
                    mIPostContentModel.getPhotoImgs().add("file://" + photoInfo.getPhotoPath());
                    mView.setUpFlow(mIPostContentModel.getPhotoImgs());
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                mView.showFailMessage("选择失败");
            }
        });

    }

    @Override
    public void unSelectPhoto(int position) {
        mIPostContentModel.getPhotoImgs().remove(position);
        mView.setUpFlow(mIPostContentModel.getPhotoImgs());
    }

    @Override
    public boolean isNonePhoto() {
        return mIPostContentModel.getPhotoImgs().size() == 0;
    }

    @Override
    public void postContent(final String msg, final String source) {

        if (isNonePhoto() && msg.isEmpty()) {
            mView.showWarningMessage("请输入文字或者选择图片!");
            return;
        }
        mView.showLoading(true);
        mIPostContentModel.postPhotoToSmms(new IPostContentModel.OnPostPhotoCallBack() {
            @Override
            public void onSuccess(String photoJson) {
                mIPostContentModel.pushContent(photoJson, msg, source)
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {
                                mView.showLoading(false);
                                mView.onPostFinishCallBack();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showLoading(false);
                                if (e.getMessage() == null) {
                                    mView.showFailMessage("发送失败");
                                } else {
                                    LoggerUtil.e(e.getMessage());
                                    if (e.getMessage().toUpperCase().equals("UNAUTHORIZED")) {
                                        mView.showFailMessage(App.getContext().getResources().getString(R.string.UNAUTHORIZED));
                                    } else {
                                        mView.showFailMessage(e.getMessage().toUpperCase());
                                    }
                                }
                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        });
            }

            @Override
            public void onFail(String msg) {
                mView.showFailMessage(msg);
                mView.showLoading(false);
            }
        });
    }
}
