package com.barco.demonrush;

import com.barco.framework.Game;
import com.barco.framework.Graphics;
import com.barco.framework.Image;
import com.barco.framework.Input;
import com.barco.framework.Screen;

import java.util.List;

/**
 * Created by Barco on 15-Jan-16.
 */
public class IntroScreen extends Screen {
    Image currentImage = Assets.intro1;
    public IntroScreen (Game game){
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int len = touchEvents.size();
        if (currentImage.equals(Assets.intro1)) {
            if (len > 0) {
                currentImage = Assets.intro2;

            }
        } else if (currentImage.equals(Assets.intro2)){
            if(len > 1){
                currentImage = Assets.intro3;
            }
        } else if (currentImage.equals(Assets.intro3)){
            if(len > 1) {
                game.setScreen(new GameScreen(game, 1));
            }

        }

    }

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height){
        if (event.x > x && event.x < x + width -1 && event.y > y && event.y < y + height -1){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void paint(float deltaTime){
        Graphics g = game.getGraphics();
        g.drawImage(currentImage, 0, 0);
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
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
