package com.emotion.emotiontech.View;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.emotion.emotiontech.R;

import butterknife.Bind;
import butterknife.OnClick;
import felix.lib.Base.Aty.BaseAty;
import felix.lib.Base.BindLayout;

/**
 * Created by felix on 2016/12/3.
 */
@BindLayout(R.layout.aty_main)
public class MainAty extends BaseAty
{
    @Bind(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void initData() {
        super.initData();
        test();
    }

    private void test() {
        new Handler().postDelayed(() -> {
            startAty(EmotionAddAty.class);
        }, 500);
    }

    @OnClick(R.id.tv_test)
    public void onClick(View view) {
        test();
    }
}
