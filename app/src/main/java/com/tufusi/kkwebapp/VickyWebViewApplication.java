package com.tufusi.kkwebapp;

import com.kingja.loadsir.core.LoadSir;
import com.tufusi.kkbase.BaseApplication;
import com.tufusi.kkbase.loadsir.CustomCallback;
import com.tufusi.kkbase.loadsir.EmptyCallback;
import com.tufusi.kkbase.loadsir.ErrorCallback;
import com.tufusi.kkbase.loadsir.LoadingCallback;
import com.tufusi.kkbase.loadsir.TimeoutCallback;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class VickyWebViewApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }
}
