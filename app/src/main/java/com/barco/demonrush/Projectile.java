package com.barco.demonrush;

import android.graphics.Rect;

import java.util.ArrayList;

public class Projectile {

    private int  speedY, x, y;
    private final int  speedX,type;

    private boolean visible;
    private Rect r;

    private Demon demon = GameScreen.getDemon();

    public Projectile(int startX, int startY, int type) {
        x = startX;
        y = startY;
        speedY = 0;
        this.type = type;
        if(type == 1) {
            speedX = 7;
        } else if( type == 3){
            speedX = -2;
        } else{
            speedX = -3;
        }

        visible = true;
        r = new Rect(0,0,0,0);

    }

    public void update(){
        if(type ==2 || type ==3){
            x += -3;
        }
        x += speedX;
        y += speedY;
        if(type == 3){
            follow();
            if(Math.random()*10 <5){
                y+=1;
            }else {
                y-=1;
            }
        }
        r.set(x, y, x + 10, y + 5);
        if (x > 800){
            visible = false;
            r = null;
        }

        if (x < 801) {
            checkCollision();
        }
    }

    public void follow(){
        if(demon.getCenterY() >= y){
            speedY = 1;
        } else{
            speedY = -1;
        }
    }

    public void checkCollision(){

        if(type == 1) {
            ArrayList<Alien> aliens = GameScreen.getAliens();
            ArrayList<Crawler> crawlers = GameScreen.getCrawlers();
            ArrayList<Boss> bosses = GameScreen.getBosses();
            for (Alien a : aliens) {
                if (Rect.intersects(r, a.r)) {
                    Assets.click.play(1);
                    visible = false;

                    if (a.health > 0) {
                        a.health--;
                    }
                    if (a.health == 0) {
                        a.setCenterX(-100);

                    }
                }
            }
            for (Crawler c : crawlers) {
                if (Rect.intersects(r, c.r)) {
                    Assets.click.play(1);
                    visible = false;

                    if (c.health > 0) {
                        c.health--;
                    }
                    if (c.health == 0) {
                        c.setCenterX(-100);

                    }
                }
            }

            for (Boss b : bosses) {
                if (Rect.intersects(r, b.r)) {
                    Assets.click.play(1);
                    visible = false;

                    if (b.health > 0) {
                        b.health--;
                    }
                    if (b.health == 0) {
                        b.setCenterX(-100);

                    }
                }

                for(Projectile p : b.getProjectiles()){
                    if(p.getR() != null){
                        if(Rect.intersects(getR(),p.getR())){

                            p.setVisible(false);
                            visible = false;
                        }
                    }

                }
            }




        }
        else{
            if(Rect.intersects(r, Demon.getRect())){
                if(demon.isDucked()){
                    visible = false;
                    Assets.blip.play(1);

                }else {
                    Demon.setHealth(Demon.getHealth() - 10);
                    GameScreen.shake = true;
                    Assets.explode.play(0.85f);
                    visible = false;
                }
            }
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public Rect getR() {
        return r;
    }

    public void setR(Rect r) {
        this.r = r;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
