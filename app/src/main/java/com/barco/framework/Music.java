package com.barco.framework;

/**
 * Created by Barco on 23-Dec-15.
 */
public interface Music {

    public void play();

    public void stop();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose(); //removes audio file

    void seekBegin();

}
