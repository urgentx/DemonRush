package com.barco.demonrush;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.barco.framework.Game;
import com.barco.framework.Graphics;
import com.barco.framework.Image;
import com.barco.framework.Input.TouchEvent;
import com.barco.framework.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.lang.Character;
import java.util.Timer;
/**
 * Main gameplay class. Handles user input, loads map from string passed from SampleGame,
 * updates all created objects, paints current object states to screen, checks player status.
 * Also implements muzzle flash and screen shake*/

public class GameScreen extends Screen {

    enum GameState {
        Ready, Running, Paused, GameOver, Won
    }

    GameState state = GameState.Ready;

    //variable setup

    private static Background bg1, bg2;
    private static Demon demon;

    private Image currentSprite, character, character2, character3, character4, character5, fireball, alien, alien2, alien3, crawler1, crawler2, crawler3, crawler4, crawler5, boss, boss2, boss3, boss4;
    private Animation anim, alienAnim, crawlerAnim, bossAnim;
    public static boolean won = false;
    public static boolean shake = false;
    public static boolean flash = false;

    private ArrayList<Tile> tiles;
    private static ArrayList<Tile> sideTiles;
    private Tile[] tilesArray;
    public static ArrayList<Tile>[] tiles2D;
    private static ArrayList<Alien> aliens;
    private static ArrayList<Boss> bosses;
    private static ArrayList<Crawler> crawlers;
    int map, shakeTime;

    private static Context context; // for accessing SharedPreferences to save/load progress data.

    Paint paint, paint2;

    public GameScreen(Game game, int map) {
        super(game);
        this.map = map;

        //initialise game objects

        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        demon = new Demon();

        aliens = new ArrayList<>();
        tiles = new ArrayList<>();
        sideTiles = new ArrayList<>();
        bosses = new ArrayList<>();
        crawlers = new ArrayList<>();

        character = Assets.character;
        character2 = Assets.character2;
        character3 = Assets.character3;
        character4 = Assets.character4;
        character5 = Assets.character5;

        alien = Assets.alien;
        alien2 = Assets.alien2;
        alien3 = Assets.alien3;
        boss = Assets.boss;
        boss2 = Assets.boss2;
        boss3 = Assets.boss3;
        boss4 = Assets.boss4;

        crawler1 = Assets.crawler1;
        crawler2 = Assets.crawler2;
        crawler3 = Assets.crawler3;
        crawler4 = Assets.crawler4;
        crawler5 = Assets.crawler5;

        fireball = Assets.fireball;

        anim = new Animation();
        anim.addFrame(character, 30);
        anim.addFrame(character2, 30);
        anim.addFrame(character3, 30);
        anim.addFrame(character4, 30);
        anim.addFrame(character5, 30);
        anim.addFrame(character4, 30);
        anim.addFrame(character3, 30);
        anim.addFrame(character2, 30);

        alienAnim = new Animation();
        alienAnim.addFrame(alien, 30);
        alienAnim.addFrame(alien2, 30);
        alienAnim.addFrame(alien3, 30);
        alienAnim.addFrame(alien2, 30);

        bossAnim = new Animation();
        bossAnim.addFrame(boss, 30);
        bossAnim.addFrame(boss2, 30);
        bossAnim.addFrame(boss3, 30);
        bossAnim.addFrame(boss4, 30);


        crawlerAnim = new Animation();
        crawlerAnim.addFrame(crawler1, 30);
        crawlerAnim.addFrame(crawler2, 30);
        crawlerAnim.addFrame(crawler3, 30);
        crawlerAnim.addFrame(crawler4, 30);
        crawlerAnim.addFrame(crawler5, 30);
        crawlerAnim.addFrame(crawler4, 30);
        crawlerAnim.addFrame(crawler3, 30);
        crawlerAnim.addFrame(crawler1, 30);

        currentSprite = anim.getImage(); //character sprite

        loadMap();

        //define paint object
        paint = new Paint();    //small text
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        paint2 = new Paint();    //large text
        paint2.setTextSize(100);
        paint2.setTextAlign(Paint.Align.CENTER);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.WHITE);

    }

    private void loadMap() {

        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        Scanner scanner = null;

        switch (map) {                                        //level number, defined in constructor
            case 1:
                scanner = new Scanner(SampleGame.map);
                break;
            case 2:
                scanner = new Scanner(SampleGame.map2);
                break;
            case 3:
                scanner = new Scanner(SampleGame.map3);
                break;
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //no more lines to read
            if (line == null) {
                break;
            }

            if (!line.startsWith("!")) {                  //ignore "!" lines
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }
        height = lines.size();

        for (int i = 0; i < height; i++) {          //traverse each line
            String line = (String) lines.get(i);
            for (int j = 0; j < width; j++) {

                if (j < line.length()) {

                    char ch = line.charAt(j);
                    char a = 'A';
                    char c = 'C';
                    char e = 'E';
                    char b = 'B';

                    if (Character.compare(ch, a) == 0) {        //add new alien

                        Alien al = new Alien(j * 40, i * 40);
                        aliens.add(al);
                    } else if (Character.compare(ch, c) == 0) {     //add new crawler
                        Crawler cr = new Crawler(j * 40, i * 40);
                        crawlers.add(cr);
                    } else if (Character.compare(ch, b) == 0) {     //add new boss
                        Boss boss = new Boss(j * 40, i * 40);
                        bosses.add(boss);
                    }
                    Tile t = new Tile(j, i, Character.getNumericValue(ch));
                    if (Character.getNumericValue(ch) == 4 || Character.getNumericValue(ch) == 6) {
                        sideTiles.add(t);       //crawlers check these tiles for pathing
                    }
                    tiles.add(t);
                }
            }
        }

        //Arrange tiles in array and only check collisions for relevant tiles

        tilesArray = tiles.toArray(new Tile[tiles.size()]);

        tiles2D = new ArrayList[450];
        Arrays.sort(tilesArray, new Comparator<Tile>() {
            @Override
            public int compare(Tile lhs, Tile rhs) {            //custom comparator function
                if (lhs.getTileX() > rhs.getTileX()) {          //defined
                    return 1;
                } else {
                    return -1;
                }
            }
        });


        for (int i = 0; i < tiles2D.length; i++) {
            tiles2D[i] = new ArrayList<Tile>();
        }

        for (int j = 0; j < tilesArray.length; j++) {

            tiles2D[tilesArray[j].getTileX() / 40].add(tilesArray[j]);


        }
        for (int i = 0; i < tiles2D.length; i++) {
            for (int j = 0; j < tiles2D[i].size(); j++) {
                Log.d("tiles", "X: " + tiles2D[i].get(j).getTileX());
            }
        }


    }

    @Override
    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        //four separate update methods here, depending on game state we call different update()
        //methods

        if (state == GameState.Ready) {
            updateReady(touchEvents);
        }
        if (state == GameState.Running) {
            updateRunning(touchEvents, deltaTime);
        }
        if (state == GameState.Paused) {
            updatePaused(touchEvents);
        }
        if (state == GameState.GameOver) {
            updateGameOver(touchEvents);
        }
        if (state == GameState.Won) {
            updateWon(touchEvents);
        }
    }


    private void updateReady(List<TouchEvent> touchEvents) {
        //start with Ready screen, when user touches screen game begins, state becomes Running,
        //now updateRunning will be called

        if (touchEvents.size() > 0) {
            state = GameState.Running;

        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        //main gameplay update method

        //all touch input handled here:
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i); //set generic of touchevents to TouchEvent?
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, 0, 285, 65, 65)) {
                    demon.jump();
                    currentSprite = anim.getImage();
                    demon.setDucked(false);
                } else if (inBounds(event, 0, 350, 65, 65)) {
                    if (demon.isDucked() == false && demon.isReadyToFire()) {
                        demon.shoot();
                    }
                } else if (inBounds(event, 0, 415, 65, 65) && demon.isJumped() == false) {
                    currentSprite = Assets.characterDown;
                    demon.setDucked(true);
                    demon.setSpeedX(0);
                }
                if (event.x > 400) {
                    //move right
                    demon.moveRight();
                    demon.setMovingRight(true);
                }
            }
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 415, 65, 65)) {
                    currentSprite = anim.getImage();
                    demon.setDucked(false);
                }
                if (inBounds(event, 0, 0, 35, 35)) {
                    pause();
                }
                if (event.x > 400) {
                    //stop right
                    demon.stopRight();
                }
            }
        }

        //check miscellaneous events like death


        //call all object update() methods

        demon.update();
        if (demon.isJumped()) {
            currentSprite = Assets.characterJumped;
        } else if (demon.isJumped() == false && demon.isDucked() == false) {
            currentSprite = anim.getImage();
        }

        ArrayList<Projectile> projectiles = demon.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            if (p.isVisible() == true) {
                p.update();
            } else {
                projectiles.remove(i);
            }

        }

        for (Alien al : aliens) {
            ArrayList<Projectile> alienProjectiles = al.getProjectiles();
            for (int i = 0; i < alienProjectiles.size(); i++) {
                Projectile p = (Projectile) alienProjectiles.get(i);
                if (p.isVisible() == true) {
                    p.update();
                } else {
                    alienProjectiles.remove(i);
                }

            }

        }
        for (Boss boss : bosses) {
            ArrayList<Projectile> bossProjectiles = boss.getProjectiles();
            for (int i = 0; i < bossProjectiles.size(); i++) {
                Projectile p = (Projectile) bossProjectiles.get(i);
                if (p.isVisible() == true) {
                    p.update();
                } else {
                    bossProjectiles.remove(i);
                }

            }

        }

        updateTiles();


        for (Alien aa : aliens) {
            aa.update();
            if (Math.random() < 0.01) {
                aa.attack();
            }
        }
        for (Boss boss : bosses) {
            boss.update();
            if (Math.random() < 0.02) {
                boss.attack();
            }
        }


        for (Crawler cr : crawlers) {
            cr.update();
        }
        bg1.update();
        bg2.update();
        animate();

        if (demon.getCenterY() > 500 || demon.getHealth() <= 0) {
            state = GameState.GameOver;
        }
        if (won) {

            state = GameState.Won;
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1) {
            return true;
        } else {
            return false;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        //process pause menu input
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (inBounds(event, 0, 0, 800, 240)) {
                    if (!inBounds(event, 0, 0, 35, 35)) {
                        resume();
                    }
                }
                if (inBounds(event, 0, 240, 800, 240)) {
                    nullify();
                    goToMenu();
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {

        //process Game Over input
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, 0, 0, 800, 480)) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    private void updateWon(List<TouchEvent> touchEvents) {

        //Process victory input, advance player level
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (inBounds(event, 0, 0, 800, 480)) {
                    nullify();

                    //write player level progress
                    SharedPreferences sp = context.getSharedPreferences("com.urgentx.demonrush.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("level", (sp.getInt("level", 1) + 1));
                    editor.commit();
                    won = false;
                    //end level
                    game.setScreen(new LevelSelectScreen(game));
                    return;
                }
            }
        }
    }

    private synchronized void updateTiles() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            t.update();
            t.collisionCheck();
        }
       /*for(int i = 0; i < tiles2D[demon.getCenterX()].size(); i++){ //check only nearby cartesian
            Tile t = tiles2D[demon.getCenterX()].get(i);              //coordinate tiles for collisions
            t.collisionCheck();
            Log.d("haha", "tileX: " + t.getTileX());
        }*/

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
        g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
        paintTiles(g);
        g.drawString("HP: " + demon.getHealth(), 700, 30, paint);


        if (shake == true) {            //screen shake

            if (shakeTime % 4 == 0) {       //move screen reference point each paint() iteration
                g.screenShake(2, 2);
            } else if (shakeTime % 4 == 1) {
                g.screenShake(-2, 0);
            } else if (shakeTime % 4 == 2) {
                g.screenShake(2, -2);
            } else if (shakeTime % 4 == 3) {
                g.screenShake(-2, 0);
            }
            shakeTime++;
            if (shakeTime >= 20) {
                shakeTime = 0;
                shake = false;

            }
        }

        //first draw game elements

        //BOUNDING RECTANGLES

        //g.drawRect((int)demon.getRect().centerX() - 34, (int)demon.getRect().centerY() - 52, (int)demon.getRect().width(), (int)demon.getRect().height(), Color.BLACK);
        //g.drawRect(demon.getRect(), Color.BLUE);
        //g.drawRect(demon.getRectRight(),Color.RED);
        //g.drawRect((int)demon.getRectLeft().centerX()-5, demon.getRectLeft().centerY() - 30, demon.getRectLeft().width(), demon.getRectLeft().height(), Color.BLUE);
       /* g.drawRect((int)demon.rectRight.getX(), (int)demon.rectRight.getY(), (int)demon.rectRight.getWidth(), (int)demon.rectRight.getHeight());
        g.drawRect((int)al.r.getX(), (int)al.r.getY(), (int)al.r.getWidth(),(int)al.r.getHeight());
        g.drawRect((int)al2.r.getX(), (int)al2.r.getY(), (int)al2.r.getWidth(),(int)al2.r.getHeight());*/

        //ACTUAL ASSETS
        g.drawImage(currentSprite, demon.getCenterX() - 61, demon.getCenterY() - 63);
        ArrayList<Projectile> projectiles = demon.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            g.drawImage(Assets.fireball, p.getX(), p.getY());
        }
        if (flash == true) { //muzzle flash
            g.drawCircle(demon.getCenterX(), demon.getCenterY() + 20, 10, Color.WHITE);
            flash = false;
        }

        for (Alien aa : aliens) {
            g.drawImage(alienAnim.getImage(), aa.getCenterX() - 48, aa.getCenterY() - 48);
            ArrayList<Projectile> alienProjectiles = aa.getProjectiles();
            for (int i = 0; i < alienProjectiles.size(); i++) {
                Projectile p = alienProjectiles.get(i);
                g.drawImage(Assets.fireball2, p.getX(), p.getY());
            }
        }

        for (Boss boss1 : bosses) {
            g.drawImage(bossAnim.getImage(), boss1.getCenterX() - 48, boss1.getCenterY() - 48);
            ArrayList<Projectile> bossProjectiles = boss1.getProjectiles();
            for (int i = 0; i < bossProjectiles.size(); i++) {
                Projectile p = (Projectile) bossProjectiles.get(i);
                g.drawImage(Assets.rocket, p.getX(), p.getY());
            }
        }

        for (Crawler cr : crawlers) {
            g.drawImage(crawlerAnim.getImage(), cr.getCenterX() - 48, cr.getCenterY() - 48);
        }


        //draw UI above game elements
        if (state == GameState.Ready) {
            drawReadyUI();
        }
        if (state == GameState.Running) {
            drawRunningUI();
        }
        if (state == GameState.Paused) {
            drawPausedUI();
        }
        if (state == GameState.GameOver) {
            drawGameOverUI();
        }
        if (state == GameState.Won) {
            drawWonUI();
        }
    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            if (t.getType() != 0) {                //don't paint middleground tiles??
                g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY());
            }
        }
    }

    public void animate() {   //might be too quick for CPU
        anim.update(10);
        alienAnim.update(6);
        crawlerAnim.update(10);
        bossAnim.update(6);
    }

    private void nullify() {
        //set all variables to null, recreate in constructor

        paint = null;
        bg1 = null;
        bg2 = null;
        demon.setHealth(200);
        demon = null;


        for (Alien aa : aliens) {
            aa = null;
        }

        for (Boss bs : bosses) {
            bs = null;
        }

        for (Crawler cr : crawlers) {
            cr = null;
        }

        currentSprite = null;
        character = null;
        character2 = null;
        character3 = null;
        character4 = null;
        character5 = null;
        fireball = null;
        alien = null;
        alien2 = null;
        alien3 = null;

        anim = null;
        alienAnim = null;
        crawlerAnim = null;
        bossAnim = null;

        tiles = null;
        tilesArray = null;
        tiles2D = null;
        aliens = null;
        paint2 = null;

        //call garbage collector
        System.gc();
    }

    private void goToMenu() {

        synchronized (this) {
            Assets.theme.seekBegin();
            Assets.theme.play();

        }
        game.setScreen(new LoadingScreen(game));
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap to start.", 400, 240, paint);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.button, 0, 285, 0, 0, 65, 65);
        g.drawImage(Assets.button, 0, 350, 0, 65, 65, 65);
        g.drawImage(Assets.button, 0, 415, 0, 130, 65, 65);
        g.drawImage(Assets.button, 0, 0, 0, 195, 35, 35);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        //Black out screen to display pause screen
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Resume", 400, 165, paint2);
        g.drawString("Menu", 400, 360, paint2);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER", 400, 240, paint2);
        g.drawString("Tap to return.", 400, 290, paint);
    }

    private void drawWonUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("Nek level unlocked!", 400, 240, paint2);
        g.drawString("Return to level select.", 400, 290, paint);
    }

    @Override
    public void pause() {
        if (state == GameState.Running) {
            state = GameState.Paused;
        }
    }

    @Override
    public void resume() {
        if (state == GameState.Paused) {
            state = GameState.Running;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }



    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

    public static Demon getDemon() {
        return demon;
    }

    public static ArrayList<Alien> getAliens() {
        return aliens;
    }

    public static ArrayList<Crawler> getCrawlers() {
        return crawlers;
    }

    public static void setCrawlers(ArrayList<Crawler> crawlers) {
        GameScreen.crawlers = crawlers;
    }

    public static ArrayList<Tile> getSideTiles() {
        return sideTiles;
    }

    public static ArrayList<Boss> getBosses() {
        return bosses;
    }

    public static void setBosses(ArrayList<Boss> bosses) {
        GameScreen.bosses = bosses;
    }

    public static void setSideTiles(ArrayList<Tile> sideTiles) {
        GameScreen.sideTiles = sideTiles;
    }

    public static void setContext(Context mcontext) {
        if (context == null)
            context = mcontext;
    }


}