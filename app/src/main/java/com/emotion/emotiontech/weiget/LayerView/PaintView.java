package com.emotion.emotiontech.weiget.LayerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import felix.lib.Base.Util.ViewUtil;

/**
 * Created by felix on 2016/12/3.
 */

public class PaintView extends View
{
    private static final String TAG = PaintView.class.getSimpleName();
    private List<LayerI> mLayerIList;
    private LayerI mOperateLayer = null;
    private Paint mPaint;
    private TouchLocEnum mTouchLocEnum = TouchLocEnum.BACKGROUND;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2 * context.getResources().getDisplayMetrics().density);
        mLayerIList = new ArrayList<>();
    }

    public String getCurText() {
        if (mOperateLayer instanceof TextLayerImp) {
            return ((TextLayerImp) mOperateLayer).getText();
        }
        return null;
    }

    public boolean setText(String str) {
        if (mOperateLayer instanceof TextLayerImp) {
            ((TextLayerImp) mOperateLayer).setText(str);
            return true;
        }
        return false;
    }

    public void addBitmap(Bitmap bitmap) {
        mLayerIList.add(new BitmapLayerImp(bitmap));
        invalidate();
    }

    public void addDrawable(Drawable drawable) {
        final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        addBitmap(bitmap);
    }

    public void addDrawable(int id) {
        addDrawable(ViewUtil.getDrawableById(id));
    }

    public void addText(String str) {
        mLayerIList.add(new TextLayerImp(str));
        invalidate();
    }

    public void deleteAll() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mLayerIList.forEach(LayerI::delete);
        } else {
            for (int i = 0; i < mLayerIList.size(); i++) {
                mLayerIList.get(i).delete();
            }
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (LayerI layerI : mLayerIList) {
            layerI.draw(canvas, mPaint);
        }
    }

    protected float[] lastX = new float[2];
    protected float[] lastY = new float[2];
//    ;
//    protected int lastX = -1;
//    protected int lastY = -1;

    private boolean changeSelected = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // Log.i(TAG, "onTouchEvent: " + event.getAction());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
//                lastX[0] = x;
//                lastY[0] = y;
                lastX[1] = event.getX(1);
                lastY[1] = event.getY(1);
                break;
            case MotionEvent.ACTION_DOWN:
                changeSelected = true;
                lastX[0] = x;
                lastY[0] = y;
//                lastX[1] = -1;
//                lastY[1] = -1;

                mTouchLocEnum = mOperateLayer == null ? TouchLocEnum.BACKGROUND : mOperateLayer.getTouchLoc(x, y);
                Log.i(TAG, "onTouchEvent: " + mTouchLocEnum.name());

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                event.getActionIndex();
                float lastx1 = lastX[0];
                float lasty1 = lastY[0];
                float lastx2 = lastX[1];
                float lasty2 = lastY[1];
                float x1 = event.getX(0);
                float y1 = event.getY(0);
                float x2 = -1;
                float y2 = -1;

                if (lastX[0] == -1 || lastY[0] == -1) {
                    return false;
                }
                final float dx = Math.abs(x - lastx1);
                final float dy = Math.abs(y - lasty1);
                if (dx > 0 && dy > 0) {
                    changeSelected = false;
                }
                if (mOperateLayer == null && mTouchLocEnum != TouchLocEnum.BACKGROUND) {
                    return false;
                }
                if (event.getPointerCount() >= 2) {
                    x2 = ((int) event.getX(1));
                    y2 = ((int) event.getY(1));
                    if (mOperateLayer != null) {
                        mOperateLayer.rotate(lastx1, lasty1, x1, y1);
                        if (lastx2 != -1 && lasty2 != -1)
                            mOperateLayer.rotate(lastx2, lasty2, x2, y2);
                    }
                    invalidate();
                } else {
                    switch (mTouchLocEnum) {
                        case RIGHT_TOP:
                        case RIGHT:
                            mOperateLayer.xScale(lastX[0], x);
                            invalidate();
                            break;
                        case BOTTOM:
                            mOperateLayer.yScale(lastY[0], y);
                            invalidate();
                            break;

                        case RIGHT_BOTTOM:
                            if (mOperateLayer != null) {
                                mOperateLayer.rotate(lastX[0], lastY[0], x, y);

                                invalidate();
                            }
//                        mOperateLayer.rorate(x, y);
//                        invalidate();
                            break;
                        case LEFT_TOP:
//                            mOperateLayer.delete();
//                            invalidate();
                            break;
                        case LEFT:
                        case TOP:

                        case LEFT_BOTTOM:
                        case IN:
                            mOperateLayer.move(lastX[0], lastY[0], x, y);
                            invalidate();
                            break;
                        case OUT:
                            break;
                        case BACKGROUND:
                            break;
                        default:
                            break;
                    }
                }

                //event.getx
                final float cx = mOperateLayer == null ? x1 + 0.0001f : mOperateLayer.getCenterX();
                final float cy = mOperateLayer == null ? y1 + 0.0001f : mOperateLayer.getCenterY();
                lastX[0] = x1 == cx ? x1 + 0.0001f : x1;
                lastY[0] = y1 == cy ? y1 + 0.0001f : y1;
                lastX[1] = x2 == cx ? x2 + 0.0001f : x2;
                lastY[1] = y2 == cy ? y2 + 0.0001f : y2;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                lastX[1] = -1;
                lastY[1] = -1;
                break;
            case MotionEvent.ACTION_UP:

                if (changeSelected) {
                    if (mOperateLayer != null) {
                        if (mTouchLocEnum == TouchLocEnum.LEFT_TOP) {
                            mOperateLayer.delete();
                            invalidate();
                            break;
                        }
                        mOperateLayer.setSelected(false);
                    }

                    mOperateLayer = null;
                    final int size = mLayerIList.size();
                    for (int i = size - 1; i >= 0; i--) {
                        final LayerI layer = mLayerIList.get(i);
                        if (layer.contain(x, y)) {
                            mOperateLayer = layer;
                            mOperateLayer.setSelected(true);
                            //mOperateLayer.setDownLoc(x, y);
                            break;
                        }
                    }
                }

                invalidate();
                lastX[0] = -1;
                lastY[0] = -1;
                lastX[1] = -1;
                lastY[1] = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                lastX[0] = -1;
                lastY[0] = -1;
                lastX[1] = -1;
                lastY[1] = -1;
                break;
            default:
                break;

        }   
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
