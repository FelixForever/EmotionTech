package com.emotion.emotiontech.weiget.LayerView;

import android.os.Parcel;
import android.os.Parcelable;

import felix.lib.Base.Util.DentisityUtil;

/**
 * Created by felix on 2016/12/3.
 */

public class Location implements Parcelable
{
    protected final static int DEFAULT_PADDING = DentisityUtil.dp2px(5);
    protected final static int DEFAULT_X = DentisityUtil.dp2px(100);
    protected final static int DEFAULT_Y = DentisityUtil.dp2px(100);
    public float x = DEFAULT_X;
    public float y = DEFAULT_Y;
    public float width = -1;
    public float height = -1;

    public Location() {

    }

    public void setPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Location(float px, float py) {
        x = px;
        y = py;
    }

    public void setSize(float w, float h) {
        width = w;
        height = h;
    }

    public Location(float px, float py, float w, float h) {
        x = px;
        y = py;
        width = w;
        height = h;
    }

    public Location(Location location) {
        this.x = location.x;
        this.y = location.y;
        this.width = location.width;
        this.height = location.height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            //判断顶点位置是否相等
            if (((Location) obj).x != this.x || ((Location) obj).y != this.y) {
                return false;
            }
            //判断大小是否相等
            if (((Location) obj).width != this.width || ((Location) obj).height != this.height) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得顶部位置信息
     *
     * @return
     */
    public float getTop() {
        return y - DEFAULT_PADDING;
    }

    /**
     * 获得左边距距离
     *
     * @return
     */
    public float getLeft() {
        return x - DEFAULT_PADDING;
    }

    /**
     * 获得右边距距离（相对于左边）
     *
     * @return
     */
    public float getRight() {
        return x + width + DEFAULT_PADDING;
    }

    /**
     * 获得底部距离（相对于顶部）
     *
     * @return
     */
    public float getBottom() {
        return y + height + DEFAULT_PADDING;
    }

    public boolean contain(Location location) {
        if (location.x > this.x && location.y > this.y && location.getRight() < this.getRight() && location.getBottom() < this.getBottom()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final String str = "Location(" + x + ", " + y + ")" + " [width=" + width + ",height=" + height + "]";
        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.x);
        dest.writeFloat(this.y);
        dest.writeFloat(this.width);
        dest.writeFloat(this.height);
    }

    protected Location(Parcel in) {
        this.x = in.readFloat();
        this.y = in.readFloat();
        this.width = in.readFloat();
        this.height = in.readFloat();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>()
    {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
