package com.emotion.emotiontech.weiget.LayerView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by felix on 2016/12/3.
 */

public class BitmapLayerImp extends LayerI
{
    private Bitmap mBitmap;

    public BitmapLayerImp(Bitmap bitmap) {
        if (bitmap == null) {
            mBitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        } else {
            mBitmap = bitmap;
        }
        initSize();
    }

    @Override
    protected int getInitHeight() {
        return mBitmap.getHeight();
    }

    @Override
    protected int getInitWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.save();
        //canvas.
        //canvas.translate(getCenterX(),getCenterY());
        //canvas.concat(mMatrix);
       // canvas.translate(getCenterX(),getCenterY());
       // canvas.rotate(mDegress,getCenterX(),getCenterY());
        canvas.drawBitmap(mBitmap,- mLocation.width/2,  -mLocation.height/2, paint);

        canvas.restore();

        //canvas.drawPoint(getCenterX(),getCenterY(),paint);
    }

}
