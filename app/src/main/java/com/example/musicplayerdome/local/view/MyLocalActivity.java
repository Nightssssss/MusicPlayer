package com.example.musicplayerdome.local.view;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.musicplayerdome.R;
import com.example.musicplayerdome.abstractclass.LocalContract;
import com.example.musicplayerdome.base.BaseActivity;
import com.example.musicplayerdome.bean.BannerBean;
import com.example.musicplayerdome.bean.MusicCanPlayBean;
import com.example.musicplayerdome.databinding.ActivityLocalPlayListBinding;

import com.example.musicplayerdome.local.bean.LocalSongBean;
import com.example.musicplayerdome.local.other.LocalPresenter;
import com.example.musicplayerdome.main.bean.CollectionListBean;
import com.example.musicplayerdome.main.bean.DailyRecommendBean;
import com.example.musicplayerdome.main.bean.HighQualityPlayListBean;
import com.example.musicplayerdome.main.bean.MainRecommendPlayListBean;
import com.example.musicplayerdome.main.bean.PlaylistDetailBean;
import com.example.musicplayerdome.main.bean.RecommendPlayListBean;
import com.example.musicplayerdome.main.bean.RecommendsongBean;
import com.example.musicplayerdome.main.bean.TopListBean;
import com.example.musicplayerdome.main.dialog.SongSheetDialog;
import com.example.musicplayerdome.personal.view.PersonalActivity;
import com.example.musicplayerdome.song.adapter.MySongListAdapter;
import com.example.musicplayerdome.song.bean.SongDetailBean;
import com.example.musicplayerdome.song.other.SongPlayManager;
import com.example.musicplayerdome.util.AppBarStateChangeListener;
import com.example.musicplayerdome.util.DensityUtil;
import com.example.musicplayerdome.util.SharedPreferencesUtil;
import com.example.musicplayerdome.util.XToastUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.starrysky.model.SongInfo;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheetItemView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayerdome.main.fragment.HomeFragment.PLAYLIST_CREATOR_AVATARURL;
import static com.example.musicplayerdome.main.fragment.HomeFragment.PLAYLIST_CREATOR_NICKNAME;
import static com.example.musicplayerdome.main.fragment.HomeFragment.PLAYLIST_DESCRIPTION;
import static com.example.musicplayerdome.main.fragment.HomeFragment.PLAYLIST_ID;
import static com.example.musicplayerdome.main.fragment.HomeFragment.PLAYLIST_NAME;
import static com.example.musicplayerdome.main.fragment.HomeFragment.PLAYLIST_PICURL;
import static com.example.musicplayerdome.personal.view.PersonalActivity.USER_ID;
import static com.example.musicplayerdome.util.GlobalVariables.cookie_string;

/**
 * SongSheetActivityMusic 歌单详情页面
 * 展示歌单里的歌曲
 */
public class MyLocalActivity extends BaseActivity<LocalPresenter> implements LocalContract.View {
    private ActivityLocalPlayListBinding binding;
    private static final String TAG = "LocalSongSheetActivity";
    private MySongListAdapter adapter;
    private long playlistId;
    private String creatorUrl, playlistName, playlistPicUrl, creatorName, description, songids = "";
    int minDistance, deltaDistance, isCollection = 1;
    private ObjectAnimator alphaAnimator, coverAlphaAnimator;
    //计算完成后发送的Handler msg
    public static final int COMPLETED = 0;
    private LinearLayout SHComment, share;
    private TextView songComment;
    private Drawable Sbackg;
    private List<SongDetailBean.SongsBean> beanList = new ArrayList<>();
    private List<PlaylistDetailBean.PlaylistBean.TrackIdsBean> songidList = new ArrayList<>();
    private List<SongInfo> songInfos = new ArrayList<>();

    private List<LocalSongBean>localSongList = new ArrayList<>();
    private List<Integer> songid = new ArrayList<>();
    private Boolean isload = false;
    private PlaylistDetailBean Sbean;

    private IntentFilter intentFilter;
    private HeadsetPlugReceiver headsetPlugReceiver;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                Sbackg = (Drawable) msg.obj;
                binding.background.setBackground((Drawable) msg.obj);
                getAlphaAnimatorBg().start();
                getAlphaAnimatorCover().start();
            }
        }
    };

    @Override
    protected LocalPresenter onCreatePresenter() {
        return new LocalPresenter(this);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        Log.d(TAG,"开始初始化");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_local_play_list);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        headsetPlugReceiver = new HeadsetPlugReceiver();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
        }
        registerReceiver(headsetPlugReceiver, intentFilter);
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    protected void initData() {
        Log.d(TAG,"开始初始化data");
        setBackBtn(getString(R.string.colorWhite));
        setLeftTitleTextColorWhite();
        setLeftTitleText(R.string.localplaylist);

        LinearLayoutManager lm = new LinearLayoutManager(MyLocalActivity.this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new MySongListAdapter(this);
        adapter.setType(2);
        binding.recyclerView.setLayoutManager(lm);
        binding.recyclerView.setAdapter(adapter);
        playlistPicUrl = "http://p1.music.126.net/njFf4NliyjkcQoRL_FBi4Q==/109951164198676497.jpg";
        Glide.with(this).load(playlistPicUrl).into(binding.XLogin);
        playlistName = "本地歌曲";
        description = " ";
        binding.XTitle.setText(playlistName);
        creatorName = " ";
        binding.tvPlaylistName.setText(creatorName);
        creatorUrl = "http://p1.music.126.net/S0eMy4iPCsUF3ExOHQhc8w==/109951164198686079.jpg";
        Glide.with(this).load(creatorUrl).into(binding.userImg);
        playlistId = (123124234235235l);
        calculateColors(playlistPicUrl);
        initSongs();
//        if (getIntent() != null) {
////            showDialog();
//            playlistPicUrl = getIntent().getStringExtra(PLAYLIST_PICURL);
//            Glide.with(this).load(playlistPicUrl).into(binding.XLogin);
//            playlistName = getIntent().getStringExtra(PLAYLIST_NAME);
//            description = getIntent().getStringExtra(PLAYLIST_DESCRIPTION);
//            binding.XTitle.setText(playlistName);
//            creatorName = getIntent().getStringExtra(PLAYLIST_CREATOR_NICKNAME);
//            binding.tvPlaylistName.setText(creatorName);
//            creatorUrl = getIntent().getStringExtra(PLAYLIST_CREATOR_AVATARURL);
//            Glide.with(this).load(creatorUrl).into(binding.userImg);
//            playlistId = getIntent().getLongExtra(PLAYLIST_ID, 0);
//            calculateColors(playlistPicUrl);
//            Log.e(TAG, "initData: 当前歌单id为" + playlistId);
//            mPresenter.getPlaylistDetail(playlistId,cookie_string);
//        }
    }

    @Override
    protected void initView() {
        Log.d(TAG,"开始初始化view");
        SHComment = findViewById(R.id.SH_comment);
        songComment = findViewById(R.id.song_comment);
        share = findViewById(R.id.share);

        binding.rlPlayall.setOnClickListener(this);
        binding.userImg.setOnClickListener(this);
        binding.XLogin.setOnClickListener(this);
        binding.buttonPersonal.setOnClickListener(this);
        share.setOnClickListener(this);
        SHComment.setOnClickListener(this);
        //设置 Header式
        binding.refreshLayout.setRefreshHeader(new MaterialHeader(this));
        //取消Footer
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setDisableContentWhenRefresh(true);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: 歌单开始刷新");
                isload = true;

            }
        });
        setMargins(binding.rlTitle, 0, getStatusBarHeight(this), 0, 0);
        minDistance = DensityUtil.dp2px(MyLocalActivity.this, 85);
        deltaDistance = DensityUtil.dp2px(this, 300) - minDistance;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.SH_comment:
                XToastUtils.info("本地歌单");
                break;
            case R.id.share:
                showSimpleBottomSheetGrid();
                break;
            case R.id.button_personal:
                mPresenter.CollectionList(isCollection, playlistId,cookie_string);
                break;
            case R.id.XLogin:
                SongSheetDialog songSheetDialog = new SongSheetDialog(this, playlistName, description, playlistPicUrl, Sbackg);
                songSheetDialog.setCanceledOnTouchOutside(true);
                songSheetDialog.show();
                break;
            case R.id.user_img:
                intent.setClass(this, PersonalActivity.class);
                intent.putExtra(USER_ID, Sbean.getPlaylist().getUserId());
                startActivity(intent);
                break;
            case R.id.rl_playall:
                SharedPreferencesUtil.putData("Ykey", 2);
                adapter.PlayAll();
                binding.bottomController.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SongPlayManager.getInstance().isDisplay()) {
            binding.bottomController.setVisibility(View.VISIBLE);
        } else {
            binding.bottomController.setVisibility(View.GONE);
        }

        binding.appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

            }

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout) {
                float alphaPercent = (float) (binding.llPlay.getTop() - minDistance) / (float) deltaDistance;
                binding.ivCoverBg.setAlpha(alphaPercent);
                binding.XLogin.setAlpha(alphaPercent);
                binding.userImg.setAlpha(alphaPercent);
                binding.XTitle.setAlpha(alphaPercent);
                binding.tvPlaylistName.setAlpha(alphaPercent);
            }
        });
    }

    //底部弹出选择列表
    private void showSimpleBottomSheetGrid() {
        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.drawable.icon_more_operation_share_friend, "分享到微信", 0, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_moment, "分享到朋友圈", 1, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_weibo, "分享到微博", 2, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.icon_more_operation_share_chat, "分享到私信", 3, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener(new BottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(BottomSheet dialog, BottomSheetItemView itemView) {
                        dialog.dismiss();
                        XToastUtils.toast(itemView.toString());
                    }
                }).build().show();
    }

    private ObjectAnimator getAlphaAnimatorCover() {
        if (coverAlphaAnimator == null) {
            coverAlphaAnimator = ObjectAnimator.ofFloat(binding.ivCoverBg, "alpha", 0f, 0.5f);
            coverAlphaAnimator.setDuration(1500);
            coverAlphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        return coverAlphaAnimator;
    }

    private ObjectAnimator getAlphaAnimatorBg() {
        if (alphaAnimator == null) {
            alphaAnimator = ObjectAnimator.ofFloat(binding.background, "alpha", 0f, 0.5f);
            alphaAnimator.setDuration(1500);
            alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        return alphaAnimator;
    }

    /**
     * 该方法用url申请一个图片bitmap，并将其压缩成原图1/300，计算上半部分和下半部分颜色RGB平均值
     * 两个RGB去作为渐变色的两个点
     * 还要开子线程去计算...
     */
    public void calculateColors(String url) {
        new Thread(() -> {
            try {
                //渐变色的两个颜色
                URL fileUrl;
                Bitmap bitmap;
                fileUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inSampleSize = 270;
                bitmap = BitmapFactory.decodeStream(is, new Rect(), opt);

                Message msg = Message.obtain();
                msg.what = COMPLETED;
                msg.obj = new BitmapDrawable(bitmap);
                handler.sendMessage(msg);

                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onGetBannerSuccess(BannerBean bean) {

    }

    @Override
    public void onGetBannerFail(String e) {

    }

    @Override
    public void onGetRecommendPlayListSuccess(MainRecommendPlayListBean bean) {

    }

    @Override
    public void onGetRecommendPlayListFail(String e) {

    }

    @Override
    public void onGetRecommendPlayListAgainSuccess(MainRecommendPlayListBean bean) {

    }

    @Override
    public void onGetRecommendPlayListAgainFail(String e) {

    }

    @Override
    public void onGetDailyRecommendSuccess(DailyRecommendBean bean) {

    }

    @Override
    public void onGetDailyRecommendFail(String e) {

    }

    @Override
    public void onGetTopListSuccess(TopListBean bean) {

    }

    @Override
    public void onGetTopListFail(String e) {

    }

    @Override
    public void onGetPlayListSuccess(RecommendPlayListBean bean) {

    }

    @Override
    public void onGetPlayListFail(String e) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetPlaylistDetailSuccess(PlaylistDetailBean bean) {
        Log.d(TAG, "获取歌单成功 : " + bean);
//        Sbean = bean;
        Glide.with(this).load(bean.getPlaylist().getCreator().getAvatarUrl()).into(binding.userImg);
        songComment.setText("" + bean.getPlaylist().getCommentCount());
//        songids = "";
//        songidList.addAll(bean.getPlaylist().getTrackIds());
//        for (int i = 0; i < songidList.size(); i++) {
//            songid.add(songidList.get(i).getId());
//            if (i == songidList.size() - 1) {
//                songids = songids + songidList.get(i).getId();
//            } else {
//                songids = songids + songidList.get(i).getId() + ",";
//            }
//        }
//        mPresenter.getSongDetailAll(songids,cookie_string);
//        initSongs();
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    initSongs();
                } else {
                    Toast.makeText(this, "未授权，功能无法实现", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    public void initSongs() {
        Log.d(TAG,"开始初始化list");
        Cursor cursor = null;
        cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Log.d(TAG,"获取本地储存访问");
            while (cursor.moveToNext()) {
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String disName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                LocalSongBean song = new LocalSongBean(disName, artist, url);
                localSongList.add(song);
            }
            onGetSongDetailSuccess();
        }else {
            Log.d(TAG,"未授权本地储存访问");
            XToastUtils.error("未授权本地储存访问");
        }
    }
    @Override
    public void onGetPlaylistDetailFail(String e) {
        hideDialog();
        XToastUtils.error("网络请求失败，请检查网络再试");
    }

    @Override
    public void onGetMusicCanPlaySuccess(MusicCanPlayBean bean) {

    }

    @Override
    public void onGetMusicCanPlayFail(String e) {

    }

    @Override
    public void onGetHighQualitySuccess(HighQualityPlayListBean bean) {

    }

    @Override
    public void onGetHighQualityFail(String e) {

    }

    @Override
    public void onGetRecommendsongSuccess(RecommendsongBean bean) {

    }

    @Override
    public void onGetRecommendsongFail(String e) {

    }

    @Override
    public void onGetCollectionListSuccess(CollectionListBean bean) {
        if (isCollection == 1) {
            isCollection = 2;
            binding.buttonPersonal.setText("已收藏");
            XToastUtils.success("收藏成功");
        } else {
            isCollection = 1;
            binding.buttonPersonal.setText("收藏");
            XToastUtils.success("取消收藏成功");
        }
    }

    @Override
    public void onGetCollectionListFail(String e) {
        XToastUtils.error(e);
    }

    @Override
    public void onGetSongDetailSuccess(SongDetailBean bean) {

    }

    @Override
    public void onGetSongDetailSuccess() {
//        hideDialog();
        songInfos.clear();
//        beanList.clear();
//        beanList.addAll(bean.getSongs());
        Log.d(TAG, "获取当前本地歌曲数量："+String.valueOf((localSongList.size())));
        for (int i = 0; i < localSongList.size(); i++) {
            SongInfo beanInfo = new SongInfo();
            beanInfo.setArtist(localSongList.get(i).getSinger());
            beanInfo.setSongName(localSongList.get(i).getSongName());
            beanInfo.setSongCover("");
            beanInfo.setSongId(String.valueOf(33894312));
//            beanInfo.setDuration(Long.parseLong(""));
            beanInfo.setSongUrl(localSongList.get(i).getSongPath());
            beanInfo.setArtistId("");
            beanInfo.setArtistKey("");
            songInfos.add(beanInfo);
        }
        if (isload) {
            adapter.setList(songInfos);
            adapter.refresh(songInfos);
            Log.e(TAG, "歌单刷新成功");
            binding.refreshLayout.finishRefresh(true);
        } else {
            adapter.setList(songInfos);
            adapter.loadMore(songInfos);
        }
    }

    @Override
    public void onGetSongDetailFail(String e) {
        XToastUtils.error(e);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
class HeadsetPlugReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase("android.intent.action.HEADSET_PLUG")){
            if(intent.hasExtra("state")){
                if (intent.getIntExtra("state", 0) == 1){
                    Toast.makeText(context, "耳机已连接", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "耳机已断开", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}