package com.emotion.emotiontech.weiget.LayerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import felix.lib.Base.Util.DentisityUtil;

/**
 * Created by felix on 2016/12/6.
 */

public class TextLayerImp extends LayerI
{
    private String mText = "";
    private Paint mPaint;

    public TextLayerImp(String str) {
        mText = str == null ? "" : str;
        mPaint = new Paint();
        mPaint.setTextSize(DentisityUtil.dp2px(22));
        initSize();
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        initSize();
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint) {
        canvas.drawText(mText,- mLocation.width / 2, mLocation.height / 2, mPaint);
    }

    @Override
    protected int getInitWidth() {
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), rect);
        mLocation.setSize(rect.width(), rect.height());
        return rect.width();
    }

    @Override
    protected int getInitHeight() {
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), rect);
        mLocation.setSize(rect.width(), rect.height());
        return rect.height();
    }
}
