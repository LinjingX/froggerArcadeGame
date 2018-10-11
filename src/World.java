import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.io.*;


public class World {
    public static final int TILE_SIZE = 48;
    public static final String LEVEL0_PATH = "assets/levels/0.lvl";
    public static final String LEVEL1_PATH = "assets/levels/1.lvl";
    private String[] textList;
    private String spriteName;
    private float xValue;
    private float yValue;
    private boolean direction;
    private static int holeFlag1 = 0;
    private static int holeFlag2 = 0;
    private static int holeFlag3 = 0;
    private static int holeFlag4 = 0;
    private static int holeFlag5 = 0;
    private static int noNextLevel = 0;
    public static int randomTime;
    public static Date startTime;
    public static Date currentTime;
    private static Date extraLifeAppearTime;
    private static Date extraLifeDisappearTime;
    private static boolean extraLifeBeginAppear = false;

    public static ArrayList<Sprite> sprites = new ArrayList<>();
    private ArrayList<Tile> lives = new ArrayList<>();
    public static ArrayList<Tile> frogs = new ArrayList<>();
    private ArrayList<Sprite> extraLife = new ArrayList<>();
    private ArrayList<Sprite> logs = new ArrayList<>();


    public World() throws IOException {
        loadSprite(1);
        //Sprite extraLife = putExtraLife();
        putExtraLife();
        randomTime = generator();
        Calendar cal = Calendar.getInstance();
        startTime = cal.getTime();
        System.out.println("RandomTime is " + randomTime);
    }

    private int generator() {
        Random r = new Random();
        int low = 25;
        int high = 35;
        int randomNumber = r.nextInt(high - low) + low;
        return randomNumber;
    }

    public void reset() {
        try {
            sprites.clear();
            loadSprite(1);
            frogs.clear();
            holeFlag1 = 0;
            holeFlag2 = 0;
            holeFlag3 = 0;
            holeFlag4 = 0;
            holeFlag5 = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Input input, int delta) {
        Calendar cal = Calendar.getInstance();
        currentTime = cal.getTime();

//        if (extraLifeDisappearTime == null) {
//            extraLifeDisappearTime = cal.getTime();
//        }
//
//        long diff = currentTime.getTime() - startTime.getTime();
//        long diffSeconds = diff / 1000 % 60;
//
//        long diff3 = currentTime.getTime() - extraLifeDisappearTime.getTime();
//        long diff3Seconds = diff3 / 1000 % 60;
//
//        if (diff3Seconds >= randomTime && extraLifeBeginAppear == false) {
//            System.out.println("Extra live appear");
//            extraLifeBeginAppear = true;
//            extraLifeAppearTime = cal.getTime();
//            startTime = cal.getTime();
//        }
//
//        if (extraLifeBeginAppear == false) {
//            if (diffSeconds >= randomTime) {
//                System.out.println("Extra live appear");
//                extraLifeBeginAppear = true;
//                extraLifeAppearTime = cal.getTime();
//                startTime = cal.getTime();
//            }
//        } else {
//            long diff2 = currentTime.getTime() - extraLifeAppearTime.getTime();
//            long diff2Seconds = diff2 / 1000 % 60;
//
//            if (diff2Seconds >= 14) {
//                System.out.println("Extra live disappear");
//                extraLifeBeginAppear = false;
//                extraLifeDisappearTime = cal.getTime();
//            } else if (diffSeconds >= 2) {
//                System.out.println("Move left or right");
//                startTime = cal.getTime();
//            }
//        }*/


        lives = new ArrayList<>();

        for (Sprite sprite : sprites) {
            sprite.update(input, delta);
        }

        for (Sprite extra : extraLife) {
            extra.update(input, delta);
        }

        lives.add(Tile.createLifeTile(24, 744));
        for (int i = 1; i < Player.lifeNum; i++) {
            lives.add(Tile.createLifeTile(lives.get(lives.size() - 1).getX() + 32, 744));
        }

        for (Sprite sprite : sprites) {
            if (sprite instanceof Player) {
                //System.out.println(sprite.getX());
                if ((48 + 24) < sprite.getX() && sprite.getX() < (192 - 24) && sprite.getY() == 48) {
                    if (holeFlag1 == 0) {
                        holeFlag1 = 1;
                        System.out.println(holeFlag1);
                        frogs.add(Tile.createFrogTile(120, 48));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if ((240 + 24) < sprite.getX() && sprite.getX() < (384 - 24) && sprite.getY() == 48) {
                    if (holeFlag2 == 0) {
                        holeFlag2 = 1;
                        frogs.add(Tile.createFrogTile(312, 48));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if ((432 + 24) < sprite.getX() && sprite.getX() < (576 - 24) && sprite.getY() == 48) {
                    if (holeFlag3 == 0) {
                        holeFlag3 = 1;
                        frogs.add(Tile.createFrogTile(504, 48));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if ((624 + 24) < sprite.getX() && sprite.getX() < (768 - 24) && sprite.getY() == 48) {
                    if (holeFlag4 == 0) {
                        holeFlag4 = 1;
                        frogs.add(Tile.createFrogTile(696, 48));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
                if ((816 + 24) < sprite.getX() && sprite.getX() < (960 - 24) && sprite.getY() == 48) {
                    if (holeFlag5 == 0) {
                        holeFlag5 = 1;
                        frogs.add(Tile.createFrogTile(888, 48));
                    } else {
                        Player.lifeNum -= 1;
                    }
                    sprite.setX(App.SCREEN_WIDTH / 2);
                    sprite.setY(App.SCREEN_HEIGHT - TILE_SIZE);
                }
            }
        }

        for (Sprite sprite : sprites) {
            boolean flag = false;
            for (Sprite extra : extraLife) {
                if (sprite instanceof Player && sprite.collides(extra)) {
                    Player.lifeNum += 1;
                    flag = true;
                }
            }
            if (flag) {
                extraLife.clear();
            }
        }

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

//        if (frogs.size() == 5 && noNextLevel == 1){
//            System.exit(0);
//        }
//
//        if (frogs.size() == 5) {
//            noNextLevel = 1;
//            reset();
//        }

    }

    private void loadSprite(int levelNum) throws IOException {
        File file = new File(LEVEL0_PATH);
        if (levelNum == 1) {
            file = new File(LEVEL1_PATH);
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String text;
        while ((text = br.readLine()) != null) {
            textList = text.split(",");
            spriteName = textList[0];
            xValue = Float.parseFloat(textList[1]);
            yValue = Float.parseFloat(textList[2]);
            if (textList.length == 4) {
                direction = Boolean.parseBoolean(textList[3]);
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
//            if (spriteName.equals("bus")) {
//                sprites.add(new Bus(xValue, yValue, direction));
//            }
//            if (spriteName.equals("racecar")) {
//                sprites.add(new Racecar(xValue, yValue, direction));
//            }
//            if (spriteName.equals("bike")) {
//                sprites.add(new Bike(xValue, yValue, direction));
//            }
//            if (spriteName.equals("bulldozer")) {
//                sprites.add(new Bulldozer(xValue, yValue, direction));
//            }
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

    private void putExtraLife() {
        Random r = new Random();
        int index;
        for (Sprite logtype : sprites) {
            if (logtype instanceof Log || logtype instanceof LongLog) {
                logs.add(logtype);
            }
        }
        index = r.nextInt(logs.size());
        Sprite randomLog = logs.get(index);
        if (randomLog instanceof Log) {
            extraLife.add(new ExtraLife(randomLog.getX(), randomLog.getY(), randomLog.getMoveRight(),
                    randomLog.getSpeed(), 3));
        } else if (randomLog instanceof LongLog) {
            extraLife.add(new ExtraLife(randomLog.getX(), randomLog.getY(), randomLog.getMoveRight(),
                    randomLog.getSpeed(), 5));
        }
        //System.out.println(randomLog.getMoveRight());

    }


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
