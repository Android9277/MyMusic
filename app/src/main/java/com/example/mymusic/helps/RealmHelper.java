package com.example.mymusic.helps;

import android.content.Context;

import com.example.mymusic.migration.Migration;
import com.example.mymusic.models.AlbumModel;
import com.example.mymusic.models.MusicModel;
import com.example.mymusic.models.MusicSourceModel;
import com.example.mymusic.models.UserModel;
import com.example.mymusic.utils.DataUtils;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by 孙丹青 on 2019/7/13.
 * 作用:RealmHelp帮助类
 */

public class RealmHelper {

    /**
     * Realm Studio
     * s928042740@163.com
     * ss19991021@cl
     */

    private Realm mRealm;

    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * Realm数据库发生结构性的变化时，需要对数据库进行迁移
     */

    /**
     * 告诉Realm数据需要迁移，并且为Realm设置最新的配置
     */
    public static void migration(){
        RealmConfiguration conf = getRealmConf();
//        为Realm设置最新的配置
        Realm.setDefaultConfiguration(conf);
//        告诉Realm数据需要迁移
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回 RealmConfiguration
     * @return
     */
    private static RealmConfiguration getRealmConf(){
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

    /**
     * 保存用户信息
     */
    public void saveUser(UserModel userModel) {
        mRealm.beginTransaction();
        mRealm.insert(userModel);
//        mRealm.insertOrUpdate(userModel);
        mRealm.commitTransaction();

    }

    /**
     * 返回所有用户
     */
    public List<UserModel> getAllUser() {
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        RealmResults<UserModel> results = query.findAll();
        return results;
    }

    /**
     * 验证用户信息
     */
    public boolean validataUser(String phone, String password) {
        boolean result = false;

        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        query = query.equalTo("phone", phone)
                .equalTo("password", password);
        UserModel userModel = query.findFirst();

        if (userModel != null) {
            result = true;
        }
        return result;
    }

    /**
     * 获取当前用户
     */

    public UserModel getUser(){
        RealmQuery<UserModel> query = mRealm.where(UserModel.class);
        UserModel userModel = query.equalTo("phone", UserHelper.getInstance().getPhone()).findFirst();
        return userModel;
    }

    /**
     * 修改密码
     */

    public void changePassword(String password){
        UserModel userModel = getUser();
        mRealm.beginTransaction();
        userModel.setPassword(password);
        mRealm.commitTransaction();
    }

    /**
     * 1.用户登录，存放数据
     * 2.用户退出，删除数据
     */

    /**
     * 保存音乐原数据
     */

    public void setMusicSource(Context context){
//        拿到资源文件中的数据
        String musicSourceJson = DataUtils.getJsonFromAssets(context, "DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class,musicSourceJson);
        mRealm.commitTransaction();

    }

    /**
     * 删除音乐原数据
     * 1.RealmResult delete
     * 2.Realm delete 删除这个模型下所有的数据
     */

    public void removeMusicSource(){
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }
    /**
     * 返回音乐源数据
     */

    public MusicSourceModel getMusicSource(){
        return mRealm.where(MusicSourceModel.class).findFirst();
    }
    /**
     * 返回歌单
     */
    public AlbumModel getAlbum(String albumId){
        return mRealm.where(AlbumModel.class).equalTo("albumId",albumId).findFirst();
    }

    /**
     * 返回音乐
     */
    public MusicModel getMusic(String musicId){
        return mRealm.where(MusicModel.class).equalTo("musicId",musicId).findFirst();
    }


}
