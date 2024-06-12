package com.example.musicplayerdome.abstractclass;

import com.example.musicplayerdome.base.BasePresenter;
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


public interface WowContract {
    interface View extends BaseView {
        void onGetBannerSuccess(BannerBean bean);

        void onGetBannerFail(String e);

        void onGetRecommendPlayListSuccess(MainRecommendPlayListBean bean);

        void onGetRecommendPlayListFail(String e);

        void onGetRecommendPlayListAgainSuccess(MainRecommendPlayListBean bean);

        void onGetRecommendPlayListAgainFail(String e);

        void onGetDailyRecommendSuccess(DailyRecommendBean bean);

        void onGetDailyRecommendFail(String e);

        void onGetTopListSuccess(TopListBean bean);

        void onGetTopListFail(String e);

        void onGetPlayListSuccess(RecommendPlayListBean bean);

        void onGetPlayListFail(String e);

        void onGetPlaylistDetailSuccess(PlaylistDetailBean bean);

        void onGetPlaylistDetailFail(String e);

        void onGetMusicCanPlaySuccess(MusicCanPlayBean bean);

        void onGetMusicCanPlayFail(String e);

        void onGetHighQualitySuccess(HighQualityPlayListBean bean);

        void onGetHighQualityFail(String e);

        void onGetRecommendsongSuccess(RecommendsongBean bean);

        void onGetRecommendsongFail(String e);

        void onGetCollectionListSuccess(CollectionListBean bean);

        void onGetCollectionListFail(String e);

        void onGetSongDetailSuccess(SongDetailBean bean);

        void onGetSongDetailFail(String e);
    }

    interface Model extends BaseModel {
        Observable<SongDetailBean> getSongDetailAll(String idlist, String cookie);

        Observable<BannerBean> getBanner();

        Observable<MainRecommendPlayListBean> getRecommendPlayList(String cookie);

        Observable<MainRecommendPlayListBean> getRecommendPlayListAgain(String cookie);

        Observable<DailyRecommendBean> getDailyRecommend(String cookie);

        Observable<TopListBean> getTopList(String cookie);

        Observable<RecommendPlayListBean> getPlayList(String type, int limit,String cookie);

        Observable<PlaylistDetailBean> getPlaylistDetail(long id,String cookie);

        Observable<MusicCanPlayBean> getMusicCanPlay(long id, String cookie);

        Observable<HighQualityPlayListBean> getHighQuality(int limit, long before, String cookie);

        Observable<RecommendsongBean> getRecommendsong();
        Observable<CollectionListBean> CollectionList(int t, long id, String cookie);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getSongDetailAll(String idlist, String cookie);

        public abstract void getBanner();

        public abstract void getRecommendPlayList(String cookie);

        public abstract void getRecommendPlayListAgain(String cookie);

        public abstract void getDailyRecommend(String cookie);

        public abstract void getRecommendsong();

        public abstract void getTopList(String cookie);

        public abstract void getPlayList(String type, int limit,String cookie);

        public abstract void getPlaylistDetail(long id,String cookie);

        public abstract void getMusicCanPlay(long id,String cookie);

        public abstract void getHighQuality(int limit, long before,String cookie);

        public abstract void CollectionList(int t, long id, String cookie);
    }
}
