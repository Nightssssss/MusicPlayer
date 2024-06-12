package com.example.musicplayerdome.song.other;



import com.example.musicplayerdome.abstractclass.SongContract;
import com.example.musicplayerdome.api.ApiEngine;
import com.example.musicplayerdome.main.bean.LikeListBean;
import com.example.musicplayerdome.song.bean.CommentLikeBean;
import com.example.musicplayerdome.song.bean.HistorySongBean;
import com.example.musicplayerdome.song.bean.LikeMusicBean;
import com.example.musicplayerdome.song.bean.LyricBean;
import com.example.musicplayerdome.song.bean.MusicCommentBean;
import com.example.musicplayerdome.song.bean.PlayListCommentBean;
import com.example.musicplayerdome.song.bean.SongDetailBean;

import io.reactivex.Observable;

public class SongModel implements SongContract.Model {

    @Override
    public Observable<HistorySongBean> addHistorySong(String op, String pid,String tracks,String cookie) {
        return ApiEngine.getInstance().getApiService().addHistorySong(op,pid,tracks,cookie);
    }

    @Override
    public Observable<SongDetailBean> getSongDetail(long ids,String cookie) {
        return ApiEngine.getInstance().getApiService().getSongDetail(ids,cookie);
    }

    @Override
    public Observable<LikeMusicBean> likeMusic(long id,String cookie) {
        return ApiEngine.getInstance().getApiService().likeMusice(id,cookie);
    }

    @Override
    public Observable<LikeMusicBean> NolikeMusic(boolean f,long id,String cookie) {
        return ApiEngine.getInstance().getApiService().NolikeMusic(f,id,cookie);
    }

    @Override
    public Observable<LikeListBean> getLikeList(long uid,String cookie) {
        return ApiEngine.getInstance().getApiService().getLikeList(uid,cookie);
    }

    @Override
    public Observable<MusicCommentBean> getMusicComment(long id) {
        return ApiEngine.getInstance().getApiService().getMusicComment(id);
    }

    @Override
    public Observable<CommentLikeBean> likeComment(long id, long cid, int t, int type) {
        return ApiEngine.getInstance().getApiService().likeComment(id, cid, t, type);
    }

    @Override
    public Observable<LyricBean> getLyric(long id,String cookie) {
        return ApiEngine.getInstance().getApiService().getLyric(id,cookie);
    }

    @Override
    public Observable<PlayListCommentBean> getPlaylistComment(long id) {
        return ApiEngine.getInstance().getApiService().getPlaylistComment(id);
    }
}
