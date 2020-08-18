package com.tufusi.kkwebapp.command;

import android.os.RemoteException;
import android.util.Log;

import com.google.auto.service.AutoService;
import com.google.gson.Gson;
import com.kingja.loadsir.core.LoadService;
import com.tufusi.kkbase.autoservice.VickyServiceLoader;
import com.tufusi.kkcommon.autoservice.IUserCenterService;
import com.tufusi.kkcommon.eventbus.LoginEvent;
import com.tufusi.kkwebview.ICallbackFromMainprocess2WebViewProcessInterface;
import com.tufusi.kkwebview.command.Command;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
@AutoService({Command.class})
public class CommandLogin implements Command {

    IUserCenterService iUserCenterService = VickyServiceLoader.load(IUserCenterService.class);
    private ICallbackFromMainprocess2WebViewProcessInterface callback;
    private String callbackNameFromNativeJs;

    public CommandLogin() {
        EventBus.getDefault().register(this);
    }

    @Override
    public String name() {
        return "login";
    }

    @Override
    public void executeCmd(Map parameters, ICallbackFromMainprocess2WebViewProcessInterface callback) {
        Log.d("CommandLogin", new Gson().toJson(parameters));
        if (iUserCenterService != null && !iUserCenterService.isLogin()) {
            iUserCenterService.login();
            this.callback = callback;
            this.callbackNameFromNativeJs = parameters.get("callbackName").toString();
        }
    }

    @Subscribe
    public void onMessageEvent(LoginEvent event) {
        Log.d("CommandLogin", event.userName);
        HashMap<String, String> map = new HashMap<>();
        map.put("accountName", event.userName);

        if (this.callback != null) {
            try {
                this.callback.onResult(callbackNameFromNativeJs, new Gson().toJson(map));
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
}
