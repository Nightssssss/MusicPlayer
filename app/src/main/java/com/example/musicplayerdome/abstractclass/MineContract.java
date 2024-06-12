package com.example.musicplayerdome.abstractclass;


import com.example.musicplayerdome.base.BasePresenter;
import com.example.musicplayerdome.history.bean.SonghistoryBean;
import com.example.musicplayerdome.main.bean.AlbumSublistBean;
import com.example.musicplayerdome.main.bean.ArtistSublistBean;
import com.example.musicplayerdome.main.bean.MvSublistBean;
import com.example.musicplayerdome.main.bean.MyFmBean;
import com.example.musicplayerdome.main.bean.PlayModeIntelligenceBean;
import com.example.musicplayerdome.personal.bean.UserDetailBean;
import com.example.musicplayerdome.personal.bean.UserPlaylistBean;

import io.reactivex.Observable;

public interface MineContract {
    interface View extends BaseView {
        void onGetUserPlaylistSuccess(UserPlaylistBean bean);

        void onGetUserPlaylistFail(String e);

        void onGetUserPlaylistAgainSuccess(UserPlaylistBean bean);

        void onGetUserPlaylistAgainFail(String e);

        void onGetUserDetailSuccess(UserDetailBean bean);

        void onGetUserDetailFails(String e);

        void onGetPlayHistoryListSuccess(SonghistoryBean bean);

        void onGetPlayHistoryListFail(String e);

        void onGetIntelligenceListSuccess(PlayModeIntelligenceBean bean);

        void onGetIntelligenceListFail(String e);

        void onGetMvSublistBeanSuccess(MvSublistBean bean);

        void onGetMvSublistBeanFail(String e);

        void onGetArtistSublistBeanSuccess(ArtistSublistBean bean);

        void onGetArtistSublistBeanFail(String e);

        void onGetAlbumSublistBeanSuccess(AlbumSublistBean bean);

        void onGetAlbumSublistBeanFail(String e);

        void onGetMyFMSuccess(MyFmBean bean);

        void onGetMyFMFail(String e);
    }

    interface Model extends BaseModel {
        Observable<UserPlaylistBean> getUserPlaylist(long uid,String cookie);

        Observable<UserPlaylistBean> getUserPlaylistAgain(long uid,String cookie);

        Observable<PlayModeIntelligenceBean> getIntelligenceList(long id, long pid);
        Observable<SonghistoryBean> getPlayHistoryList(long id, int type, String cookie);

        Observable<UserDetailBean> getUserDetail(long id, String cookie);
        Observable<MvSublistBean> getMvSublist(String cookie);

        Observable<ArtistSublistBean> getArtistSublist(String cookie);

        Observable<AlbumSublistBean> getAlbumSublistBean(String cookie);

        Observable<MyFmBean> getMyFM();
    }

    abstract class Presenter extends BasePresenter<MineContract.View, MineContract.Model> {
        public abstract void getUserPlaylist(long uid,String cookie);

        public abstract void getUserPlaylistAgain(long uid,String cookie);

        public abstract void getPlayHistoryList(long id, int type, String cookie);

        public abstract void getIntelligenceList(long id, long pid);

        public abstract void getUserDetail(long id, String cookie);

        public abstract void getMvSublist(String cookie);

        public abstract void getArtistSublist(String cookie);

        public abstract void getAlbumSublist(String cookie);

        public abstract void getMyFM();
    }
}
