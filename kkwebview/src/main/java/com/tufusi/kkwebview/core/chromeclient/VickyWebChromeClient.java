package com.tufusi.kkwebview.core.chromeclient;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.tufusi.kkwebview.IWebViewCallBack;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class VickyWebChromeClient extends WebChromeClient {

    private static final String TAG = "VickyWebChromeClient";

    private IWebViewCallBack mWebViewCallBack;

    public VickyWebChromeClient(IWebViewCallBack callBack) {
        mWebViewCallBack = callBack;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mWebViewCallBack != null) {
            mWebViewCallBack.updateTitle(title);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     *
     * @param consoleMessage Object containing details of the console message.
     * @return {@code true} if the message is handled by the client.
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        // Call the old version of this function for backwards compatability.
        Log.d(TAG, consoleMessage.message());
        return super.onConsoleMessage(consoleMessage);
    }
}
