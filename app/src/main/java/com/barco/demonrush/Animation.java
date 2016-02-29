package com.barco.demonrush;

import com.barco.framework.Image;

import java.util.ArrayList;

/**
 * Stores a sequence of Images/Duration pairs, and returns current image based on elapsed time passed
 * to update().
 */

public class Animation {

    private ArrayList frames;
    private int currentFrame;
    private long animTime;
    private long totalDuration;

    public Animation() {
        frames = new ArrayList();
        totalDuration = 0;

        synchronized (this) {
            animTime = 0;
            currentFrame = 0;
        }
    }

    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));

    }

    public synchronized void update(long elapsedTime) {
        if (frames.size() >= 1) {
            animTime += elapsedTime;
            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currentFrame = 0;
            }

            while (animTime > getFrame(currentFrame).endTime) { //while loop is important here,
                currentFrame++;                                 //couldn't skip a frame with
            }                                                   //an "if" statement.
        }
    }

    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currentFrame).image;
        }
    }

    private AnimFrame getFrame(int i) {
        return (AnimFrame) frames.get(i);
    }

    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

}