package com.example.mymusic.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.helps.UserHelper;
import com.example.mymusic.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        initNavBar(true,"个人中心",false);
        mTextView = fd(R.id.tv_user);
        String phone = UserHelper.getInstance().getPhone();
        mTextView.setText("用户:"+phone);
    }

    /**
     * 修改密码
     * @param view
     */
    public void onChangeClick(View view) {

        Intent intent = new Intent(this,ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 退出登录
     * @param view
     */
    public void onLogoutClick(View view) {

        UserUtils.logOut(this);
    }
}
