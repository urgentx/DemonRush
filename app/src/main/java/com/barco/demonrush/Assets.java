package com.barco.demonrush;

import android.util.Log;

import com.barco.framework.Image;
import com.barco.framework.Music;
import com.barco.framework.Sound;

/**
 * Holds all game assets. Loads theme music.
 */

public class Assets {

    public static Image menu, levelSelect, levelSelect2, levelSelect3,intro1, intro2, intro3, splash, background, character, character2, character3, character4, character5, fireball, alien, alien2, alien3;
    public static Image crawler1, crawler2, crawler3, crawler4, crawler5, portal, boss, boss2, boss3, boss4;
    public static Image tile, tileMould, tileOcean, characterJumped, characterDown;
    public static Image button, rocket, fireball2;
    public static Sound click, jump, explode, blip;
    public static Music theme;

    public static void load(SampleGame sampleGame){
        theme = sampleGame.getAudio().createMusic("menutheme.mp3");
        theme.setLooping(true);
        theme.setVolume(1);
        theme.play();
    }
}
