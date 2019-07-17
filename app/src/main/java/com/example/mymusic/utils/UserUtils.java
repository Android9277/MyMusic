package com.example.mymusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.mymusic.R;
import com.example.mymusic.activitys.LoginActivity;
import com.example.mymusic.helps.RealmHelper;
import com.example.mymusic.helps.UserHelper;
import com.example.mymusic.models.UserModel;

import java.util.List;

/**
 * Created by 孙丹青 on 2019/7/11.
 * 作用:
 */

public class UserUtils {

    /**
     * 验证用户登录合法性
     */
    public static boolean validateLogin(Context context, String phone, String password) {
        //简单的
//        RegexUtils.isMobileSimple(phone);
        //精确的
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        /**
         * 1、用户当前手机号是否被注册了
         * 2、用户输入的手机号和密码是否匹配
         */
        if (!UserUtils.userExistFromPhone(phone)) {
            Toast.makeText(context, "当前手机号未注册", Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelper realmHelp = new RealmHelper();
        boolean result = realmHelp.validataUser(phone, EncryptUtils.encryptMD5ToString(password));
        if (!result) {
            Toast.makeText(context, "手机号或者密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        //保存用户登录标记
        boolean isSave = SPUtils.saveUser(context, phone);
        if (!isSave) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }

        //保存用户标记，在全局单例类中
        UserHelper.getInstance().setPhone(phone);

//        保存音乐原数据
        realmHelp.setMusicSource(context);

        realmHelp.close();
        return true;
    }

    /**
     * 退出登录
     */

    public static void logOut(Context context) {
        //删除sp保存的用户标记
        boolean isRemove = SPUtils.removeUser(context);

        if (!isRemove) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }

//        删除数据源
        RealmHelper realmHelp = new RealmHelper();
        realmHelp.removeMusicSource();
        realmHelp.close();

        Intent intent = new Intent(context, LoginActivity.class);

        //添加intent标志符，清理task栈，并且生成一个新的task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义跳转Activity动画
        ((Activity) context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }

    /**
     * 注册用户
     *
     * @param context        上下文
     * @param phone
     * @param password
     * @param passwordConfim
     */
    public static boolean registerUser(Context context, String phone, String password, String passwordConfim) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (StringUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(passwordConfim)) {
            Toast.makeText(context, "密码不一致，请检查", Toast.LENGTH_SHORT).show();
            return false;
        }


        //用户当前手机号是否已经被注册

        if (UserUtils.userExistFromPhone(phone)) {
            Toast.makeText(context, "当前手机号" + phone + "已被注册！", Toast.LENGTH_SHORT).show();
            return false;
        }
        /**
         * 1、通过Realm获取到当前已经注册的所有用户
         * 2、根据用户输入的手机号匹配所有用户，如果可以匹配证明该手机号已经被注册了
         */


        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);
        return true;
    }

    /**
     * 保存用户数据到数据库
     *
     * @param userModel
     */
    public static void saveUser(UserModel userModel) {

        RealmHelper realmHelp = new RealmHelper();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }

    /**
     * 根据用户输入的手机号匹配所有用户，如果可以匹配证明该手机号已经被注册了
     */
    public static boolean userExistFromPhone(String phone) {
        boolean result = false;

        RealmHelper realmHelp = new RealmHelper();
        List<UserModel> allUser = realmHelp.getAllUser();

        for (UserModel userModel : allUser) {
            if (userModel.getPhone().equals(phone)) {
                //当前手机号已经存在
                result = true;
                break;
            }
        }

        realmHelp.close();
        return result;
    }

    /**
     * 验证是否存在已经登录用户
     */

    public static boolean validateUserLogin(Context context) {
        return SPUtils.isLoginUser(context);
    }


    /**
     * 修改密码
     * 1.数据验证
     * 1.原密码是否输入
     * 2.新密码是否输入并且密码与确定密码是否相等
     * 3.新密码数据是否正确
     * 1.Realm获取到当前用户登录模型
     * 2.根据用户模型中保存的密码匹配用户原密码
     * 2.利用Realm模型自动更新的特性完成密码的修改
     */
    public static boolean changePassword(Context context, String oldPassword, String password, String passwordConfirm) {

        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }


        /**
         * 验证原来密码是否正确
         */
        RealmHelper realmHelp = new RealmHelper();
        UserModel userModel = realmHelp.getUser();
        if (!EncryptUtils.encryptMD5ToString(oldPassword).equals(userModel.getPassword())) {
            Toast.makeText(context, "原密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        }
        realmHelp.changePassword(EncryptUtils.encryptMD5ToString(password));
        realmHelp.close();

        return true;

    }
}

