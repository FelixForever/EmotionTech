package com.emotion.emotiontech.weiget.LayerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.emotion.emotiontech.R;
import com.emotion.emotiontech.Utils.MathUtil;

import felix.lib.Base.Util.DentisityUtil;
import felix.lib.Base.Util.ViewUtil;

/**
 * Created by felix on 2016/12/3.
 */

public abstract class LayerI
{
    private boolean mIsDelete = false;
    public static Drawable sCloseDrawable;
    public static Drawable sScaleDrawable;
    public static Drawable sRotateDrawable;
    protected final String TAG = getClass().getSimpleName();

    protected final static int DEFAULT_WIDTH = DentisityUtil.dp2px(100);
    protected final static int DEFAULT_HEIGHT = DentisityUtil.dp2px(100);
    protected final static int DEFAULT_ERROR = DentisityUtil.dp2px(12.5f);
    protected final static int DEFAULT_PADDING = DentisityUtil.dp2px(5);

    protected float mDegress = 0;
    protected float mDownDegress = 0;
    protected Location mLocation;
    Matrix mCenterMatrix;
    Matrix mTranslateMatrix;
    //Matrix mInverseMatrix;
    Matrix mRotateMatrix;
    Matrix mZoomMatrix;

    private boolean mIsSelected = false;

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public LayerI() {
        sCloseDrawable = ViewUtil.getDrawableById(R.drawable.cha);
        sScaleDrawable = ViewUtil.getDrawableById(R.drawable.zuoyou);
        sRotateDrawable = ViewUtil.getDrawableById(R.drawable.xuanzhuan);

        mLocation = new Location();
        mCenterMatrix = new Matrix();
        mTranslateMatrix = new Matrix();
        mRotateMatrix = new Matrix();
        mZoomMatrix = new Matrix();
    }

    protected void initSize() {
        mLocation.setSize(getInitWidth(), getInitHeight());
        mCenterMatrix.setTranslate(getCenterX(), getCenterY());
    }

    public void delete() {
        mIsDelete = true;
    }

    public void restore() {
        mIsDelete = false;
    }

    /**
     * 旋转放大
     *
     * @param lastX
     * @param lastY
     * @param X
     * @param Y
     */
    public void rotate(float lastX, float lastY, float X, float Y) {
        Matrix matrix1 = MathUtil.getRoateMatrix(getCenterX(), getCenterY(), lastX, lastY);
        Matrix matrix2 = MathUtil.getRoateMatrix(getCenterX(), getCenterY(), X, Y);
        matrix1.invert(matrix1);
        mRotateMatrix.postConcat(matrix2);
        mRotateMatrix.postConcat(matrix1);
        mZoomMatrix.postConcat(MathUtil.getZoomMatrix(getCenterX(), getCenterY(), lastX, lastY, X, Y));
    }

    public void yScale(float lastY, float y) {
        scale(0, lastY, 0, y, false, true);
    }

    public void xScale(float lastX, float x) {
        scale(lastX, 0, x, 0, true, false);
    }

    public void scale(float lastX, float lastY, float x, float y, boolean isX, boolean isY) {
        //boolean isReset
        if (lastX == getCenterX()) {
            lastX += 1;
        }
        if (lastY == getCenterY()) {
            lastY += 1;
        }
        if (x == getCenterX()) {
            x += 1;
        }
        if (y == getCenterY()) {
            y += 1;
        }
        final float xscale = isX ? (x - getCenterX()) / (1.0f * (lastX - getCenterX())) : 1;
        final float yscale = isY ? (y - getCenterY()) / (1.0f * (lastY - getCenterY())) : 1;
        Log.i(TAG, "scale: xscale=" + xscale + " yscale=" + yscale);
        Matrix matrix = new Matrix();
        matrix.setScale(xscale, yscale);
        mZoomMatrix.postConcat(matrix);
        Log.i(TAG, "scale: xscale: " + mZoomMatrix.toShortString());
//        final float xscale = isX ? ((x == getCenterX() ? x + 1 : x) - getCenterX()) * 2.0f / mLocation.width : 1;
//        final float yscale = isY ? ((y == getCenterY() ? y + 1 : y) - getCenterY()) * 2.0f / mLocation.height : 1;
//        mZoomMatrix.setScale(xscale,yscale);
    }

    public void move(float lastX, float lastY, float x, float y) {
//mLocation.setPoint(mLocation.x+x-lastX,mLocation.y+y-lastY);
        final float dx = x - lastX;
        final float dy = y - lastY;
        Log.i(TAG, "move: " + "dx=" + dx + " dy=" + dy);
        mTranslateMatrix.postTranslate(dx, dy);
    }

    final public void draw(Canvas canvas, Paint paint) {

        if (mIsDelete) {
            return;
        }
        canvas.save();
        canvas.concat(mCenterMatrix);
        canvas.concat(mTranslateMatrix);
        canvas.concat(mRotateMatrix);
        canvas.save();
        canvas.concat(MathUtil.getMinZoomMMatrix(mZoomMatrix, getWidth(), getHeight()));
        onDraw(canvas, paint);
        canvas.restore();
        if (mIsSelected) {
            int width = ((int) (mLocation.width / 2));
            int height = ((int) (mLocation.height / 2));
            int paddingWidth = DEFAULT_PADDING;
            int paddingHeight = DEFAULT_PADDING;
            float[] point = new float[]{width, height, paddingWidth, paddingHeight};
            Matrix matrix = new Matrix(MathUtil.getMinZoomMMatrix(mZoomMatrix, getWidth(), getHeight()));
            matrix.mapPoints(point);
            width = ((int) point[0]);
            height = ((int) point[1]);
            paddingWidth = ((int) point[2]);
            paddingHeight = ((int) point[3]);

            paint.setColor(Color.parseColor("#eeeeee"));
            canvas.drawRect(-width - paddingWidth,
                    -height - paddingHeight,
                    width + paddingWidth,
                    height + paddingHeight, paint);

            sScaleDrawable.setBounds(width + paddingWidth - sScaleDrawable.getIntrinsicWidth() / 2,
                    -sScaleDrawable.getIntrinsicHeight() / 2,
                    width + paddingWidth + sScaleDrawable.getIntrinsicWidth() / 2,
                    +sScaleDrawable.getIntrinsicHeight() / 2
            );
            sScaleDrawable.draw(canvas);
            sScaleDrawable.setBounds(-sScaleDrawable.getIntrinsicWidth() / 2,
                    height + paddingHeight - sScaleDrawable.getIntrinsicHeight() / 2,
                    sScaleDrawable.getIntrinsicWidth() / 2,
                    height + paddingHeight + sScaleDrawable.getIntrinsicHeight() / 2
            );
            canvas.save();
            Matrix matrix1 = new Matrix();
            matrix1.setTranslate(0, -height - paddingHeight);
            matrix1.postRotate(90);
            matrix1.postTranslate(0, height + paddingHeight);
            canvas.concat(matrix1);

            sScaleDrawable.draw(canvas);

            canvas.restore();

            //关闭按钮和缩放按钮
            sCloseDrawable.setBounds(-width - paddingWidth - sCloseDrawable.getIntrinsicWidth() / 2,
                    -height - paddingHeight - sCloseDrawable.getIntrinsicHeight() / 2,
                    -width - paddingWidth + sCloseDrawable.getIntrinsicWidth() / 2,
                    -height - paddingHeight + sCloseDrawable.getIntrinsicHeight() / 2
            );
            sCloseDrawable.draw(canvas);
            sRotateDrawable.setBounds(width + paddingWidth - sRotateDrawable.getIntrinsicWidth() / 2,
                    height + paddingHeight - sRotateDrawable.getIntrinsicHeight() / 2,
                    width + paddingWidth + sRotateDrawable.getIntrinsicWidth() / 2,
                    height + paddingHeight + sRotateDrawable.getIntrinsicHeight() / 2
            );
            sRotateDrawable.draw(canvas);
        }

        canvas.restore();
        paint.setColor(Color.GREEN);
        canvas.drawRect(mLocation.getLeft(), mLocation.getTop(), mLocation.getRight(), mLocation.getBottom(), paint);
        paint.setColor(Color.RED);
        canvas.drawRect(mLocation.getLeft() - DEFAULT_ERROR, mLocation.getTop() - DEFAULT_ERROR, mLocation.getRight() + DEFAULT_ERROR, mLocation.getBottom() + DEFAULT_ERROR, paint);

        canvas.drawRect(mLocation.getLeft() + DEFAULT_ERROR, mLocation.getTop() + DEFAULT_ERROR, mLocation.getRight() - DEFAULT_ERROR, mLocation.getBottom() - DEFAULT_ERROR, paint);

        paint.setColor(Color.RED);
        canvas.drawPoint(testX, testY, paint);
        paint.setColor(Color.BLACK);
    }

    public TouchLocEnum getTouchLoc(float px, float py) {
        float[] point = getMapPoint(px, py);
        float x = point[0];
        float y = point[1];
        Log.i(TAG, "getTouchLoc: x in range " + xInRange(x));
        Log.i(TAG, "getTouchLoc: y in range " + yInRange(y));
        if (xInRange(x) && yInRange(y)) {
            boolean isLeft = inErrorRange(x, mLocation.getLeft());
            // Log.i(TAG, "getTouchLoc: isleft=" + isLeft);
            boolean isTop = inErrorRange(y, mLocation.getTop());
            boolean isRight = inErrorRange(x, mLocation.getRight());
            boolean isBottom = inErrorRange(y, mLocation.getBottom());
            if (isLeft) {
                if (isTop) {
                    return TouchLocEnum.LEFT_TOP;
                } else if (isBottom) {
                    return TouchLocEnum.LEFT_BOTTOM;
                }
                return TouchLocEnum.LEFT;
            } else if (isRight) {
                if (isTop) {
                    return TouchLocEnum.RIGHT_TOP;
                } else if (isBottom) {
                    return TouchLocEnum.RIGHT_BOTTOM;
                }
                return TouchLocEnum.RIGHT;
            } else {
                if (isTop) {
                    return TouchLocEnum.TOP;
                } else if (isBottom) {
                    return TouchLocEnum.BOTTOM;
                }
                return TouchLocEnum.IN;
            }
        } else {

            return TouchLocEnum.OUT;
        }
    }

    private float testX = -1;
    private float testY = -1;

    private float[] getMapPoint(float x, float y) {
        //Log.i(TAG, "getMapPoint: x=" + x + "   y=" + y);

        float[] point = new float[]{x, y};

        //mInverseMatrix.mapPoints(point);
        Matrix center = new Matrix(mCenterMatrix);
        center.invert(center);
        Matrix translate = new Matrix(mTranslateMatrix);
        translate.invert(translate);
        Matrix zoom = new Matrix(MathUtil.getMinZoomMMatrix(mZoomMatrix, getWidth(), getHeight()));
        zoom.invert(zoom);
        Matrix rotate = new Matrix(mRotateMatrix);
        rotate.invert(rotate);

        center.mapPoints(point);
        translate.mapPoints(point);
        zoom.mapPoints(point);
        rotate.mapPoints(point);
        // mTranslateMatrix.mapPoints(point);
        mCenterMatrix.mapPoints(point);

        testX = point[0];
        testY = point[1];
        //  Log.i(TAG, "getMapPoint: " + new Gson().toJson(point));
        return new float[]{point[0], point[1]};
    }

    protected abstract void onDraw(Canvas canvas, Paint paint);

    public float getX() {
        return mLocation.x;
    }

    public float getY() {
        return mLocation.y;
    }

    public float getWidth() {
        return mLocation.width;
    }

    public float getHeight() {
        return mLocation.height;
    }

    protected abstract int getInitHeight();

    protected abstract int getInitWidth();

    public float getCenterX() {
        return mLocation.x + mLocation.width / 2;
    }

    public float getCenterY() {
        return mLocation.y + mLocation.height / 2;
    }

    public boolean contain(float px, float py) {
        float[] point = getMapPoint(px, py);
        float x = point[0];
        float y = point[1];
        return xInRange(x) && yInRange(y);
    }

    private boolean xInRange(float x) {
        return inRange(x, mLocation.getLeft(), mLocation.getRight());
    }

    private boolean yInRange(float y) {
        return inRange(y, mLocation.getTop(), mLocation.getBottom());
    }

    private boolean inRange(float x, float min, float max) {
        if (x >= min && x <= max) {
            return true;
        }
        if (x > max && inError(x - max)) {
            return true;
        }
        if (x < min && inError(min - x)) {
            return true;
        }
        float dx = Math.max(x - min, max - x);

        return inError(dx);
    }

    private boolean inErrorRange(float x, float value) {
        return inError(value - x);
    }

    private boolean inError(float dx) {
        if (dx == 0)
            return true;
        dx = Math.abs(dx);
        float[] values = new float[9];
        mZoomMatrix.getValues(values);
        float value1 = Math.max(Math.abs(values[0]), 1);
        float value2 = Math.max(Math.abs(values[4]), 1);
        float value = Math.max(value1, value2);

        return dx < DEFAULT_ERROR * 1.0f / value;
    }

}
