package com.example.musicplayerdome.main.other;


import com.example.musicplayerdome.abstractclass.WowContract;
import com.example.musicplayerdome.api.ApiEngine;
import com.example.musicplayerdome.bean.BannerBean;
import com.example.musicplayerdome.bean.MusicCanPlayBean;
import com.example.musicplayerdome.main.bean.CollectionListBean;
import com.example.musicplayerdome.main.bean.DailyRecommendBean;
import com.example.musicplayerdome.main.bean.HighQualityPlayListBean;
import com.example.musicplayerdome.main.bean.MainRecommendPlayListBean;
import com.example.musicplayerdome.main.bean.PlaylistDetailBean;
import com.example.musicplayerdome.main.bean.RecommendPlayListBean;
import com.example.musicplayerdome.main.bean.RecommendsongBean;
import com.example.musicplayerdome.main.bean.TopListBean;
import com.example.musicplayerdome.song.bean.SongDetailBean;

import io.reactivex.Observable;

public class WowModel implements WowContract.Model {
    @Override
    public Observable<SongDetailBean> getSongDetailAll(String idlist, String cookie) {
        return ApiEngine.getInstance().getApiService().getSongDetailAll(idlist,cookie);
    }

    @Override
    public Observable<BannerBean> getBanner() {
        return ApiEngine.getInstance().getApiService().getBanner("2");
    }

    @Override
    public Observable<MainRecommendPlayListBean> getRecommendPlayList(String cookie) {
        return ApiEngine.getInstance().getApiService().getRecommendPlayList(cookie);
    }

    @Override
    public Observable<MainRecommendPlayListBean> getRecommendPlayListAgain(String cookie) {
        return ApiEngine.getInstance().getApiService().getRecommendPlayList(cookie);
    }

    @Override
    public Observable<DailyRecommendBean> getDailyRecommend(String cookie) {
        return ApiEngine.getInstance().getApiService().getDailyRecommend(cookie);
    }

    @Override
    public Observable<TopListBean> getTopList(String cookie) {
        return ApiEngine.getInstance().getApiService().getTopList(cookie);
    }

    @Override
    public Observable<RecommendPlayListBean> getPlayList(String type, int limit,String cookie) {
        return ApiEngine.getInstance().getApiService().getPlayList(type, limit,cookie);
    }

    @Override
    public Observable<PlaylistDetailBean> getPlaylistDetail(long id,String cookie) {
        return ApiEngine.getInstance().getApiService().getPlaylistDetail(id,cookie);
    }

    @Override
    public Observable<MusicCanPlayBean> getMusicCanPlay(long id,String cookie) {
        return ApiEngine.getInstance().getApiService().getMusicCanPlay(id,cookie);
    }

    @Override
    public Observable<HighQualityPlayListBean> getHighQuality(int limit, long before,String cookie) {
        return ApiEngine.getInstance().getApiService().getHighquality(limit, before,cookie);
    }

    @Override
    public Observable<RecommendsongBean> getRecommendsong() {
        return ApiEngine.getInstance().getApiService().getRecommendsong();
    }

    @Override
    public Observable<CollectionListBean> CollectionList(int t, long id,String cookie) {
        return ApiEngine.getInstance().getApiService().CollectionMusicList(t,id,cookie);
    }
}
