package com.example.musicplayerdome.login.other;



import com.example.musicplayerdome.abstractclass.LoginContract;
import com.example.musicplayerdome.api.ApiEngine;
import com.example.musicplayerdome.bean.KeyBean;
import com.example.musicplayerdome.bean.QrCheckBean;
import com.example.musicplayerdome.bean.QrImgBean;
import com.example.musicplayerdome.bean.ValCodeBean;
import com.example.musicplayerdome.login.bean.LoginBean;

import io.reactivex.Observable;

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<KeyBean> loginGetQrKey(long timestamp) {
        return ApiEngine.getInstance().getApiService().loginGetQrKey(timestamp);
    }

    @Override
    public Observable<QrImgBean> loginCreateQrImg(String key, long timestamp, Boolean qrimg) {
        return ApiEngine.getInstance().getApiService().loginCreateQrImg(key,timestamp,qrimg);
    }

    @Override
    public Observable<QrCheckBean> loginCheckQrStatus(String key, long timestamp, Boolean noCookie) {
        return ApiEngine.getInstance().getApiService().loginCheckQr(key,timestamp,noCookie);
    }

    @Override
    public Observable<LoginBean>loginByCode(String phone,String captcha){
        return ApiEngine.getInstance().getApiService().loginByCode(phone,captcha);
    }

    @Override
    public Observable<ValCodeBean> sentCode(String phone) {
        return ApiEngine.getInstance().getApiService().sentCode(phone);
    }

    @Override
    public Observable<LoginBean> login(String phone, String password) {
        return ApiEngine.getInstance().getApiService().login(phone, password);
    }

    @Override
    public Observable<LoginBean>anonimousLogin(){
        return ApiEngine.getInstance().getApiService().anonimousLogin();
    }

    @Override
    public Observable<LoginBean> getUserAccount(String cookie) {
        return ApiEngine.getInstance().getApiService().getUserAccount(cookie);
    }
}
