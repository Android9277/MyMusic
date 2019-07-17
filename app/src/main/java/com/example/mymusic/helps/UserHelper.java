package com.example.mymusic.helps;

/**
 * Created by 孙丹青 on 2019/7/13.
 * 作用:
 * 1、用户登录
 *   1、当用户登录时，利用SharedPreferences保存用户标记（手机号）
 *   2、利用全局单例类UserHelper保存用户登录信息
 *     1、用户登录之后
 *     2、用户打开应用程序 检测SharedPreferences中是否存在用户登录标记
 *        ，如果存在则为UserHelper进行赋值，并且进入主页。如果不存在，则进入登录页面
 * 2、用户退出
 *   1、删除掉SharedPreferences保存的用户标记，退出到登录页面
 */

public class UserHelper {
    private static UserHelper instance;

    private UserHelper() {};

    public static UserHelper getInstance() {
        if (instance == null) {
            synchronized (UserHelper.class) {
                if (instance == null) {
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
