// IWebViewProcess2MainprocessInterface.aidl
package com.tufusi.kkwebview;

import com.tufusi.kkwebview.ICallbackFromMainprocess2WebViewProcessInterface;

interface IWebViewProcess2MainprocessInterface {
    void handleWebCommand(String commandName, String jsonParams, in ICallbackFromMainprocess2WebViewProcessInterface callback);
}
