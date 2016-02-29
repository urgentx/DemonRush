package com.barco.framework;

import com.barco.framework.Graphics.ImageFormat;

/**
 * Created by Barco on 23-Dec-15.
 */
public interface Image {

    public int getWidth();

    public int getHeight();

    public ImageFormat getFormat();

    public void dispose();
}


