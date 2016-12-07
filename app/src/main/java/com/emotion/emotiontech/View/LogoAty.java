package com.emotion.emotiontech.View;

import com.emotion.emotiontech.R;

import felix.lib.Base.Aty.BaseLogoAty;

/**
 * Created by felix on 2016/12/3.
 */
public class LogoAty extends BaseLogoAty
{
    @Override
    protected Class getClsAty() {
        return MainAty.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_logo;
    }

    @Override
    protected int getTime() {
        return 500;
    }
}
