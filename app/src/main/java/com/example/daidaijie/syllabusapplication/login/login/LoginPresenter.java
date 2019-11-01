package com.example.daidaijie.syllabusapplication.login.login;

import android.util.Log;

import com.example.daidaijie.syllabusapplication.ILoginModel;
import com.example.daidaijie.syllabusapplication.base.IBaseModel;
import com.example.daidaijie.syllabusapplication.bean.AuthLogin;
import com.example.daidaijie.syllabusapplication.bean.UserInfo;
import com.example.daidaijie.syllabusapplication.bean.UserLogin;
import com.example.daidaijie.syllabusapplication.di.qualifier.user.UnLoginUser;
import com.example.daidaijie.syllabusapplication.di.scope.PerActivity;
import com.example.daidaijie.syllabusapplication.user.IUserModel;
import com.example.daidaijie.syllabusapplication.util.LoggerUtil;
import com.tendcloud.tenddata.TCAgent;
import com.tendcloud.tenddata.TDAccount;

import javax.inject.Inject;

import io.realm.RealmObject;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by daidaijie on 2016/10/13.
 */

public class LoginPresenter implements LoginContract.presenter {
    private String TAG = this.getClass().getSimpleName();

    LoginContract.view mView;

    ILoginModel mILoginModel;

    IUserModel mIUserModel;

    @Inject
    @PerActivity
    public LoginPresenter(LoginContract.view view, ILoginModel loginModel, @UnLoginUser IUserModel userModel) {
        mView = view;
        mILoginModel = loginModel;
        mIUserModel = userModel;
    }

    @Override
    public void start() {
        mILoginModel.getUserLoginNormal(new IBaseModel.OnGetSuccessCallBack<UserLogin>() {
            @Override
            public void onGetSuccess(UserLogin userLogin) {
                mView.setLogin(userLogin);
            }
        });
    }

    @Override
    public void login(final String username, String password) {
        mView.showLoading(true);

        update(username, password);

        mILoginModel.authLogin(username, password)
                .subscribe(new Observer<AuthLogin>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.showLoading(false);
                        mView.showFailMessage("获取失败");

                    }

                    @Override
                    public void onNext(AuthLogin authLogin) {
                        mILoginModel.setAuthLogin(authLogin);
                        Log.d(TAG, "onNext: " + authLogin.getCode());
//                        AuthLogin.Data  data = authLogin.getData();
//                        Log.d(TAG, "onNext: " + authLogin.getData().getToken());
                        Log.d(TAG, "onNext: " + authLogin.getData());
                        if (authLogin.getCode() == 200) {
                            mView.showLoading(false);
                            mILoginModel.saveAuthLoginToDisk();
                            mView.toMainView();
                        } else {
                            mView.showLoading(false);
                            mView.showFailMessage("获取失败");
                        }
                    }
                });
    }

    public void update(final String username, String password) {
        UserLogin userLogin = new UserLogin(username, password);
        mILoginModel.setUserLogin(userLogin);
        mILoginModel.saveUserLoginToDisk();
        Observable.merge(mIUserModel.getUserInfoFromNet(), mIUserModel.getUserBaseBeanFromNet())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RealmObject>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoading(false);
                        //talking data 注册登陆
                        TCAgent.onRegister(username, TDAccount.AccountType.REGISTERED, username);
                        TCAgent.onLogin(username, TDAccount.AccountType.REGISTERED, username);
//                        mView.toMainView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.printStack(e);
                        mView.showLoading(false);
                        if (e.getMessage() == null) {
//                            mView.showFailMessage("获取失败");
                        } else {
//                            mView.showFailMessage(e.getMessage().toUpperCase());
                        }
                    }

                    @Override
                    public void onNext(RealmObject realmObject) {
                        if (realmObject instanceof UserInfo) {
//                            mILoginModel.saveUserLoginToDisk();
                        }
                    }
                });
    }
}
