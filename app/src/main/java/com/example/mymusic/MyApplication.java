package com.example.mymusic;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.mymusic.helps.RealmHelper;

import io.realm.Realm;

/**
 * Created by 孙丹青 on 2019/7/11.
 * 作用:
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        Realm.init(this);

        RealmHelper.migration();
    }
}
