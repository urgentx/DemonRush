package com.barco.demonrush;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.barco.framework.Game;
import com.barco.framework.Graphics;
import com.barco.framework.Screen;

/**
 * Load all assets into memory, while displaying Splash image.
 * Only visible for a second or two on most devices.
 */
public class LoadingScreen extends Screen {

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {

        //Load images:
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.png", Graphics.ImageFormat.RGB565);
        Assets.menu2 = g.newImage("menu2.png", Graphics.ImageFormat.RGB565);
        Assets.menu3 = g.newImage("menu3.png", Graphics.ImageFormat.RGB565);
        Assets.menu4 = g.newImage("menu4.png", Graphics.ImageFormat.RGB565);
        Assets.menu5 = g.newImage("menu5.png", Graphics.ImageFormat.RGB565);
        Assets.menu6 = g.newImage("menu6.png", Graphics.ImageFormat.RGB565);
        Assets.menu7 = g.newImage("menu7.png", Graphics.ImageFormat.RGB565);
        Assets.menu8 = g.newImage("menu8.png", Graphics.ImageFormat.RGB565);
        Assets.menu9 = g.newImage("menu9.png", Graphics.ImageFormat.RGB565);
        Assets.menu10 = g.newImage("menu10.png", Graphics.ImageFormat.RGB565);
        Assets.levelSelect = g.newImage("levelselect.png", Graphics.ImageFormat.RGB565);
        Assets.levelSelect2 = g.newImage("levelselect2.png", Graphics.ImageFormat.RGB565);
        Assets.levelSelect3 = g.newImage("levelselect3.png", Graphics.ImageFormat.RGB565);
        Assets.intro1 = g.newImage("intro1.png", Graphics.ImageFormat.RGB565);
        Assets.intro2 = g.newImage("intro2.png", Graphics.ImageFormat.RGB565);
        Assets.intro3 = g.newImage("intro3.png", Graphics.ImageFormat.RGB565);
        Assets.background = g.newImage("background.png", Graphics.ImageFormat.RGB565);
        Assets.character = g.newImage("character.png", Graphics.ImageFormat.ARGB4444);
        Assets.character2 = g.newImage("character2.png", Graphics.ImageFormat.ARGB4444);
        Assets.character3 = g.newImage("character3.png", Graphics.ImageFormat.ARGB4444);
        Assets.character4 = g.newImage("character4.png", Graphics.ImageFormat.ARGB4444);
        Assets.character5 = g.newImage("character5.png", Graphics.ImageFormat.ARGB4444);
        Assets.characterJumped = g.newImage("jumped.png", Graphics.ImageFormat.ARGB4444);
        Assets.characterDown = g.newImage("down.png", Graphics.ImageFormat.ARGB4444);
        Assets.fireball = g.newImage("fireball.png", Graphics.ImageFormat.RGB565);
        Assets.fireball2 = g.newImage("fireball2.png", Graphics.ImageFormat.RGB565);
        Assets.rocket = g.newImage("rocket.png", Graphics.ImageFormat.RGB565);

        Assets.alien = g.newImage("alien.png", Graphics.ImageFormat.ARGB4444);
        Assets.alien2 = g.newImage("alien2.png", Graphics.ImageFormat.ARGB4444);
        Assets.alien3 = g.newImage("alien3.png", Graphics.ImageFormat.ARGB4444);
        Assets.crawler1 = g.newImage("crawler1.png", Graphics.ImageFormat.ARGB4444);
        Assets.crawler2 = g.newImage("crawler2.png", Graphics.ImageFormat.ARGB4444);
        Assets.crawler3 = g.newImage("crawler3.png", Graphics.ImageFormat.ARGB4444);
        Assets.crawler4 = g.newImage("crawler4.png", Graphics.ImageFormat.ARGB4444);
        Assets.crawler5 = g.newImage("crawler5.png", Graphics.ImageFormat.ARGB4444);
        Assets.boss = g.newImage("boss.png", Graphics.ImageFormat.ARGB4444);
        Assets.boss2 = g.newImage("boss2.png", Graphics.ImageFormat.ARGB4444);
        Assets.boss3 = g.newImage("boss3.png", Graphics.ImageFormat.ARGB4444);
        Assets.boss4 = g.newImage("boss4.png", Graphics.ImageFormat.ARGB4444);

        Assets.portal = g.newImage("portal.png", Graphics.ImageFormat.ARGB4444);
        Assets.tile = g.newImage("tile.png", Graphics.ImageFormat.ARGB4444);
        Assets.tileMould = g.newImage("tilemold.png", Graphics.ImageFormat.ARGB4444);

        Assets.button = g.newImage("button.jpg", Graphics.ImageFormat.RGB565);

        //Load sounds:
        Assets.click =  game.getAudio().createSound("explode.ogg");
        Assets.jump = game.getAudio().createSound("jump.ogg");
        Assets.explode = game.getAudio().createSound("explode2.ogg");
        Assets.blip = game.getAudio().createSound("blip.ogg");

        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void paint(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawImage(Assets.splash,0,0);
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){

    }

    @Override
    public void backButton(){

    }
}
