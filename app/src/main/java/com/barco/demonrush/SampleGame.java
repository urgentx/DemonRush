package com.barco.demonrush;

import android.util.Log;

import com.barco.framework.Screen;
import com.barco.framework.implementation.AndroidGame;
import com.barco.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Main game activity, all subsequent game code runs in a set of Screen classes.
 * Provides methods for handling app pause/resume, as well as reading in map files.
 */


public class SampleGame extends AndroidGame {

    public static String map, map2, map3;  //Read from files, then passed to GameScreen for parsing.
    boolean firstTimeCreate = true;

    @Override
    public Screen getInitScreen() {

        if (firstTimeCreate) {
            Assets.load(this);
            firstTimeCreate = false;
        }

        InputStream is = getResources().openRawResource(R.raw.map1);
        map = convertStreamToString(is);
        is = getResources().openRawResource(R.raw.map2);
        map2 = convertStreamToString(is);
        InputStream ls = getResources().openRawResource(R.raw.map3);
        map3 = convertStreamToString(ls);

        LevelSelectScreen.setContext(getApplicationContext());      //pass app Context for access to
        GameScreen.setContext(getApplicationContext());             //SharedPreferences for saving data

        return new SplashLoadingScreen(this);
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append((line + "\n"));
            }
        } catch (IOException e) {
            Log.w("LOG", e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.w("LOG", e.getMessage());
            }
        }
        return sb.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        Assets.theme.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        Assets.theme.pause();
    }
}
