package com.emotion.emotiontech.View;

import android.graphics.Bitmap;

/**
 * Created by felix on 2016/12/5.
 */

public interface ViewPresenter
{
    void addBitmap(Bitmap bitmap);

    void addText(String str);

    String getText();

    boolean setText(String str);
}
