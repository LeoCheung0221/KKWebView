package com.tufusi.kkwebview.command;

import com.tufusi.kkwebview.ICallbackFromMainprocess2WebViewProcessInterface;

import java.util.Map;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public interface Command {

    String name();

    void executeCmd(Map parameters, ICallbackFromMainprocess2WebViewProcessInterface callback);
}
