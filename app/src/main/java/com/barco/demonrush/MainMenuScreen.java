package com.barco.demonrush;

import android.graphics.Color;

import com.barco.framework.Game;
import com.barco.framework.Graphics;
import com.barco.framework.Input;
import com.barco.framework.Input.TouchEvent;
import com.barco.framework.Screen;


import java.util.List;

/**
 * Displays main menu, and accepts user input. Then redirects either to LevelSelectScreen or
 * GameScreen. Kills process if back button pressed.
 */
public class MainMenuScreen extends Screen {

    public MainMenuScreen(Game game) {

        super(game);
    }


    @Override
    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 10, 350, 300, 450)) {

                    game.setScreen(new IntroScreen((game)));

                } else if (inBounds(event, 10, 250, 210, 350)) {

                    game.setScreen(new LevelSelectScreen(game));

                }
            }
        }
    }


    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.menu, 0, 0);

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
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
