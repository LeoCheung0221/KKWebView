package com.tufusi.kkwebapp.command;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

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
public class CommandShowToast implements Command {

    @Override
    public String name() {
        return "showToast";
    }

    @Override
    public void executeCmd(Map parameters, ICallbackFromMainprocess2WebViewProcessInterface callback) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.kApplication, String.valueOf(parameters.get("message")), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
