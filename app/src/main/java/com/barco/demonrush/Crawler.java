package com.barco.demonrush;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Subclass of Enemy. Ignores follow method, as this enemy type just patrols. Only moves horizontally,
 * and is stopped and turned around by sideTiles defined in GameScreen.
 */
public class Crawler extends Enemy {


    public Crawler(int centerX, int centerY) {
        setCenterX(centerX);
        setCenterY(centerY);
        setMoveSpeed(-1);
    }

    @Override
    public void update() {
        setCenterX(getCenterX() + getSpeedX());
        setCenterY(getCenterY() + getSpeedY());
        setSpeedX(getBg().getSpeedX() * 5 + getMoveSpeed());

        r.set(getCenterX() - 25, getCenterY() - 35, getCenterX() + 20, getCenterY() + 35);
        for (Tile t : GameScreen.getSideTiles()) { //check only side tiles for turnaround
            if (Rect.intersects(r, t.getR())) {
                if (t.getType() == 4) {
                    setMoveSpeed(-1);
                } else if (t.getType() == 6) {
                    setMoveSpeed(1);
                }
            }
        }
        if (Rect.intersects(r, Demon.yellowRed)) { //check for collision with Demon
            if (Rect.intersects(r, Demon.rect)) {
                checkCollision();
            }

        }
    }

    @Override
    public void checkCollision() {
        GameScreen.shake = true;
        getDemon().setHealth(getDemon().getHealth() - 30);
        setCenterX(-100);
    }
}
