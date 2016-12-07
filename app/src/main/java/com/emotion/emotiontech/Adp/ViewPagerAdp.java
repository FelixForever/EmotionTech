package com.emotion.emotiontech.Adp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.emotion.emotiontech.View.EmotionView.BackgroundFg;
import com.emotion.emotiontech.View.EmotionView.BaseViewFg;
import com.emotion.emotiontech.View.EmotionView.FontFg;
import com.emotion.emotiontech.View.EmotionView.MaterialFg;
import com.emotion.emotiontech.View.EmotionView.PaintFg;
import com.emotion.emotiontech.View.EmotionView.ToolFg;
import com.emotion.emotiontech.View.ViewPresenter;

/**
 * Created by felix on 2016/12/5.
 */

public class ViewPagerAdp extends FragmentPagerAdapter
{
    private static final int MaterialPosition = 0;
    private static final int PaintPosition = 1;
    private static final int FontPosition = 2;
    private static final int BackgroundPosition = 3;
    private static final int ToolPosition = 4;

    protected ViewPresenter mViewPresenter;
    protected Context mContext;

    public ViewPagerAdp(Context context, FragmentManager fm, ViewPresenter viewPresenter) {
        super(fm);
        mContext = context;
        mViewPresenter = viewPresenter;
    }

    @Override
    public Fragment getItem(int position) {
        BaseViewFg baseViewFg = null;
        switch (position) {
            case MaterialPosition:
                baseViewFg = new MaterialFg();
                break;
            case PaintPosition:
                baseViewFg = new PaintFg();
                break;
            case FontPosition:
                baseViewFg = new FontFg();
                break;
            case BackgroundPosition:
                baseViewFg = new BackgroundFg();
                break;
            case ToolPosition:
                baseViewFg = new ToolFg();
                break;
            default:
                break;

        }
        if (baseViewFg != null) {
            baseViewFg.setViewPresenter(mViewPresenter);
        }
        return baseViewFg;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
