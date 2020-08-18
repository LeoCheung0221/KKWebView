package com.tufusi.kkwebview.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.tufusi.kkwebview.IWebViewCallBack;
import com.tufusi.kkwebview.WebViewFragment;
import com.tufusi.kkwebview.bean.JsParam;
import com.tufusi.kkwebview.core.chromeclient.VickyWebChromeClient;
import com.tufusi.kkwebview.core.client.VickyWebViewClient;
import com.tufusi.kkwebview.core.settings.WebViewDefaultSettings;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class BaseWebView extends WebView {

    public static final String TAG = "VickyMeWebView";

    public BaseWebView(Context context) {
        this(context, null);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        WebViewProcessCommandDispatcher.getInstance().initAidlConnection();
        WebViewDefaultSettings.getInstance().setSettings(this);
        addJavascriptInterface(this, "vickymewebview");
    }


    public void registerWebViewCallBack(IWebViewCallBack callBack) {
        setWebViewClient(new VickyWebViewClient(callBack));
        setWebChromeClient(new VickyWebChromeClient(callBack));
    }

    @JavascriptInterface
    public void takeNativeAction(final String jsParam) {
        Log.i(TAG, jsParam);
        if (!TextUtils.isEmpty(jsParam)) {
            JsParam jsParamObject = new Gson().fromJson(jsParam, JsParam.class);
            if (jsParamObject != null) {
                WebViewProcessCommandDispatcher.getInstance()
                        .executeCommand(jsParamObject.name, new Gson().toJson(jsParamObject.param), this);
            }
        }
    }

    public void handleCallback(final String callbackName, final String response) {
        if (!TextUtils.isEmpty(callbackName) && !TextUtils.isEmpty(response)) {
            post(new Runnable() {
                @Override
                public void run() {
                    String jsCode = "javascript:vickymejs.callback('" + callbackName + "'," + response + ")";
                    Log.e("xxxxxx", jsCode);
                    evaluateJavascript(jsCode, null);
                }
            });
        }
    }
}
