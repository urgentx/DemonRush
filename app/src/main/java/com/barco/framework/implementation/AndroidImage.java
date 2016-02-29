package com.barco.framework.implementation;

import android.graphics.Bitmap;

import com.barco.framework.Graphics;
import com.barco.framework.Graphics.ImageFormat;
import com.barco.framework.Image;

/**
 * Created by Barco on 27-Dec-15.
 */
public class AndroidImage implements Image {

    Bitmap bitmap;
    ImageFormat format;

    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
}
