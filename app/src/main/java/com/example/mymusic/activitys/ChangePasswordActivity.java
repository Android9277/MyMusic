package com.example.mymusic.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.utils.UserUtils;
import com.example.mymusic.views.InputView;

public class ChangePasswordActivity extends BaseActivity {

    private InputView mOldPassWord,mPassWord,mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        initNavBar(true,"修改密码",false);

        mOldPassWord = fd(R.id.input_old_password);
        mPassWord = fd(R.id.input_password);
        mPasswordConfirm = fd(R.id.input_password_confirm);


    }

    public void onChangeEnterClick(View view) {
        String oldPassword = mOldPassWord.getInputStr();
        String password = mPassWord.getInputStr();
        String passwordConfirm = mPasswordConfirm.getInputStr();

        boolean result = UserUtils.changePassword(this, oldPassword, password, passwordConfirm);
        if (!result){
            return;
        }
        Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
        UserUtils.logOut(this);
    }
}
