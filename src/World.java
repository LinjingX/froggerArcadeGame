import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.*;

/**
 * representation of this shadow leap game
 */
public class World {
    /**
     * the tile size
     */
    public static final int TILE_SIZE = 48;
    private static final int FIRSTLIFE_X = 24;
    private static final int FIRSTLIFE_Y = 744;
    private static final int LIFE_INTERVAL = 32;
    private static final int HOLE1_L_X = 72;
    private static final int HOLE1_R_X = 168;
    private static final int HOLE2_L_X = 264;
    private static final int HOLE2_R_X = 360;
    private static final int HOLE3_L_X = 456;
    private static final int HOLE3_R_X = 552;
    private static final int HOLE4_L_X = 648;
    private static final int HOLE4_R_X = 744;
    private static final int HOLE5_L_X = 840;
    private static final int HOLE5_R_X = 936;
    private static final int LOW_RANGE = 25;
    private static final int HIGH_RANGE = 36;
    private static final int INDEX0 = 0;
    private static final int INDEX1 = 1;
    private static final int INDEX2 = 2;
    private static final int INDEX3 = 3;
    private static final int TEXTLENGTH4 = 4;
    private static final int MAXHOLE = 5;
    private static final int LEVEL0 = 0;
    private static final int LEVEL1 = 1;
    private static final String LEVEL0_PATH = "assets/levels/0.lvl";
    private static final String LEVEL1_PATH = "assets/levels/1.lvl";
    private static boolean hole1Filled = false;
    private static boolean hole2Filled = false;
    private static boolean hole3Filled = false;
    private static boolean hole4Filled = false;
    private static boolean hole5Filled = false;
    private static boolean noNextLevel = false;
    /**
     * the random time to be generated when the game starts. to be used for extra life
     */
    public static int randomTime;
    /**
     * the start time of each period
     */
    public static Date startTime;
    /**
     * the current time
     */
    public static Date currentTime;
//    private static Date extraLifeAppearTime;
//    private static Date extraLifeDisappearTime;
//    private static boolean extraLifeBeginAppear = false;

    public static ArrayList<Sprite> sprites = new ArrayList<>();
    private static ArrayList<Tile> lives = new ArrayList<>();
    private static ArrayList<Tile> frogs = new ArrayList<>();
    private static ArrayList<Sprite> extraLife = new ArrayList<>();
    private static ArrayList<Sprite> logs = new ArrayList<>();

    /**
     * constructor
     * @throws IOException
     */
    public World() throws IOException {
        loadSprite(LEVEL0);
        putExtraLife();
        randomTime = generator();
        Calendar cal = Calendar.getInstance();
        startTime = cal.getTime();
        //System.out.println("RandomTime is " + randomTime);
    }

    //generate a random number (time in seconds), to be used for extra life
    private int generator() {
        Random r = new Random();
        int low = LOW_RANGE;
        // 25 is inclusive and 36 is exclusive
        int high = HIGH_RANGE;
        int randomNumber = r.nextInt(high - low) + low;
        return randomNumber;
    }

    /**
     * reset the game with different level
     */
    public void reset() {
        try {
            sprites.clear();
            loadSprite(LEVEL1);
            frogs.clear();
            hole1Filled = false;
            hole2Filled = false;
            hole3Filled = false;
            hole4Filled = false;
            hole5Filled = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * update the changes in the world
     *
     * @param input the key board input from user (if any)
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    public void update(Input input, int delta) {
        // generate the current time for each update called
        Calendar cal = Calendar.getInstance();
        currentTime = cal.getTime();


        for (Sprite sprite : sprites) {
            sprite.update(input, delta);
        }

        for (Sprite extra : extraLife) {
            extra.update(input, delta);
        }

        lives.clear();
        // draw the first life icon
        lives.add(Tile.createLifeTile(FIRSTLIFE_X, FIRSTLIFE_Y));
        // calculate the location of the rest of the icons
        for (int i = 1; i < Player.lifeNum; i++) {
            lives.add(Tile.createLifeTile(lives.get(lives.size() - 1).getX() + LIFE_INTERVAL, FIRSTLIFE_Y));
        }

        //check if the play collides with the extra life
        for (Sprite sprite : sprites) {
            //boolean flag = false;
            for (Sprite extra : extraLife) {
                if (sprite instanceof Player && extra instanceof ExtraLife) {
                    if (sprite.collides(extra) && ((ExtraLife) extra).extraLifeBeginAppear) {
                        Player.lifeNum += 1;
                        ((ExtraLife) extra).extraLifeBeginAppear = false;
                        // set the disappear time of the extra life to be the time when it collides with player
                        ((ExtraLife) extra).extraLifeDisappearTime = cal.getTime();
                    }
                }
            }
//            if (flag) {
//                extraLife.clear();
//            }
        }

        // check if the player is on log/long log/turtles
        for (Sprite sprite1 : sprites) {
            for (Sprite sprite2 : sprites) {
                if (sprite1 != sprite2
                        && sprite1.collides(sprite2)) {
                    if ((sprite1.hasTag(Sprite.RIDEABLE) && sprite2 instanceof Player)) {
                        ((Player) sprite2).ride = true;
                    } else if ((sprite2.hasTag(Sprite.RIDEABLE) && sprite1 instanceof Player)) {
                        ((Player) sprite1).ride = true;
                    }
                }
            }
        }

        //loop over all pairs of sprites and test for intersection
        for (Sprite sprite1 : sprites) {
            for (Sprite sprite2 : sprites) {
                if (sprite1 != sprite2
                        && sprite1.collides(sprite2)) {
                    sprite1.onCollision(sprite2, delta);
                }
            }
        }

        checkHole();

        // all holes are filled and there is no more next level, the game ends
        if (frogs.size() == MAXHOLE && noNextLevel) {
            System.exit(0);
        }
        // all holes are filled, go to the next level and reset the game
        if (frogs.size() == MAXHOLE) {
            noNextLevel = true;
            reset();
        }

    }

    //load all sprites according to the contents in different .lvl file
    private void loadSprite(int levelNum) throws IOException {
        String[] textList;
        String spriteName;
        float xValue;
        float yValue;
        boolean direction = false;
        File file = new File(LEVEL0_PATH);
        if (levelNum == LEVEL1) {
            file = new File(LEVEL1_PATH);
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String text;
        while ((text = br.readLine()) != null) {
            textList = text.split(",");
            spriteName = textList[INDEX0];
            xValue = Float.parseFloat(textList[INDEX1]);
            yValue = Float.parseFloat(textList[INDEX2]);
            if (textList.length == TEXTLENGTH4) {
                direction = Boolean.parseBoolean(textList[INDEX3]);
            }
            //create tiles
            if (spriteName.equals("water")) {
                sprites.add(Tile.createWaterTile(xValue, yValue));
            }
            if (spriteName.equals("grass")) {
                sprites.add(Tile.createGrassTile(xValue, yValue));
            }
            if (spriteName.equals("tree")) {
                sprites.add(Tile.createTreeTile(xValue, yValue));
            }
            if (spriteName.equals("bus")) {
                sprites.add(new Bus(xValue, yValue, direction));
            }
            if (spriteName.equals("racecar")) {
                sprites.add(new Racecar(xValue, yValue, direction));
            }
            if (spriteName.equals("bike")) {
                sprites.add(new Bike(xValue, yValue, direction));
            }
            if (spriteName.equals("bulldozer")) {
                sprites.add(new Bulldozer(xValue, yValue, direction));
            }
            if (spriteName.equals("log")) {
                sprites.add(new Log(xValue, yValue, direction));
            }
            if (spriteName.equals("longLog")) {
                sprites.add(new LongLog(xValue, yValue, direction));
            }
            if (spriteName.equals("turtle")) {
                sprites.add(new Turtle(xValue, yValue, direction));
            }

        }

        // create player
        sprites.add(new Player(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT - TILE_SIZE));
    }

    // choose a random log and put extra life on it
    protected static void putExtraLife() {
        extraLife.clear();
        logs.clear();
        Random r = new Random();
        int index;
        for (Sprite logtype : sprites) {
            if (logtype instanceof Log || logtype instanceof LongLog) {
                logs.add(logtype);
            }
        }
        // generate a random index
        index = r.nextInt(logs.size());
        // get the lof from that index
        Sprite randomLog = logs.get(index);
        if (randomLog instanceof Log) {
            extraLife.add(new ExtraLife(randomLog.getX(), randomLog.getY(), randomLog.getMoveRight(),
                    randomLog.getSpeed(), ((Log) randomLog).getLength()));
        } else if (randomLog instanceof LongLog) {
            extraLife.add(new ExtraLife(randomLog.getX(), randomLog.getY(), randomLog.getMoveRight(),
                    randomLog.getSpeed(), ((LongLog) randomLog).getLength()));
        }
        //System.out.println("Random log index = " + index);
    }

    // check whether the player has entered any hole
    // if enter a hole that is already occupied, loose a life
    // if enter a hole that is empty, draw a frog in that hole
    private void checkHole(){
        for (Sprite sprite : sprites) {
            if (sprite instanceof Player) {
                if (HOLE1_L_X < sprite.getX() && sprite.getX() < HOLE1_R_X && sprite.getY() == TILE_SIZE) {
                    if (!hole1Filled) {
                        hole1Filled = true;
                        frogs.add(Tile.createFrogTile((HOLE1_L_X+HOLE1_R_X)/2, TILE_SIZE));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if (HOLE2_L_X < sprite.getX() && sprite.getX() < HOLE2_R_X && sprite.getY() == TILE_SIZE) {
                    if (!hole2Filled) {
                        hole2Filled = true;
                        frogs.add(Tile.createFrogTile((HOLE2_L_X+HOLE2_R_X)/2, TILE_SIZE));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if (HOLE3_L_X < sprite.getX() && sprite.getX() < HOLE3_R_X && sprite.getY() == TILE_SIZE) {
                    if (!hole3Filled) {
                        hole3Filled = true;
                        frogs.add(Tile.createFrogTile((HOLE3_L_X+HOLE3_R_X)/2, TILE_SIZE));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if (HOLE4_L_X < sprite.getX() && sprite.getX() < HOLE4_R_X && sprite.getY() == TILE_SIZE) {
                    if (!hole4Filled) {
                        hole4Filled = true;
                        frogs.add(Tile.createFrogTile((HOLE4_L_X+HOLE4_R_X)/2, TILE_SIZE));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if (HOLE5_L_X < sprite.getX() && sprite.getX() < HOLE5_R_X && sprite.getY() == TILE_SIZE) {
                    if (!hole5Filled) {
                        hole5Filled = true;
                        frogs.add(Tile.createFrogTile((HOLE5_L_X+HOLE5_R_X)/2, TILE_SIZE));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
            }
        }
    }

    /**
     * Render all the sprites, so it reflects the current state of sprites.
     *
     * @param g  The Slick graphics object, used for drawing.
     */
    public void render(Graphics g) {
        for (Sprite sprite : sprites) {
            sprite.render();
        }
        for (Tile life : lives) {
            life.render();
        }
        for (Tile frog : frogs) {
            frog.render();
        }
        for (Sprite extra : extraLife) {
            extra.render();
        }
    }
}
