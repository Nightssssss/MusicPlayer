package com.example.musicplayerdome.api;


import com.example.musicplayerdome.bean.BannerBean;
import com.example.musicplayerdome.bean.DjCategoryRecommendBean;
import com.example.musicplayerdome.bean.DjCatelistBean;
import com.example.musicplayerdome.bean.DjDetailBean;
import com.example.musicplayerdome.bean.DjPaygiftBean;
import com.example.musicplayerdome.bean.DjProgramBean;
import com.example.musicplayerdome.bean.DjRecommendBean;
import com.example.musicplayerdome.bean.DjRecommendTypeBean;
import com.example.musicplayerdome.bean.DjSubBean;
import com.example.musicplayerdome.bean.KeyBean;
import com.example.musicplayerdome.bean.MusicCanPlayBean;
import com.example.musicplayerdome.bean.QrCheckBean;
import com.example.musicplayerdome.bean.QrImgBean;
import com.example.musicplayerdome.bean.ValCodeBean;
import com.example.musicplayerdome.history.bean.SonghistoryBean;
import com.example.musicplayerdome.main.bean.AlbumSublistBean;
import com.example.musicplayerdome.main.bean.ArtistSublistBean;
import com.example.musicplayerdome.login.bean.LoginBean;
import com.example.musicplayerdome.main.bean.CatlistBean;
import com.example.musicplayerdome.main.bean.CollectionListBean;
import com.example.musicplayerdome.main.bean.DailyRecommendBean;
import com.example.musicplayerdome.main.bean.HighQualityPlayListBean;
import com.example.musicplayerdome.main.bean.LikeListBean;
import com.example.musicplayerdome.main.bean.LogoutBean;
import com.example.musicplayerdome.main.bean.MainEventBean;
import com.example.musicplayerdome.main.bean.MainRecommendPlayListBean;
import com.example.musicplayerdome.main.bean.MvSublistBean;
import com.example.musicplayerdome.main.bean.MyFmBean;
import com.example.musicplayerdome.main.bean.PlayModeIntelligenceBean;
import com.example.musicplayerdome.main.bean.PlaylistDetailBean;
import com.example.musicplayerdome.main.bean.RecommendPlayListBean;
import com.example.musicplayerdome.main.bean.RecommendedVideoBean;
import com.example.musicplayerdome.main.bean.RecommendsongBean;
import com.example.musicplayerdome.main.bean.TopListBean;
import com.example.musicplayerdome.personal.bean.FollowUserBean;
import com.example.musicplayerdome.personal.bean.UserDetailBean;
import com.example.musicplayerdome.personal.bean.UserEventBean;
import com.example.musicplayerdome.personal.bean.UserPlaylistBean;
import com.example.musicplayerdome.search.bean.AlbumSearchBean;
import com.example.musicplayerdome.search.bean.FeedSearchBean;
import com.example.musicplayerdome.search.bean.HotSearchDetailBean;
import com.example.musicplayerdome.search.bean.LikeVideoBean;
import com.example.musicplayerdome.search.bean.PlayListSearchBean;
import com.example.musicplayerdome.search.bean.RadioSearchBean;
import com.example.musicplayerdome.search.bean.SimiSingerBean;
import com.example.musicplayerdome.search.bean.SingerAblumSearchBean;
import com.example.musicplayerdome.search.bean.SingerDescriptionBean;
import com.example.musicplayerdome.search.bean.SingerSearchBean;
import com.example.musicplayerdome.search.bean.SingerSongSearchBean;
import com.example.musicplayerdome.search.bean.SongSearchBean;
import com.example.musicplayerdome.search.bean.SynthesisSearchBean;
import com.example.musicplayerdome.search.bean.UserSearchBean;
import com.example.musicplayerdome.search.bean.VideoDataBean;
import com.example.musicplayerdome.search.bean.VideoUrlBean;
import com.example.musicplayerdome.song.bean.CollectionMVBean;
import com.example.musicplayerdome.song.bean.CommentLikeBean;
import com.example.musicplayerdome.song.bean.HistorySongBean;
import com.example.musicplayerdome.song.bean.LikeMusicBean;
import com.example.musicplayerdome.song.bean.LyricBean;
import com.example.musicplayerdome.song.bean.MVDetailBean;
import com.example.musicplayerdome.song.bean.MusicCommentBean;
import com.example.musicplayerdome.song.bean.PlayListCommentBean;
import com.example.musicplayerdome.song.bean.SongDetailBean;
import com.example.musicplayerdome.song.bean.SongMvBean;
import com.example.musicplayerdome.song.bean.SongMvDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yuncun.bean.YuncunReviewBean;

/**
api接口
 */
public interface ApiService {
    //本地305 IP
//    //String BASE_URL = "http://10.0.2.2:3000";
    //电脑ip
//     String BASE_URL = "http://100.64.210.156:3000";
    //小米8手机ip
//    String BASE_URL = "http://192.168.43.96:3000";
    //别人的腾讯服务器
    String BASE_URL = "http://139.199.168.243:3000";

    @GET("playlist/tracks")
    Observable<HistorySongBean>addHistorySong(@Query("op") String op,@Query("pid") String pid,@Query("tracks") String tracks,@Query("cookie") String cookie);

    @GET("login/qr/key")
    Observable<KeyBean> loginGetQrKey(@Query("rid") long timestamp);
    @GET("login/qr/create")
    Observable<QrImgBean> loginCreateQrImg(@Query("key") String key, @Query("timestamp") long timestamp, @Query("qrimg") Boolean qrimg);
    @GET("login/qr/check")
    Observable<QrCheckBean> loginCheckQr(@Query("key") String key, @Query("timestamp") long timestamp, @Query("noCookie") Boolean noCookie);
    @GET("login/cellphone")
    Observable<LoginBean> login(@Query("phone") String phone, @Query("password") String password);
    @GET("register/anonimous")
    Observable<LoginBean>anonimousLogin();
    @GET("/captcha/sent")
    Observable<ValCodeBean>sentCode(@Query("phone") String phone);
    @GET("/captcha/verify")
    Observable<LoginBean>loginByCode(@Query("phone") String phone,@Query("captcha")String captcha);
    @GET("logout")
    Observable<LogoutBean> logout();

    @GET("banner")
    Observable<BannerBean> getBanner(@Query("type") String type);

    @GET("recommend/resource")
    Observable<MainRecommendPlayListBean> getRecommendPlayList(@Query("cookie") String cookie);

    @GET("recommend/songs")
    Observable<DailyRecommendBean> getDailyRecommend(@Query("cookie") String cookie);

    @GET("toplist")
    Observable<TopListBean> getTopList(@Query("cookie") String cookie);

    @GET("dj/recommend")
    Observable<DjRecommendBean> getRadioRecommend();

    @GET("dj/recommend/type")
    Observable<DjRecommendTypeBean> getDjRecommend(@Query("type") int type);

    @GET("top/playlist")
    Observable<RecommendPlayListBean> getPlayList(@Query("cat") String type, @Query("limit") int limit,@Query("cookie") String cookie);

    @GET("top/playlist/highquality")
    Observable<HighQualityPlayListBean> getHighquality(@Query("limit") int limit, @Query("before") long before,@Query("cookie") String cookie);

    @GET("playlist/catlist")
    Observable<CatlistBean> getCatlist();

    @GET("playlist/detail")
    Observable<PlaylistDetailBean> getPlaylistDetail(@Query("id") long id,@Query("cookie") String cookie);

    @GET("check/music")
    Observable<MusicCanPlayBean> getMusicCanPlay(@Query("id") long id,@Query("cookie") String cookie);

    @GET("user/playlist")
    Observable<UserPlaylistBean> getUserPlaylist(@Query("uid") long uid,@Query("cookie") String cookie);

    @GET("user/event")
    Observable<UserEventBean> getUserEvent(@Query("uid") long uid, @Query("limit") int limit, @Query("lasttime") long time,@Query("cookie") String cookie);

    @GET("user/detail")
    Observable<UserDetailBean> getUserDetail(@Query("uid") long uid,@Query("cookie") String cookie);

    @GET("user/record")
    Observable<SonghistoryBean> getPlayHistoryList(@Query("uid") long uid, @Query("type") int type,@Query("cookie") String cookie);
    @GET("user/account")
    Observable<LoginBean> getUserAccount(@Query("cookie") String cookie);
    @GET("search/hot/detail")
    Observable<HotSearchDetailBean> getSearchHotDetail();

    @GET("search")//歌曲搜索
    Observable<SongSearchBean> getSongSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")//视频搜索
    Observable<FeedSearchBean> getFeedSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")//歌手搜索
    Observable<SingerSearchBean> getSingerSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")
    Observable<AlbumSearchBean> getAlbumSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")//歌单搜索
    Observable<PlayListSearchBean> getPlayListSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")
    Observable<RadioSearchBean> getRadioSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")//用户搜索
    Observable<UserSearchBean> getUserSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("search")//搜索
    Observable<SynthesisSearchBean> getSynthesisSearch(@Query("keywords") String keywords, @Query("type") int type);

    @GET("artists")//热门歌手
    Observable<SingerSongSearchBean> getSingerHotSong(@Query("id") long id);

    @GET("playlist/subscribe")//收藏/取消收藏歌单
    Observable<CollectionListBean> CollectionMusicList(@Query("t") int t, @Query("id") long id,@Query("cookie") String cookie);

    @GET("video/timeline/recommend")//获取推荐视频
    Observable<RecommendedVideoBean> getRecommendedVideos(@Query("cookie") String cookie);

    @GET("video/group")//获取视频标签/分类下的视频
    Observable<RecommendedVideoBean> getGroupVideos(@Query("id") long id,@Query("cookie") String cookie);

    @GET("artist/album")//获取歌手专辑
    Observable<SingerAblumSearchBean> getSingerAlbum(@Query("id") long id);

    @GET("artist/desc")//获取歌手描述
    Observable<SingerDescriptionBean> getSingerDesc(@Query("id") long id);

    @GET("simi/artist")//获取相似歌手
    Observable<SimiSingerBean> getSimiSinger(@Query("id") long id,@Query("cookie") String cookie);

    @GET("likelist")//喜欢音乐列表
    Observable<LikeListBean> getLikeList(@Query("uid") long uid,@Query("cookie") String cookie);

    @GET("song/detail")//获取歌曲详情
    Observable<SongDetailBean> getSongDetail(@Query("ids") long ids,@Query("cookie") String cookie);

    @GET("song/detail")//获取歌曲详情
    Observable<SongDetailBean> getSongDetailAll(@Query("ids") String idlist,@Query("cookie") String cookie);

    @GET("like")//喜欢音乐
    Observable<LikeMusicBean> likeMusice(@Query("id") long id,@Query("cookie") String cookie);

    @GET("follow")//关注/取消关注用户
    Observable<FollowUserBean> followUser(@Query("id") long id, @Query("t") int t,@Query("cookie") String cookie);

    @GET("like")//不喜欢音乐
    Observable<LikeMusicBean> NolikeMusic(@Query("like") boolean y,@Query("id") long id,@Query("cookie") String cookie);

    @GET("comment/music")//歌曲评论
    Observable<MusicCommentBean> getMusicComment(@Query("id") long id);

    @GET("mv/detail")//获取 mv 数据
    Observable<MVDetailBean> getMVDetail(@Query("mvid") long id);

    @GET("comment/mv")//mv 评论
    Observable<MusicCommentBean> getSongMvComment(@Query("id") long id);

    @GET("comment/like")//给评论点赞
    Observable<CommentLikeBean> likeComment(@Query("id") long id, @Query("cid") long cid, @Query("t") int t, @Query("type") int type);

    @GET("playmode/intelligence/list")//心动模式/智能播放
    Observable<PlayModeIntelligenceBean> getIntelligenceList(@Query("id") long id, @Query("pid") long pid);

    @GET("mv/url")//mv 地址
    Observable<SongMvDataBean> getSongMv(@Query("id") long id);

    @GET("artist/mv")//获取歌手 mv
    Observable<SongMvBean> getSongMvData(@Query("id") long id);

    @GET("artist/mv")//加载更多歌手 mv
    Observable<SongMvBean> LoadMoreSongMvData(@Query("id") long id, @Query("offset") long offset);

    @GET("video/url")//获取视频播放地址
    Observable<VideoUrlBean> getVideoData(@Query("id") String id);

    @GET("comment/video")//视频评论
    Observable<MusicCommentBean> getVideoComment(@Query("id") String id);

    @GET("video/detail/info")//获取视频点赞转发评论数数据
    Observable<VideoDataBean> getVideoDetails(@Query("vid") String id);

    @GET("resource/like")//资源点赞( MV,电台,视频)
    Observable<LikeVideoBean> ISLikeVideo(@Query("t") int t , @Query("type") int type , @Query("id") String id);

    @GET("album/sublist")//获取已收藏专辑列表
    Observable<AlbumSublistBean> getAlbumSublist(@Query("cookie") String cookie);

    @GET("personalized/newsong")//推荐新音乐
    Observable<RecommendsongBean> getRecommendsong();

    @GET("artist/sublist")//收藏的歌手列表
    Observable<ArtistSublistBean> getArtistSublist(@Query("cookie") String cookie);

    @GET("mv/sub")//收藏/取消收藏 MV
    Observable<CollectionMVBean> CollectionMV(@Query("mvid") long id,@Query("t") int t);

    @GET("personalized/mv")//推荐 mv
    Observable<MvSublistBean> getRecommendMV();

    @GET("comment/hotwall/list")//云村热评
    Observable<YuncunReviewBean> getYuncun(@Query("cookie") String cookie);

    @GET("mv/sublist")//收藏的 MV 列表
    Observable<MvSublistBean> getMvSublist(@Query("cookie") String cookie);

    @GET("personal_fm")//私人 FM
    Observable<MyFmBean> getMyFm();

    @GET("event")
    Observable<MainEventBean> getMainEvent();

    @GET("lyric")//获取歌词
    Observable<LyricBean> getLyric(@Query("id") long id,@Query("cookie") String cookie);

    @GET("comment/playlist")//歌单评论
    Observable<PlayListCommentBean> getPlaylistComment(@Query("id") long id);

    @GET("dj/paygift")
    Observable<DjPaygiftBean> getDjPaygift(@Query("limit") int limit, @Query("offset") int offset);

    @GET("dj/category/recommend")
    Observable<DjCategoryRecommendBean> getDjCategoryRecommend();

    @GET("dj/catelist")
    Observable<DjCatelistBean> getDjCatelist();

    @GET("dj/sub")
    Observable<DjSubBean> subDj(@Query("rid") long rid, @Query("t") int isSub);

    @GET("dj/program")
    Observable<DjProgramBean> getDjProgram(@Query("rid") long rid);

    @GET("dj/detail")
    Observable<DjDetailBean> getDjDetail(@Query("rid") long rid);
}
