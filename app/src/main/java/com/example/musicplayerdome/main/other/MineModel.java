package com.example.musicplayerdome.main.other;


import com.example.musicplayerdome.abstractclass.MineContract;
import com.example.musicplayerdome.api.ApiEngine;
import com.example.musicplayerdome.history.bean.SonghistoryBean;
import com.example.musicplayerdome.main.bean.AlbumSublistBean;
import com.example.musicplayerdome.main.bean.ArtistSublistBean;
import com.example.musicplayerdome.main.bean.MvSublistBean;
import com.example.musicplayerdome.main.bean.MyFmBean;
import com.example.musicplayerdome.main.bean.PlayModeIntelligenceBean;
import com.example.musicplayerdome.personal.bean.UserDetailBean;
import com.example.musicplayerdome.personal.bean.UserPlaylistBean;

import io.reactivex.Observable;

public class MineModel implements MineContract.Model {
    @Override
    public Observable<UserPlaylistBean> getUserPlaylist(long uid,String cookie) {
        return ApiEngine.getInstance().getApiService().getUserPlaylist(uid,cookie);
    }

    @Override
    public Observable<UserPlaylistBean> getUserPlaylistAgain(long uid,String cookie) {
        return ApiEngine.getInstance().getApiService().getUserPlaylist(uid,cookie);
    }

    @Override
    public Observable<PlayModeIntelligenceBean> getIntelligenceList(long id, long pid) {
        return ApiEngine.getInstance().getApiService().getIntelligenceList(id, pid);
    }

    @Override
    public Observable<SonghistoryBean> getPlayHistoryList(long id, int type, String cookie) {
        return ApiEngine.getInstance().getApiService().getPlayHistoryList(id,type,cookie);
    }

    @Override
    public Observable<UserDetailBean> getUserDetail(long id,String cookie) {
        return ApiEngine.getInstance().getApiService().getUserDetail(id,cookie);
    }

    @Override
    public Observable<MvSublistBean> getMvSublist(String cookie) {
        return ApiEngine.getInstance().getApiService().getMvSublist(cookie);
    }

    @Override
    public Observable<ArtistSublistBean> getArtistSublist(String cookie) {
        return ApiEngine.getInstance().getApiService().getArtistSublist(cookie);
    }

    @Override
    public Observable<AlbumSublistBean> getAlbumSublistBean(String cookie) {
        return ApiEngine.getInstance().getApiService().getAlbumSublist(cookie);
    }

    @Override
    public Observable<MyFmBean> getMyFM() {
        return ApiEngine.getInstance().getApiService().getMyFm();
    }

}
