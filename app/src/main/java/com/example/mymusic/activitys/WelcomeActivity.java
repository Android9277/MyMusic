package com.example.mymusic.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mymusic.R;
import com.example.mymusic.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {


    //1.延时三秒
    //2.跳转页面

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        final boolean login = UserUtils.validateUserLogin(this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Log.e("WelcomeActivity","当前线程为:"+Thread.currentThread());
//                toMain();
                if (login) {
                    toMain();
                } else {
                    toLogin();
                }
            }
        }, 2 * 1000);

    }

    /**
     * 跳转到MainActivity
     */
    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 跳转到LoginActivity
     */
    private void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
