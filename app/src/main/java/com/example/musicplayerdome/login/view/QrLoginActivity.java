package com.example.musicplayerdome.login.view;

import static com.example.musicplayerdome.util.GlobalVariables.cookie_string;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.musicplayerdome.R;
import com.example.musicplayerdome.abstractclass.LoginContract;
import com.example.musicplayerdome.api.ApiEngine;
import com.example.musicplayerdome.base.BaseActivity;
import com.example.musicplayerdome.bean.QrImgBean;
import com.example.musicplayerdome.databinding.ActivityLoginQrcodeBinding;
import com.example.musicplayerdome.login.bean.LoginBean;
import com.example.musicplayerdome.login.other.LoginPresenter;
import com.example.musicplayerdome.util.ActivityStarter;
import com.example.musicplayerdome.util.ClickUtil;
import com.example.musicplayerdome.util.PrivacyUtils;
import com.example.musicplayerdome.util.SettingSPUtils;
import com.example.musicplayerdome.util.SharePreferenceUtil;
import com.example.musicplayerdome.util.XToastUtils;

import java.util.Timer;
import java.util.TimerTask;

public class QrLoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    private static final String TAG = "qrLoginActivity";
    ActivityLoginQrcodeBinding binding;
    @Override
    public void onLoginSuccess(LoginBean bean) {
//        hideDialog();
        Log.d(TAG, "onLoginSuccess : " + bean);
        SharePreferenceUtil.getInstance(this).saveUserInfo(bean);
        ActivityStarter.getInstance().startMainActivity(this);
        this.finish();
    }


    @Override
    public void onLoginFail(String error) {
        hideDialog();
        Log.w(TAG, "bean : " + error);
        if (error.equals("HTTP 502 Bad Gateway")) {
            XToastUtils.error(R.string.scan_fail);
        } else {
            XToastUtils.error(error);
        }
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_qrcode);
    }
    @Override
    public void onGetQrImgSuccess(QrImgBean qrImgBean) {
//        hideDialog();
        Log.d(TAG,"onGetQrImgSuccess"+qrImgBean);
        // 解析base64编码的二维码
//        String base64String="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";
        String base64String = qrImgBean.getData().getQrimg().split("data:image/png;base64,")[1];
        // 将字符串转换为字节数组
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        // 使用位图工厂将字节数组转换为位图对象
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        binding.ivQrcode.setImageBitmap(decodedByte);
        // 轮询检查是否扫描成功二维码
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.d(TAG, "计时器计时中！！！！");
//                mPresenter.loginCheckQrScan();
//                timer.cancel();
//            }
//        },5000, 5000);
    }


    @Override
    protected LoginPresenter onCreatePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initData() {
        setBackBtn(getString(R.string.colorBlack));
        setLeftTitleText(R.string.login_qr);
    }

    @Override
    protected void initView() {
        setMargins(binding.title,0,getStatusBarHeight(this),0,0);
        binding.register.setOnClickListener(this);
        binding.forgetPwd.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        SettingSPUtils spUtils = new SettingSPUtils(QrLoginActivity.this);
        if (!spUtils.isAgreePrivacy()) {
            PrivacyUtils.showPrivacyDialog(this, (dialog, which) -> {
                dialog.dismiss();
                spUtils.setIsAgreePrivacy(true);
            });
        }
        // 解析二维码
        mPresenter.loginGetQrImg();
    }

    @Override
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000, v)) {
            return;
        }
        /**
         * loginType : 1
         * code : 200
         * account : {"id":415560926,"userName":"21_13272136221","type":1,"status":0,"whitelistAuthority":0,"createTime":1486309512362,
         * "salt":"[B@3c3d6a89","tokenVersion":0,"ban":0,"baoyueVersion":0,"donateVersion":0,"vipType":0,"viptypeVersion":0,"anonimousUser":false}
         * profile : {"detailDescription":"","followed":false,"userId":415560926,
         * "defaultAvatar":false,"avatarUrl":"https://p3.music.126.net/ULOn30612l-96hKgIy18tA==/18787355185828647.jpg",
         * "nickname":"rikkatheworld","birthday":887904000000,"avatarImgId":18787355185828650,
         * "province":440000,"accountStatus":0,"vipType":0,"gender":0,"djStatus":0,"avatarImgIdStr":"18787355185828647",
         * "backgroundImgIdStr":"109951163710677237","experts":{},"mutual":false,"remarkName":null,
         * "expertTags":null,"authStatus":0,"backgroundImgId":109951163710677230,"userType":0,
         * "city":440300,"signature":"","authority":0,"description":"",
         * "backgroundUrl":"https://p4.music.126.net/r4Ej-BqYiX-Al8AqRFeAUA==/109951163710677237.jpg",
         * "avatarImgId_str":"18787355185828647","followeds":3,"follows":1,"eventCount":0,"playlistCount":3,"playlistBeSubscribedCount":1}
         * bindings : [{"refreshTime":1486309535,"url":"","userId":415560926,"tokenJsonStr":"{\"countrycode\":\"\",\"cellphone\":\"13272136221\",\"hasPassword\":true}","id":2973312066,"type":1,"expiresIn":2147483647,"bindingTime":1486309535025,"expired":false},{"refreshTime":1558957256,"url":"","userId":415560926,"tokenJsonStr":"{\"access_token\":\"A73ACD9A247F38479247AD3BA69488BB\",\"refresh_token\":\"3DDEE9EA1955B12F72682A71CC2E95C8\",\"openid\":\"CFA63F441F4BA6BF4999A0182698EC62\",\"nickname\":\"RikkaTheWorld\",\"expires_in\":7776000}","id":2973299013,"type":5,"expiresIn":7776000,"bindingTime":1486309512369,"expired":false}]
         */
        switch (v.getId()) {
            case R.id.btn_login:
//                LoginBean loginBean = new LoginBean();
//                loginBean.setLoginType(1);
//                loginBean.setCode(200);
//                LoginBean.AccountBean accountBean = new LoginBean.AccountBean();
//                LoginBean.ProfileBean profileBean = new LoginBean.ProfileBean();
//                List<LoginBean.BindingsBean> bindingsBeans = new ArrayList<LoginBean.BindingsBean>();
//                accountBean.setId(415560926);
//                accountBean.setUserName("21_13272136221");
//                accountBean.setType(1);
//                accountBean.setStatus(0);
//                long createTime = 1486309512362L;
//                accountBean.setCreateTime(createTime);
//                accountBean.setSalt("[B@3c3d6a89");
//                accountBean.setTokenVersion(0);
//                accountBean.setBan(0);
//                accountBean.setBaoyueVersion(0);
//                accountBean.setDonateVersion(0);
//                accountBean.setVipType(0);
//                accountBean.setDonateVersion(0);
//                accountBean.setAnonimousUser(false);
//                profileBean.setFollowed(false);
//                profileBean.setUserId(415560926);
//                profileBean.setDefaultAvatar(false);
//                profileBean.setAvatarUrl("https://p3.music.126.net/ULOn30612l-96hKgIy18tA==/18787355185828647.jpg");
//                profileBean.setNickname("rikkatheworld");
//                profileBean.setBirthday(887904000000L);
//                profileBean.setAvatarImgIdStr("18787355185828650");
//                profileBean.setAvatarImgId_str("18787355185828650");
//                profileBean.setBackgroundImgIdStr("109951163710677237");
//                profileBean.setMutual(false);
//                profileBean.setAuthStatus(0);
//                profileBean.setBackgroundImgId(109951163710677230L);
//                profileBean.setUserType(0);
//                profileBean.setCity(440300);
//                profileBean.setSignature("");
//                profileBean.setAuthority(0);
//                profileBean.setDescription("");
//                profileBean.setBackgroundUrl("https://p4.music.126.net/r4Ej-BqYiX-Al8AqRFeAUA==/109951163710677237.jpg");
//                profileBean.setAvatarImgId_str("18787355185828647");
//                profileBean.setFolloweds(3);
//                profileBean.setFollows(1);
//                profileBean.setEventCount(0);
//                profileBean.setPlaylistCount(3);
//                profileBean.setPlaylistBeSubscribedCount(1);
//                LoginBean.BindingsBean bindingsBean = new LoginBean.BindingsBean();
//                bindingsBean.setRefreshTime(1486309535);
//                bindingsBean.setUrl("");
//                bindingsBean.setUserId(415560926);
//                bindingsBean.setTokenJsonStr("{\\\"countrycode\\\":\\\"\\\",\\\"cellphone\\\":\\\"13272136221\\\",\\\"hasPassword\\\":true}");
//                bindingsBean.setId(2973312066L);
//                bindingsBean.setType(1);
//                bindingsBean.setExpiresIn(2147483647);
//                bindingsBean.setBindingTime(1486309535025L);
//                bindingsBean.setExpired(false);
//                bindingsBeans.add(bindingsBean);
//                loginBean.setAccount(accountBean);
//                loginBean.setProfile(profileBean);
//                loginBean.setBindings(bindingsBeans);
//                onLoginSuccess(loginBean);
//                XToastUtils.info(R.string.in_developing);
                // 测试哦
//                String cookie = "MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/wapi/feedback; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/weapi/feedback; HTTPOnly;MUSIC_SNS=; Max-Age=0; Expires=Sun, 04 Jun 2023 02:37:34 GMT; Path=/;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/api/feedback; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/eapi/feedback; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/wapi/feedback; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/wapi/clientlog; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/weapi/clientlog; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/api/feedback; HTTPOnly;__csrf=2dcd303efd79a5e923781536adaeca1d; Max-Age=1296010; Expires=Mon, 19 Jun 2023 02:37:44 GMT; Path=/;;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/weapi/feedback; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/wapi/clientlog; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/eapi/clientlog; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/neapi/clientlog; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/eapi/clientlog; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/openapi/clientlog; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/neapi/clientlog; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/api/clientlog; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/api/clientlog; HTTPOnly;MUSIC_U=000749D1256EF0B79113ABF5373E578F6B7176A9CDD73317CA81E53598947C41774800B541CB1431D8E1E85BE382A3225E1BAD9E2A825A4D15B71DC4AECE777ED946CDB7C2BBF61583A5650C83C208476E0177DC64E7D9B3E3285E37D6675A8E0F4E62CB0ED43C0D1ED11929C682A9C83381D681B50DC7174422DABCC71B2B324756BCDAFAA1742AA39EF09F7FA18117D2E34830F0566105A9C08536DED57F0F6482C3C187928DC8B4EEDBFADB01D0DC41D75B1FEBA592C6AD3E8718F4A37B612A3DF0F6BD8FF4BC07836380289D5333B284C6F9A266BC20746FD6AC74586DDB09DB8DCBBE2FFFD447E91DDABD272C2233A6480CFDC060B8DE9D9DDCE93D206E2003F85D935004F4AE9758A3AA51FBF27B; Max-Age=15552000; Expires=Fri, 01 Dec 2023 02:37:34 GMT; Path=/; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/eapi/feedback; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/openapi/clientlog; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/neapi/feedback; HTTPOnly;MUSIC_A_T=1496116987147; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/neapi/feedback; HTTPOnly;MUSIC_R_T=1496117227892; Max-Age=2147483647; Expires=Fri, 22 Jun 2091 05:51:41 GMT; Path=/weapi/clientlog; HTTPOnly";
                mPresenter.getUserAccount(cookie_string);
//                ApiEngine.addCookieFromString(cookie);
                break;
            case R.id.register:
            case R.id.forget_pwd:
                XToastUtils.info(R.string.in_developing);
                break;
        }
    }
}
