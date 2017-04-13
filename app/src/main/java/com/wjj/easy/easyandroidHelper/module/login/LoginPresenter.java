package com.wjj.easy.easyandroidHelper.module.login;

import com.wjj.easy.easyandroid.mvp.domain.executor.Executor;
import com.wjj.easy.easyandroid.mvp.domain.executor.MainThread;
import com.wjj.easy.easyandroid.mvp.domain.usecases.UseCase;
import com.wjj.easy.easyandroidHelper.model.LoginResponse;
import com.wjj.easy.easyandroidHelper.model.base.BaseStatus;
import com.wjj.easy.easyandroidHelper.module.login.domain.GetVerifyCodeTask;
import com.wjj.easy.easyandroidHelper.module.login.domain.LoginTask;

import javax.inject.Inject;

/**
 * Login Presenter
 * Created by wujiajun on 17/4/7.
 */
public class LoginPresenter implements LoginContract.Presenter {

    @Inject
    LoginContract.View mView;
    @Inject
    Executor threadExecutor;
    @Inject
    MainThread mainThread;
    @Inject
    GetVerifyCodeTask mGetVerifyCodeTask;
    @Inject
    LoginTask mLoginTask;

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void getVerifyCode(String userName, String pwd) {
        mGetVerifyCodeTask.setUserName(userName);
        mGetVerifyCodeTask.setPwd(pwd);
        mGetVerifyCodeTask.setCallback(new UseCase.Callback<BaseStatus>() {
            @Override
            public void success(BaseStatus baseStatus) {
                getView().showToast("getVerifyCode success!");
            }

            @Override
            public void fail() {
                getView().showToast("getVerifyCode fail!");
            }
        });
        threadExecutor.execute(mGetVerifyCodeTask);
    }

    @Override
    public void login(String userName, String pwd, String verifyCode) {
        getView().showLoadingDialog();
        mLoginTask.setUserName(userName);
        mLoginTask.setPwd(pwd);
        mLoginTask.setVerifyCode(verifyCode);
        mLoginTask.setCallback(new UseCase.Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse) {
                getView().showToast("login success!");
            }

            @Override
            public void fail() {
                getView().showToast("login fail!");
            }
        });
        threadExecutor.execute(mLoginTask);
    }

    @Override
    public void start() {

    }

    public LoginContract.View getView() {
        return mView;
    }
}
