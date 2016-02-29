package com.barco.demonrush;

import android.graphics.Rect;
import android.util.Log;

import com.barco.framework.Image;

/**
 * Categorises all constructed tiles by type. Can be visualised on the numpad, i.e. type 8 is collision
 * surface UP, type 2 is surface DOWN, type 7 is both LEFT and UP, etc.
 * Type 0 means empty space. Checks collisions with Demon object from GameScreen only.
 */
public class Tile {

    private int tileX, tileY, speedX, type;
    public Image tileImage;
    private Demon demon = GameScreen.getDemon();
    private Background bg = GameScreen.getBg1();
    private Rect r;

    public Tile(int x, int y, int typeInt) {
        tileX = x * 40;
        tileY = y * 40;
        type = typeInt;

        r = new Rect();

        if (type < 10 && type > 0) {    //Assign image to tile
            tileImage = Assets.tile;
        } else if (type == 14) {
            tileImage = Assets.portal;
            tileY -= 48;
        } else {
            type = 0;
        }
    }

    public void update() {

        speedX = bg.getSpeedX() * 5; //speed dependant on background speed
        tileX += speedX;
        r.set(tileX, tileY, tileX + 40, tileY + 40); //instance bounding rectangle for collisions
    }

    public synchronized void collisionCheck() {

        if (Rect.intersects(r, demon.yellowRed)) { //only check tiles around Demon
            if (Rect.intersects(r, demon.rectRight) && type == 14) { //end of level portal check
                GameScreen.won = true;
            }
            if (Rect.intersects(r, demon.rect) && (type == 8 || type == 9 || type == 7)) { //floor collisions
                if (demon.getCenterY() > tileY + 20) {
                    demon.setCenterY(tileY + 85);
                    demon.setSpeedY(0);
                    demon.setJumped(false);
                    return;
                }
            /*if(demon.getSpeedY() < -5){       //enables demon to cling to ceilings
                demon.setSpeedY(-10);
                demon.setCenterY(tileY + 63);
                demon.setJumped(false);
                return;

            } else*/
                else {
                    demon.setSpeedY(0);
                    demon.setCenterY(tileY - 63);
                    demon.setJumped(false);
                    return;
                }
            } else if (Rect.intersects(r, demon.rect) && (type == 2 || type == 1 || type == 3)) { //ceiling collisions
                demon.setSpeedY(0);
                demon.setCenterY(tileY + 100);
                return;
            } else if (type != 5 && type != 2 && type != 0 && type != 8) {     //horizontal collisions
                if (Rect.intersects(r, demon.rectRight)) {
                    demon.setCenterX(tileX - 40);
                    return;
                    //demon.setSpeedX(0);
                } else if (Rect.intersects(demon.rectLeft, r)) {
                    demon.setCenterX(tileX + 110);
                    return;
                }

            }
        }
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getType() {
        return type;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public Background getBg() {
        return bg;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Rect getR() {
        return r;
    }

    public void setR(Rect r) {
        this.r = r;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }

}