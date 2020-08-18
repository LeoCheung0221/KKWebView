package com.tufusi.kkcommon.autoservice;

import android.content.Context;

import androidx.fragment.app.Fragment;

/**
 * Created by 鼠夏目 on 2020/8/11.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public interface IWebViewService {

    void startWebViewActivity(Context context, String url, String title, boolean isShowActionBar);

    Fragment getWebViewFragment(String url, boolean enableNativeRefresh);

    void startTargetHtml(Context context);
}
