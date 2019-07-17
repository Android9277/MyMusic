package com.example.mymusic.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by 孙丹青 on 2019/7/13.
 * 作用:用户模型
 */

public class UserModel extends RealmObject {

    @PrimaryKey
    private String phone; //主键
    @Required
    private String password; //非空

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
