package com.tufusi.kkwebview;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public interface IWebViewCallBack {

    void pageStarted(String url);

    void pageFinished(String url);

    void onError();

    void updateTitle(String title);

}
