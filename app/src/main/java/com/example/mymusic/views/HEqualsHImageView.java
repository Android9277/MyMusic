package com.example.mymusic.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by 孙丹青 on 2019/7/11.
 * 作用:
 */

public class HEqualsHImageView extends AppCompatImageView {
    public HEqualsHImageView(Context context) {
        super(context);
    }

    public HEqualsHImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HEqualsHImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

//        //获取View的宽度
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//
//        //获取View模式
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
    }
}
