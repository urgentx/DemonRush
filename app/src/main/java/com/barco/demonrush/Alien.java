package com.barco.demonrush;

import java.util.ArrayList;

/**
 * Subclass of Enemy. Just provides an attack() method.
 */
public class Alien extends Enemy {

    private boolean readyToFire = true;

    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public Alien(int centerX, int centerY) {
        setCenterX(centerX);
        setCenterY(centerY);
    }

    @Override
    public void attack(){
        if(readyToFire){
            Projectile p = new Projectile(getCenterX(), getCenterY(), 2);
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