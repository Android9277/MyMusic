package com.example.mymusic.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by 孙丹青 on 2019/7/12.
 * 作用:音乐播放
 */

public class MediaPlayerHelp {


    private static MediaPlayerHelp instance;

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private OnMeidaPlayerHelperListener onMeidaPlayerHelperListener;

    public void setOnMeidaPlayerHelperListener(OnMeidaPlayerHelperListener onMeidaPlayerHelperListener) {
        this.onMeidaPlayerHelperListener = onMeidaPlayerHelperListener;
    }

    public static MediaPlayerHelp getInstance(Context context) {

        if (instance == null) {
            synchronized (MediaPlayerHelp.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelp(context);
                }
            }
        }

        return instance;

    }

    private MediaPlayerHelp(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }


    /**
     * 1、setPath：当前需要播放的音乐
     * 2、start：播放音乐
     * 3、pause：暂停播放
     */

    /**
     * 当前需要播放的音乐
     *
     * @param path
     */
    public void setPath(String path) {
        /**
         * 1、音乐正在播放，或者切换了音乐，重置音乐播放状态
         * 2、设置播放音乐路径
         * 3、准备播放
         */

        /**
         * （逻辑错误！）当音乐进行切换的时候，如果音乐处于播放状态，
         * 那么我们就去重置音乐的播放状态，而如果音乐没有处于播放状态（暂停状态），
         * 那么就不去重置播放状态
         */
//        1、音乐正在播放，或者切换了音乐，重置音乐播放状态
        if (mMediaPlayer.isPlaying() || !path.equals(mPath)) {
            mMediaPlayer.reset();
        }
        mPath = path;

//        2、设置播放音乐路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        3、准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMeidaPlayerHelperListener != null) {
                    onMeidaPlayerHelperListener.onPrepared(mp);
                }
            }
        });

//        监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (onMeidaPlayerHelperListener != null) {
                    onMeidaPlayerHelperListener.onCompletion(mediaPlayer);
                }
            }
        });

//        监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onMeidaPlayerHelperListener != null) {
                    onMeidaPlayerHelperListener.onCompletion(mp);
                }
            }
        });

    }

    /**
     * 返回正在播放的音乐路径
     *
     * @return
     */
    public String getPath() {
        return mPath;
    }

    /**
     * 播放音乐
     */
    public void start() {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause() {
        mMediaPlayer.pause();
    }

    public interface OnMeidaPlayerHelperListener {
        void onPrepared(MediaPlayer mp);

        void onCompletion(MediaPlayer mp);
    }
}
