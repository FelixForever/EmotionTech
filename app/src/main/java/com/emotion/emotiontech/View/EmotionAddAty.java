package com.emotion.emotiontech.View;

import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.emotion.emotiontech.Adp.ViewPagerAdp;
import com.emotion.emotiontech.R;
import com.emotion.emotiontech.weiget.LayerView.PaintView;

import butterknife.Bind;
import butterknife.OnClick;
import felix.lib.Base.Aty.BaseAty;
import felix.lib.Base.BindLayout;

/**
 * Created by felix on 2016/12/3.
 */
@BindLayout(R.layout.aty_emotion_add)
public class EmotionAddAty extends BaseAty implements ViewPresenter
{
    @Bind(R.id.pv_emotion)
    PaintView mPvEmotion;
    @Bind(R.id.tab_main)
    TabLayout mTabMain;
    @Bind(R.id.vp_main)
    ViewPager mVpMain;

    @Override
    protected void initData() {
        super.initData();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        mVpMain.setAdapter(new ViewPagerAdp(mContext, getSupportFragmentManager(), this));
        mTabMain.setupWithViewPager(mVpMain);
        int[] drawableIds = new int[]{
                R.drawable.sucai, R.drawable.qianbi, R.drawable.ziti, R.drawable.tupian, R.drawable.gongju
        };
        final int size = mTabMain.getTabCount();
        for (int i = 0; i < size; i++) {
            mTabMain.getTabAt(i).setIcon(drawableIds[i]);
        }

    }

    @Override
    public void addBitmap(Bitmap bitmap) {
        mPvEmotion.addBitmap(bitmap);
    }

    @Override
    public void addText(String str) {
        mPvEmotion.addText(str);
    }

    @Override
    public String getText() {
        return mPvEmotion.getCurText();
    }

    @Override
    public boolean setText(String str) {
        return mPvEmotion.setText(str);
    }

    @Override
    protected void onEditClick(View view) {
        super.onEditClick(view);

    }

    @OnClick({R.id.iv_previous, R.id.iv_del, R.id.id_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_previous:
                break;
            case R.id.iv_del:
                mPvEmotion.deleteAll();
                break;
            case R.id.id_reset:
                break;
        }
    }
}
