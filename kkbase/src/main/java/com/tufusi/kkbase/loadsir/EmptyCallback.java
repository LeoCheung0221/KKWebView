package com.tufusi.kkbase.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.tufusi.kkbase.R;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }
}
