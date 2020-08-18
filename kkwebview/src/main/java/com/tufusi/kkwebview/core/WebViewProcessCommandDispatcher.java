package com.tufusi.kkwebview.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.tufusi.kkbase.BaseApplication;
import com.tufusi.kkwebview.ICallbackFromMainprocess2WebViewProcessInterface;
import com.tufusi.kkwebview.IWebViewProcess2MainprocessInterface;
import com.tufusi.kkwebview.mainprocess.MainProcessCommandService;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class WebViewProcessCommandDispatcher implements ServiceConnection {

    private static WebViewProcessCommandDispatcher mInstance;
    private IWebViewProcess2MainprocessInterface iWebViewProcess2MainprocessInterface;

    public static WebViewProcessCommandDispatcher getInstance() {
        if (mInstance == null) {
            synchronized (WebViewProcessCommandDispatcher.class) {
                mInstance = new WebViewProcessCommandDispatcher();
            }
        }
        return mInstance;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        iWebViewProcess2MainprocessInterface
                = IWebViewProcess2MainprocessInterface.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        iWebViewProcess2MainprocessInterface = null;
        initAidlConnection();
    }

    @Override
    public void onBindingDied(ComponentName name) {
        iWebViewProcess2MainprocessInterface = null;
        initAidlConnection();
    }

    public void initAidlConnection() {
        Intent intent = new Intent(BaseApplication.kApplication, MainProcessCommandService.class);
        BaseApplication.kApplication.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void executeCommand(String commandName, String params, final BaseWebView baseWebView) {
        if (iWebViewProcess2MainprocessInterface != null) {
            try {
                iWebViewProcess2MainprocessInterface.handleWebCommand(
                        commandName, params, new ICallbackFromMainprocess2WebViewProcessInterface.Stub() {
                            @Override
                            public void onResult(String callbackName, String response) throws RemoteException {
                                baseWebView.handleCallback(callbackName, response);
                            }
                        }
                );
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }
}
