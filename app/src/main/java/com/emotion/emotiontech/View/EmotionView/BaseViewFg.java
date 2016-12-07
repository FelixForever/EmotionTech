package com.emotion.emotiontech.View.EmotionView;

import android.view.View;

import com.emotion.emotiontech.View.ViewPresenter;

import felix.lib.Base.Aty.BaseFg;

/**
 * Created by felix on 2016/12/5.
 */

public class BaseViewFg extends BaseFg
{
    protected static ViewPresenter mViewPresenter;

    @Override
    protected void initData(View view) {
        super.initData(view);
        if (getActivity() instanceof ViewPresenter) {
            mViewPresenter = ((ViewPresenter) getActivity());
        }
    }

    public void setViewPresenter(ViewPresenter viewPresenter) {
        if (mViewPresenter != null)
            mViewPresenter = viewPresenter;
    }
}
