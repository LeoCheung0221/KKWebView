package com.tufusi.kkwebview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.common.cache.LoadingCache;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tufusi.kkbase.loadsir.ErrorCallback;
import com.tufusi.kkbase.loadsir.LoadingCallback;
import com.tufusi.kkwebview.databinding.FragmentWebviewBinding;
import com.tufusi.kkwebview.utils.Constants;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class WebViewFragment extends Fragment implements IWebViewCallBack, OnRefreshListener {

    private static final String TAG = "WebViewFragment";

    private String mUrl;
    private boolean isEnableNativeRefresh;
    private FragmentWebviewBinding mBinding;
    private LoadService mLoadService;
    private boolean isError;

    public static Fragment newInstance(String url, boolean enableNativeRefresh) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        bundle.putBoolean(Constants.CAN_NATIVE_REFRESH, enableNativeRefresh);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(Constants.URL);
            isEnableNativeRefresh = bundle.getBoolean(Constants.CAN_NATIVE_REFRESH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false);
        mBinding.webview.registerWebViewCallBack(this);
        mBinding.webview.loadUrl(mUrl);

        mLoadService = LoadSir.getDefault().register(mBinding.smartRefreshLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mLoadService.showCallback(LoadingCache.class);
                mBinding.webview.reload();
            }
        });

        mBinding.smartRefreshLayout.setOnRefreshListener(this);
        mBinding.smartRefreshLayout.setEnableRefresh(isEnableNativeRefresh);
        mBinding.smartRefreshLayout.setEnableLoadMore(false);

        return mLoadService.getLoadLayout();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mBinding.webview.reload();
    }

    @Override
    public void pageStarted(String url) {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void pageFinished(String url) {
        if (isError) {
            mBinding.smartRefreshLayout.setEnableRefresh(true);
        } else {
            mBinding.smartRefreshLayout.setEnableRefresh(isEnableNativeRefresh);
        }
        Log.d(TAG, "pageFinished");
        mBinding.smartRefreshLayout.finishRefresh();

        if (mLoadService != null) {
            if (isError) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                mLoadService.showSuccess();
            }
        }

        isError = false;
    }

    @Override
    public void onError() {
        Log.e(TAG, "onError");
        isError = true;
        mBinding.smartRefreshLayout.finishRefresh();
    }

    @Override
    public void updateTitle(String title) {
        if (getActivity() instanceof WebViewActivity) {
            ((WebViewActivity) getActivity()).updateTitle(title);
        }
    }
}
