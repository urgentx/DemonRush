package com.barco.demonrush;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Subclass of Enemy. Slower movement, fires homing rockets (Projectile type 3) instead of normal
 * bullets.
 */
public class Boss extends Enemy {

    private boolean readyToFire = true;


    private ArrayList<Projectile> projectiles = new ArrayList<>();


    public Boss(int centerX, int centerY) {
        setCenterX(centerX);
        setCenterY(centerY);
    }

    @Override
    public void update() {
        follow();
        setCenterX(getCenterX() + getSpeedX());
        setCenterY(getCenterY() + getSpeedY());
        setSpeedX(getBg().getSpeedX() * 5 + getMoveSpeed());
        r.set(getCenterX() - 25, getCenterY() - 32, getCenterX() + 65, getCenterY() + 65);

        if (Rect.intersects(r, Demon.yellowRed)) {
            if (Rect.intersects(r, Demon.rect)) {
                checkCollision();
            }

        }
    }

    @Override
    public void attack() {
        if (readyToFire) {
            Projectile p = new Projectile(getCenterX(), getCenterY(), 3);
            projectiles.add(p);
        }
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

}