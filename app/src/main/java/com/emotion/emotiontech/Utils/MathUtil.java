package com.emotion.emotiontech.Utils;

import android.graphics.Matrix;
import android.util.Log;

import felix.lib.Base.Util.DentisityUtil;

/**
 * Created by felix on 2016/12/5.
 */

public class MathUtil
{
    private static final String TAG = MathUtil.class.getSimpleName();

    public static double getDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double d2 = dx * dx + dy * dy;
        return Math.sqrt(d2);
    }

    public static float getDistance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float d2 = dx * dx + dy * dy;
        return ((float) Math.sqrt(d2));
    }

    public static double getZoom(double cx, double cy, double x1, double y1, double x2, double y2) {
        double d1 = getDistance(cx, cy, x1, y1);
        double d2 = getDistance(cx, cy, x2, y2);
        return d2 / d1;
    }

    public static float getZoom(float cx, float cy, float x1, float y1, float x2, float y2) {
        float d1 = getDistance(cx, cy, x1, y1);
        float d2 = getDistance(cx, cy, x2, y2);
        return d2 / d1;
    }

    public static Matrix getRoateMatrix(float cx, float cy, float x, float y) {
        float dx = x - cx;
        float dy = y - cy;
        float d = getDistance(cx, cy, x, y);
        float sinA = dy / d;
        float cosA = dx / d;
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{cosA, -sinA, 0, sinA, cosA, 0, 0, 0, 1});
        return matrix;
    }

    public static Matrix    getZoomMatrix(float cx, float cy, float x1, float y1, float x2, float y2) {
        float d1 = getDistance(cx, cy, x1, y1);
        float d2 = getDistance(cx, cy, x2, y2);
        float zoom = d2 / d1;
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);
        return matrix;
    }

    public static Matrix getMinZoomMMatrix(Matrix matrix) {
        return getMinZoomMMatrix(matrix, -1, -1);
    }

    public static Matrix getMinZoomMMatrix(Matrix matrix, float width, float height) {
        Log.i(TAG, "getMinZoomMMatrix: " + matrix.toShortString());
        float minValue = DentisityUtil.dp2px(66);

        float minScale = width > height ? minValue / height : minValue / width;
        if (minScale < 0) {
            minScale = 0.5f;
        }
        if (minScale > 1) {
            minScale = 1;
        }
        Log.i(TAG, "getMinZoomMMatrix: minscale="+minScale);
        minScale=0.5f;
        float[] values = new float[9];
        matrix.getValues(values);
        if (values[0] < minScale && values[0] > -minScale) {
            if (values[0] > 0) {
                values[0] = minScale;
            } else {
                values[0] = -minScale;
            }
        }
        if (values[4] < minScale && values[4] > -minScale) {
            if (values[4] > 0) {
                values[4] = minScale;
            } else {
                values[4] = -minScale;
            }
        }
        Matrix matrix1 = new Matrix();
        matrix1.setValues(values);
        return matrix1;
    }

}
