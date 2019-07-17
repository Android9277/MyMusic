package com.example.mymusic.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mymusic.R;
import com.example.mymusic.helps.MediaPlayerHelp;
import com.example.mymusic.models.MusicModel;
import com.example.mymusic.services.MusicService;

/**
 * Created by 孙丹青 on 2019/7/12.
 * 作用:
 */

public class PlayMusicView extends FrameLayout {
    private MusicModel mMusicModel;
    private MusicService.MusicBind mMusicBinder;
    private Intent mServiceIntent;
    private boolean isPlaying, isBindService;
    private View mView;
    private FrameLayout mFlPlayMusic;
    private ImageView mIvIcon, mIvNeedle, mIvPlay;
    private Animation mPlayMusicAnim, mPlayNeedleAnim, mStopNeedleAnim;

    private Context mContext;

    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
//        MediaPlayer
        mContext = context;

        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music, this, false);

        mFlPlayMusic = mView.findViewById(R.id.fl_play_music);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                trigger();
            }
        });
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);

        /**
         * 1.定义所需要执行的动画
         *   1.光盘转动的动画
         *   2.指针指向光盘的动画
         *   3.指针离开光盘的动画
         * 2.startAnimation
         */

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.stop_needle_anim);

        addView(mView);

    }

    /**
     * 切换播放状态
     */

    private void trigger() {

        if (isPlaying) {
            stopMusic();
        } else {
            playMusic();
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic() {
        isPlaying = true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        startMusicService();

//        /**
//         * 1.判断当前音乐是否是在播放
//         * 2.如果当前音乐是在播放的音乐，直接调用start方法
//         * 3.如果当前音乐不是需要播放的音乐，就调用setPath
//         */

//        if (mMediaPlayerHelp.getPath() != null && mMediaPlayerHelp.getPath().equals(path)){
//            mMediaPlayerHelp.start();
//        } else {
//            mMediaPlayerHelp.setPath(path);
//            mMediaPlayerHelp.setOnMeidaPlayerHelperListener(new MediaPlayerHelp.OnMeidaPlayerHelperListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mMediaPlayerHelp.start();
//                }
//
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//
//                }
//            });
//        }
    }

    /**
     * 停止播放
     */
    public void stopMusic() {
        isPlaying = false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);

        if (mMusicBinder != null)
            mMusicBinder.stopMusic();

    }

    /**
     * 设置光盘中显示的音乐封面图片
     */
    public void setMusicIcon() {

        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }

    public void setMusic(MusicModel music) {
        mMusicModel = music;
        setMusicIcon();
    }

    /**
     * 启动音乐服务
     */
    public void startMusicService() {
//        启动Service
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        } else {
            mMusicBinder.playMusic();
        }

//        绑定Service，当前Service未绑定，绑定服务
        if (!isBindService) {
            isBindService = true;
            mContext.bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
        }

    }

    /**
     * 解除绑定
     */
    public void destory() {
//        如果已经绑定了服务，解除绑定
        if (isBindService) {
            isBindService = false;
            mContext.unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mMusicBinder = (MusicService.MusicBind) iBinder;
            mMusicBinder.setMusic(mMusicModel);
            mMusicBinder.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
