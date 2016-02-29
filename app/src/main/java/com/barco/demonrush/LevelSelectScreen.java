package com.barco.demonrush;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Environment;

import com.barco.framework.Game;
import com.barco.framework.Graphics;
import com.barco.framework.Input;
import com.barco.framework.Screen;
import com.barco.framework.implementation.AndroidFileIO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Displays level options based on player progress stored in SharedPreferences
 */
public class LevelSelectScreen extends Screen{

        private static Context context;
        private int level = 1;

    public LevelSelectScreen (Game game){
        super(game);

        //read player level progress
        SharedPreferences sp = context.getSharedPreferences("com.urgentx.demonrush.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
        level = sp.getInt("level", 1);
    }


    @Override
    public void update(float deltaTime){  //accept player input and initiate new GameScreen.

        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(int i = 0; i < touchEvents.size(); i++){
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP){
                if(inBounds(event, 150, 50, 500,100)){

                    game.setScreen(new GameScreen(game,1));
                }
                else if(inBounds(event, 150,200,500,100) && (level == 2 || level ==3)){

                    game.setScreen(new GameScreen(game,2));

                }
                else if(inBounds(event, 150,350,500,100) && level == 3){

                    game.setScreen(new GameScreen(game,3));

                } else if(inBounds(event, 665,410,130,50)){

                    game.setScreen(new MainMenuScreen(game));

                }
            }
        }
    }


    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height){ //helper method
        if (event.x > x && event.x < x + width -1 && event.y > y && event.y < y + height -1){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void paint(float deltaTime){
        Graphics g = game.getGraphics();
        if(level == 1) {
            g.drawImage(Assets.levelSelect, 0, 0);
        } else if(level == 2){
            g.drawImage(Assets.levelSelect2,0,0);
        }else if(level == 3){
            g.drawImage(Assets.levelSelect3,0,0);
        }
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
        game.setScreen(new MainMenuScreen(game));
    }

    public static void setContext(Context mcontext) {
        if (context == null)
            context = mcontext;
    }
}


