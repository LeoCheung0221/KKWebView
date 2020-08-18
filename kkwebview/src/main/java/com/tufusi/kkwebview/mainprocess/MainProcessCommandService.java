package com.tufusi.kkwebview.mainprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class MainProcessCommandService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return MainProcessCommandManager.getInstance();
    }
}
