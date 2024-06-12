package com.example.musicplayerdome.abstractclass;


import com.example.musicplayerdome.base.BasePresenter;
import com.example.musicplayerdome.bean.KeyBean;
import com.example.musicplayerdome.bean.QrCheckBean;
import com.example.musicplayerdome.bean.QrImgBean;
import com.example.musicplayerdome.bean.ValCodeBean;
import com.example.musicplayerdome.login.bean.LoginBean;

import io.reactivex.Observable;

public interface LoginContract {

    interface Model extends BaseModel {
        Observable<KeyBean>loginGetQrKey(long timestamp);
        Observable<QrImgBean>loginCreateQrImg(String key, long timestamp, Boolean qrimg);
        Observable<QrCheckBean>loginCheckQrStatus(String key, long timestamp, Boolean noCookie);
        Observable<LoginBean>loginByCode(String phone,String captcha);
        Observable<ValCodeBean>sentCode(String phone);

        Observable<LoginBean> login(String phone, String password);
        Observable<LoginBean>anonimousLogin();

        Observable<LoginBean> getUserAccount(String cookie);
    }

    interface View extends BaseView {

        void onLoginSuccess(LoginBean bean);

        void onLoginFail(String e);
        void onGetQrImgSuccess(QrImgBean qrImgBean);

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void login(String phone, String password);
        public abstract void sendCode(String phone);
        public abstract void loginByCode(String phone,String captcha);
    }
}
