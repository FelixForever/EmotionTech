package com.emotion.emotiontech.View.EmotionView;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.emotion.emotiontech.R;

import butterknife.OnClick;
import felix.lib.Base.BindLayout;
import felix.lib.Base.Util.ViewUtil;

/**
 * Created by felix on 2016/12/5.
 */
@BindLayout(R.layout.fg_material)
public class MaterialFg extends BaseViewFg
{

    @OnClick({R.id.tv_material, R.id.tv_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_material:
                materialClick();
                break;
            case R.id.tv_history:
                break;
        }
    }

    public static int count = 0;

    private void materialClick() {
        int[] ids = new int[]{R.mipmap.ic_launcher, R.drawable.sucai, R.drawable.ziti, R.drawable.qianbi, R.drawable.gongju};
        int index = count++ % ids.length;
        mViewPresenter.addBitmap(((BitmapDrawable) ViewUtil.getDrawableById(ids[index])).getBitmap());
    }
}
