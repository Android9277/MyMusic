package com.example.mymusic.views;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by 孙丹青 on 2019/7/11.
 * 作用:
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpcae;

    public GridSpaceItemDecoration(int space,RecyclerView parent){
        mSpcae = space;

        getRecyclerViewOffsets(parent);
    }

    /**
     *
     * @param outRect Item的边界
     * @param view itemView
     * @param parent RecyclerView
     * @param state RecyclerView的状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpcae;

        //判断item是否为第一个item
//        if (parent.getChildLayoutPosition(view) % 3 == 0){
//            outRect.left = 0;
//        }


    }
    private void getRecyclerViewOffsets(RecyclerView parent){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) parent.getLayoutParams();
        layoutParams.leftMargin = -mSpcae;
        parent.setLayoutParams(layoutParams);
    }
}
