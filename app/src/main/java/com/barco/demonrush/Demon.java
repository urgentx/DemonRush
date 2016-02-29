package com.barco.demonrush;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Main character class. Handles movement and shooting, but not collisions.
 */

public class Demon {

    final int JUMPSPEED = -27;
    final int MOVESPEED = 7;

    private static int health = 200;

    private int centerX = 100;
    private int centerY = 180;
    private boolean jumped = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean ducked = false;
    private boolean readyToFire = true;

    private int speedX = 0;
    private int speedY = 1;

    Background bg1;
    Background bg2;

    public static Rect rect = new Rect(0, 0, 0, 0); //bounding rectangle for VERTICAL collision
    public static Rect rectRight = new Rect(0, 0, 0, 0); //bounding rectangle for left HORIZONTAL collision
    public static Rect rectLeft = new Rect(0, 0, 0, 0);  //bounding rectangle for right HORIZONTAL collision
    public static Rect yellowRed = new Rect(0, 0, 0, 0); //tiles to take into consideration for collision

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();


    public void update() {
        // moves character or scrolls background
        bg1 = GameScreen.getBg1();
        bg2 = GameScreen.getBg2();
        if (speedX < 0) {
            centerX += speedX;         //moving backwards
        }
        if (speedX == 0 || speedX < 0) {    //stop background
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
        }

        if (centerX <= 200 && speedX > 0) {     //moving forwards
            centerX += speedX;
        }

        if (speedX > 0 && centerX > 200) {      //scroll background
            bg1.setSpeedX(-MOVESPEED / 5);
            bg2.setSpeedX(-MOVESPEED / 5);
        }

        // updates y position
        speedY++;
        centerY += speedY; // this part causes some ground stutter

        if (jumped == true) {
            speedY += 1;

            if (speedY > 3) {
                jumped = true;
            }
        }

        // prevents going beyond x=0
        if (centerX + speedX <= 60) {
            centerX = 61;
        }

        rect.set(centerX - 34, centerY - 43, centerX + 34, centerY + 61);
        yellowRed.set(centerX - 110, centerY - 110, centerX + 190, centerY + 190);
        rectLeft.set(centerX - 34, centerY - 15, centerX - 30, centerY + 15);
        rectRight.set(centerX + 15, centerY - 15, centerX + 32, centerY + 15);
    }


    public void moveRight() {
        if (ducked == false) {
            speedX = MOVESPEED;
        }

    }

    public void moveLeft() {
        if (ducked == false) {
            speedX = -MOVESPEED;
        }

    }

    public void stopRight() {  //prevent stutter when changing directions, and forcing to release
        setMovingRight(false); //movement key before stopping that direction
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }


    public void stop() {
        if (isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        }

        if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        }

        if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }
    }

    public void jump() {
        if (jumped == false) {
            speedY = JUMPSPEED;
            Assets.jump.play(0.4f); //jump sound
            jumped = true;
        }
    }

    public void shoot() {
        if (readyToFire) {
            Projectile p = new Projectile(centerX, centerY + 10, 1);
            projectiles.add(p);
        }
        GameScreen.flash = true; //muzzle flash

    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public boolean isJumped() {
        return jumped;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public boolean isDucked() {
        return ducked;
    }

    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public ArrayList getProjectiles() {
        return projectiles;
    }

    public boolean isReadyToFire() {
        return readyToFire;
    }

    public void setReadyToFire(boolean readyToFire) {
        this.readyToFire = readyToFire;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Demon.health = health;
    }

    public static Rect getRect() {
        return rect;
    }

    public static void setRect(Rect rect) {
        Demon.rect = rect;
    }

    public static Rect getRectRight() {
        return rectRight;
    }

    public static void setRectRight(Rect rectRight) {
        Demon.rectRight = rectRight;
    }

    public static Rect getRectLeft() {
        return rectLeft;
    }

    public static void setRectLeft(Rect rectLeft) {
        Demon.rectLeft = rectLeft;
    }

    public static Rect getYellowRed() {
        return yellowRed;
    }

    public static void setYellowRed(Rect yellowRed) {
        Demon.yellowRed = yellowRed;
    }

}