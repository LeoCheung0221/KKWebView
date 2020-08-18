package com.tufusi.kkwebapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tufusi.kkbase.autoservice.VickyServiceLoader;
import com.tufusi.kkcommon.autoservice.IWebViewService;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_webviewactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWebViewService webViewService = VickyServiceLoader.load(IWebViewService.class);
                if (webViewService != null) {
                    webViewService.startTargetHtml(MainActivity.this);
                }
            }
        });
    }
}
