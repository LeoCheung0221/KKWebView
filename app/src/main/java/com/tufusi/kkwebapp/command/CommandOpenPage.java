package com.tufusi.kkwebapp.command;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

import com.google.auto.service.AutoService;
import com.tufusi.kkbase.BaseApplication;
import com.tufusi.kkwebview.ICallbackFromMainprocess2WebViewProcessInterface;
import com.tufusi.kkwebview.command.Command;

import java.util.Map;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
@AutoService({Command.class})
public class CommandOpenPage implements Command {

    @Override
    public String name() {
        return "openPage";
    }

    @Override
    public void executeCmd(Map parameters, ICallbackFromMainprocess2WebViewProcessInterface callback) {
        String targetClass = String.valueOf(parameters.get("target_class"));
        if (!TextUtils.isEmpty(targetClass)) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(BaseApplication.kApplication, targetClass));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.kApplication.startActivity(intent);
        }
    }
}
