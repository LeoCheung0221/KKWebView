package com.tufusi.kkbase;

import android.app.Application;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class BaseApplication extends Application {

    public static Application kApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        kApplication = this;
    }
}
