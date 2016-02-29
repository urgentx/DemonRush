package com.barco.demonrush;

import com.barco.framework.Game;
import com.barco.framework.Graphics;
import com.barco.framework.Screen;

/**
 * Screen for loading Splash image.
 */
public class SplashLoadingScreen extends Screen {

    public SplashLoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.splash = g.newImage("splash.jpg", Graphics.ImageFormat.RGB565);
        game.setScreen(new LoadingScreen(game));
    }

    @Override
    public void paint(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
