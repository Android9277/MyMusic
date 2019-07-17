package com.example.mymusic.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by 孙丹青 on 2019/7/15.
 * 作用:
 */

public class MusicSourceModel extends RealmObject {
    private RealmList<AlbumModel> album;
    private RealmList<MusicModel> hot;

    public RealmList<AlbumModel> getAlbum() {
        return album;
    }

    public void setAlbum(RealmList<AlbumModel> album) {
        this.album = album;
    }

    public RealmList<MusicModel> getHot() {
        return hot;
    }

    public void setHot(RealmList<MusicModel> hot) {
        this.hot = hot;
    }
}
