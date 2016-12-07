package com.emotion.emotiontech.View.EmotionView;

import android.view.View;

import com.emotion.emotiontech.R;

import butterknife.OnClick;
import felix.lib.Base.BindLayout;
import felix.lib.Base.Util.ToastUtil;
import felix.lib.Base.Util.ViewUtil;

/**
 * Created by felix on 2016/12/5.
 */
@BindLayout(R.layout.fg_font)
public class FontFg extends BaseViewFg
{

    @OnClick({R.id.tv_add_font, R.id.tv_font})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_font:
                mViewPresenter.addText("lalal");
                break;
            case R.id.tv_font:
                String text = mViewPresenter.getText();
                ToastUtil.showToast(text);

                if (text != null)
                    ViewUtil.showInputDialog(mContext, text, (dialog, which, str) -> mViewPresenter.setText(str));
                break;
        }
    }
}
