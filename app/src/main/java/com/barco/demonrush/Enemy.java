package com.barco.demonrush;

import android.graphics.Rect;

/**
 * Enemy superclass. Handles all movement, and behaves like a flying alien type.
 * Checks collision with Demon only.
 */
public class Enemy {

    private int power, speedX, speedY, centerX, centerY;
    private Background bg = GameScreen.getBg1();
    public Rect r = new Rect(0, 0, 0, 0);
    public int health = 5;
    private int moveSpeed;
    private Demon demon = GameScreen.getDemon();


    // behavioural methods
    public void update() {
        follow();
        centerX += speedX;
        centerY += speedY;
        speedX = bg.getSpeedX() * 3 + moveSpeed;
        r.set(centerX - 25, centerY - 32, centerX + 25, centerY + 35);

        if (Rect.intersects(r, Demon.yellowRed)) {      //only check tiles around Demon
            if (Rect.intersects(r, Demon.rect)) {
                checkCollision();
            }

        }
    }

    public void checkCollision() {
        demon.setHealth(demon.getHealth() - 30);
        GameScreen.shake = true;
        setCenterX(-100);
    }

    public void follow() {          //adjust Y value in Demon's direction
        if (centerX < -95 || centerX > 810) {
            moveSpeed = 0;
        } else if (Math.abs(demon.getCenterX() - centerX) < 5) {
            moveSpeed = 0;
        } else {
            if (demon.getCenterX() >= centerX) {
                moveSpeed = 1;
            } else {
                moveSpeed = -1;
            }
        }

        if (centerY < 20 || centerY > 480) {
            speedY = 0;
        } else if (Math.abs(demon.getCenterY() - centerY) < 5) {
            speedY = 0;
        } else {
            if (demon.getCenterY() >= centerY) {
                speedY = 1;
            } else {
                speedY = -1;
            }
        }

    }

    public void die() {

    }

    public void attack() {

    }

    public int getPower() {
        return power;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public int getCenterY() {
        return centerY;
    }

    public Background getBg() {
        return bg;
    }

    public Demon getDemon() {
        return demon;
    }

    public void setDemon(Demon demon) {
        this.demon = demon;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }


    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setBg(Background bg) {
        this.bg = bg;
    }

    public Rect getR() {
        return r;
    }

    public void setR(Rect r) {
        this.r = r;
    }
}