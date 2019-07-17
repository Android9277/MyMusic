package com.example.mymusic.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.utils.UserUtils;
import com.example.mymusic.views.InputView;

public class LoginActivity extends BaseActivity {


    private static InputView mInputphone, mInputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
        initView();
    }

    private void initData() {

    }

    /**
     * 初始化View
     */
    private void initView() {
        initNavBar(false, "登录", false);
        mInputphone = fd(R.id.input_phone);
        mInputPassword = fd(R.id.input_password);

    }

    /**
     * 跳转注册页面点击事件
     */
    public void onRegisterClick(View v) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    /**
     * 登录点击事件
     *
     * @param v
     */
    public void onCommitClick(View v) {

        String phone = mInputphone.getInputStr();
        String password = mInputPassword.getInputStr();


        //验证用户输入是否合法

        if (!UserUtils.validateLogin(this, phone, password)) {
            return;
        }


        //跳转到应用主页
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Toast.makeText(this, "成功登录", Toast.LENGTH_SHORT).show();
        finish();
    }


}
