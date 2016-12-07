package felix.lib.Base.Util;

import android.content.Context;

/**
 * Created by felix on 10/18/2016.
 */

public class DentisityUtil
{
    private static Context sContext;
    private static float sDensity=-1;
    private static float sScaledDensity=-1;

    public static void init(Context context) {
        if (sContext != null) {
            return;
        }
        sContext = context;
        sDensity = context.getResources().getDisplayMetrics().density;
        sScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 将dp值转换为px值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        if (sDensity == -1) {
            return (int) (pxValue / 1.5 + 0.5f);
        }
        return (int) (pxValue / sDensity + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dp2px(float dipValue) {
        if (sDensity == -1) {
            return (int) (dipValue * 1.5 + 0.5f);
        }
        return (int) (dipValue * sDensity + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        if (sDensity == -1) {
            return (int) (pxValue / 1.5 + 0.5f);
        }
        return (int) (pxValue / sScaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        if (sDensity == -1) {
            return (int) (spValue * 1.5 + 0.5f);
        }
        return (int) (spValue * sScaledDensity + 0.5f);
    }
}
