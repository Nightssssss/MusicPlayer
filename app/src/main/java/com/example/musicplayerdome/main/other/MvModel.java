package com.example.musicplayerdome.main.other;

import com.example.musicplayerdome.abstractclass.MvContract;
import com.example.musicplayerdome.api.ApiEngine;
import com.example.musicplayerdome.main.bean.MvSublistBean;

import io.reactivex.Observable;
import yuncun.bean.YuncunReviewBean;

public class MvModel implements MvContract.Model{
    @Override
    public Observable<MvSublistBean> getRecommendMV() {
        return ApiEngine.getInstance().getApiService().getRecommendMV();
    }

    @Override
    public Observable<YuncunReviewBean> getYuncun(String cookie) {
        return ApiEngine.getInstance().getApiService().getYuncun(cookie);
    }

    @Override
    public Observable<YuncunReviewBean> getYuncunAgain(String cookie) {
        return ApiEngine.getInstance().getApiService().getYuncun(cookie);
    }
}
