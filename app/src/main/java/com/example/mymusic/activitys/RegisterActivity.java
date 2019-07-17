package com.example.mymusic.activitys;

import android.os.Bundle;
import android.view.View;

import com.example.mymusic.R;
import com.example.mymusic.utils.UserUtils;
import com.example.mymusic.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword,mInputPasswordConfim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
            initNavBar(true,"注册",false);
            mInputPhone = fd(R.id.input_phone);
            mInputPassword = fd(R.id.input_password);
            mInputPasswordConfim = fd(R.id.input_password_confirm);
    }

    /**
     * 注册
     * 1、首先验证用户输入合法性验证
     *   1、用户输入的手机号是否合法
     *   2、用户是否输入了密码和确认密码，并且是否一致
     *   3、输入的手机号是否已经被注册
     * 2、保存用户输入手机号和密码（MD5加密密码）
     */
    public void onRegisterClick(View view) {


        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfim.getInputStr();

        boolean result = UserUtils.registerUser(this, phone, password, passwordConfirm);

        if (!result) return;

        //后退页面
        onBackPressed();
    }
}
