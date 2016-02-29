package com.barco.framework;

import android.graphics.Paint;
import android.graphics.Rect;

public interface Graphics {
    public static enum ImageFormat {
        ARGB8888, ARGB4444, RGB565;
    }

    public Image newImage(String filename, ImageFormat format);

    public void clearScreen(int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawCircle(float cx, float cy, float radius, int color);

    public void drawRect(Rect rect, int color);

    public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

    public void drawImage(Image image, int x, int y);

    void drawString(String text, int x, int y, Paint paint);

    public void screenShake(float dx, float dy);

    public int getWidth();

    public int getHeight();

    public void drawARGB(int i, int j, int k, int l);
}
