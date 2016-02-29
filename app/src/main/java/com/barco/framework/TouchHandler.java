package com.barco.framework;

import android.view.View;

import com.barco.framework.Input;

import java.util.List;

/**
 * Created by Barco on 27-Dec-15.
 */
public interface TouchHandler extends View.OnTouchListener {

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();
}
