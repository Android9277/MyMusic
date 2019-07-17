package com.example.mymusic.activitys;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.adapters.MusicGridAdapter;
import com.example.mymusic.adapters.MusicListAdapter;
import com.example.mymusic.helps.RealmHelper;
import com.example.mymusic.models.MusicSourceModel;
import com.example.mymusic.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {



    private RecyclerView mRvGrid ,mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;
    private RealmHelper mRealmHelper;
    private MusicSourceModel mMusicSourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new RealmHelper();
        mMusicSourceModel = mRealmHelper.getMusicSource();
    }


    private void initView() {
        initNavBar(false, "音乐主页", true);
        mRvGrid = fd(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize), mRvGrid));
        //滑动冲突
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter = new MusicGridAdapter(this,mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mGridAdapter);

        /**
         * RecyclerView高度计算
         */


        mRvList = fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //滑动冲突
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter = new MusicListAdapter(this,mRvList,mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }
}
