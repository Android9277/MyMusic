package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymusic.R;
import com.example.mymusic.activitys.PlayMusicActivity;
import com.example.mymusic.models.MusicModel;
import com.example.mymusic.views.PlayMusicView;

import java.util.List;

/**
 * Created by 孙丹青 on 2019/7/12.
 * 作用:
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulationRvHeight;
    private List<MusicModel> mDataSource;

    public MusicListAdapter(Context context, RecyclerView recyclerView,List<MusicModel> dataSource){
        mContext = context;
        mRv = recyclerView;
        mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music,viewGroup,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        setRecyclerViewHeight();

        final MusicModel musicModel = mDataSource.get(i);

        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(viewHolder.ivIcon);

        viewHolder.tvName.setText(musicModel.getName());
        viewHolder.tvAuthor.setText(musicModel.getAuthor());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID,musicModel.getMusicId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }


    /**
     * 1、获取itemView的高度
     * 2、itemView的数量
     */
    private void setRecyclerViewHeight(){

        if (isCalcaulationRvHeight || mRv == null) return;

        isCalcaulationRvHeight = true;

//        1、获取itemView的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
//        2、itemView的数量
        int itemCount = getItemCount();

        int recyclerViewHeight = itemViewLp.height * itemCount;

//        设置RecyclerView的高度
        LinearLayout.LayoutParams rvlP = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvlP.height = recyclerViewHeight;
        mRv.setLayoutParams(rvlP);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView ivIcon;
        TextView tvName,tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
