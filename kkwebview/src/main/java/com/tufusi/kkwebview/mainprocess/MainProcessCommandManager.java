package com.tufusi.kkwebview.mainprocess;

import android.os.IBinder;
import android.os.RemoteException;

import com.google.gson.Gson;
import com.tufusi.kkwebview.ICallbackFromMainprocess2WebViewProcessInterface;
import com.tufusi.kkwebview.IWebViewProcess2MainprocessInterface;
import com.tufusi.kkwebview.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class MainProcessCommandManager extends IWebViewProcess2MainprocessInterface.Stub {

    private static MainProcessCommandManager mInstance;

    private static HashMap<String, Command> mCommands = new HashMap<>();

    public static MainProcessCommandManager getInstance() {
        if (mInstance == null) {
            synchronized (MainProcessCommandManager.class) {
                mInstance = new MainProcessCommandManager();
            }
        }
        return mInstance;
    }

    private MainProcessCommandManager() {
        ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
        for (Command command : serviceLoader) {
            if (!mCommands.containsKey(command.name())) {
                mCommands.put(command.name(), command);
            }
        }
    }

    @Override
    public void handleWebCommand(String commandName, String jsonParams,
                                 ICallbackFromMainprocess2WebViewProcessInterface callback) throws RemoteException {
        MainProcessCommandManager.getInstance().executeCommand(commandName, new Gson().fromJson(jsonParams, Map.class), callback);
    }

    public void executeCommand(String commandName, Map params, ICallbackFromMainprocess2WebViewProcessInterface callback) {
        mCommands.get(commandName).executeCmd(params, callback);
    }
}
