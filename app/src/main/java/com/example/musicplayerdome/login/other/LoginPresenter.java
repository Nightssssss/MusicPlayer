package com.example.musicplayerdome.login.other;


import android.util.Log;

import com.example.musicplayerdome.abstractclass.LoginContract;
import com.example.musicplayerdome.bean.KeyBean;
import com.example.musicplayerdome.bean.QrCheckBean;
import com.example.musicplayerdome.bean.QrImgBean;
import com.example.musicplayerdome.bean.ValCodeBean;
import com.example.musicplayerdome.login.bean.LoginBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginPresenter extends LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";

    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        this.mModel = new LoginModel();
    }
    // 二维码验证的key
    private String key;
    private int status;
    private String login_url;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // 1.二维码key生成
    public void loginGetQrImg() {
        mModel.loginGetQrKey(System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<KeyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(KeyBean keyBean) {
                        Log.d(TAG, "onNext: "+keyBean);
                        key = keyBean.getData().getUnikey();
                        mModel.loginCreateQrImg(key, System.currentTimeMillis(), true)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<QrImgBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        Log.d(TAG, "onSubscribe");
                                    }
                                    @Override
                                    public void onNext(QrImgBean qrImgBean) {
                                        Log.d(TAG, "onNext: "+qrImgBean);
                                        login_url = qrImgBean.getData().getQrurl();
                                        mView.onGetQrImgSuccess(qrImgBean);
                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "二维码图片获取失败 : 请检查api文件夹中的ApiService的BASE_URL地址是否正确，如果是使用本地服务，请确保在同一个wift（不能是手机端的热点）下");
                                        mView.onLoginFail(e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "二维码key获取失败 : 请检查api文件夹中的ApiService的BASE_URL地址是否正确，如果是使用本地服务，请确保在同一个wift（不能是手机端的热点）下");
                        mView.onLoginFail(e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete!");
                    }
                });
    }
    public void loginCheckQrScan() {
        mModel.loginCheckQrStatus(key, System.currentTimeMillis(), true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QrCheckBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }
                    @Override
                    public void onNext(QrCheckBean bean) {
                        Log.d(TAG, "状态响应码: " + bean.getCode());
                        int code = bean.getCode();
                        if (code == 800) {
                            status = code;
                            mView.onLoginFail("二维码已过期，请重新获取");
                        }
                        if (code == 803) {
                            // 获取当前登录状态
                            status = code;
                            Log.d(TAG, "登录成功，准备获取用户信息");
                            String cookie = bean.getCookie();
                            Log.d(TAG,cookie);
                            if(!cookie.isEmpty())
                                getUserAccount(cookie);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "二维码检查失败 : 请检查api文件夹中的ApiService的BASE_URL地址是否正确，如果是使用本地服务，请确保在同一个wift（不能是手机端的热点）下");
                        mView.onLoginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete!");
                    }
                });
    }


    public void getUserAccount(String cookie){
        mModel.getUserAccount(cookie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.onLoginSuccess(loginBean);
                        Log.d(TAG, "登录成功");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "登录状态获取失败 : 请检查api文件夹中的ApiService的BASE_URL地址是否正确，如果是使用本地服务，请确保在同一个wift（不能是手机端的热点）下");
                        mView.onLoginFail(e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void login(String phone, String password) {
        mModel.login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(LoginBean bean) {
                        mView.onLoginSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "登录请求错误 : 请检查api文件夹中的ApiService的BASE_URL地址是否正确，如果是使用本地服务，请确保在同一个wift（不能是手机端的热点）下");
                        mView.onLoginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete!");
                    }
                });
    }

    @Override
    public void sendCode(String phone) {
        mModel.sentCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ValCodeBean>(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(ValCodeBean valCodeBean) {
                        Log.d(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "登录请求错误 : 请检查api文件夹中的ApiService的BASE_URL地址是否正确，如果是使用本地服务，请确保在同一个wift（不能是手机端的热点）下");
                        mView.onLoginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete!");
                    }
                });
    }

    @Override
    public void loginByCode(String phone,String captcha) {
        mModel.loginByCode(phone,captcha)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
