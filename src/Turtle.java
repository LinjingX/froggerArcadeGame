import org.lwjgl.Sys;
import org.newdawn.slick.Input;

import java.util.Calendar;
import java.util.Date;

public class Turtle extends MovingObject {
    private static final String ASSET_PATH = "assets/turtles.png";
    private static final float SPEED = 0.085f;
    private static int sec9 = 9;
    private static final int sec2 = 2;
    private static Date startTime;
    private static Date currentTime;
    private static Date hideTime;
    private static boolean appear = true;
    private static boolean firstRun = true;

    public Turtle(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[]{Sprite.RIDEABLE}, moveRight);
        Calendar cal = Calendar.getInstance();
        startTime = cal.getTime();
        //System.out.println(startTime);
    }

    public final float getInitialX() {
        return moveRight ? -(World.TILE_SIZE * 3) / 2
                : App.SCREEN_WIDTH + (World.TILE_SIZE * 3) / 2;
    }

    @Override
    public void update(Input input, int delta) {
        Calendar cal = Calendar.getInstance();
        currentTime = cal.getTime();
        if (hideTime == null) {
            hideTime = cal.getTime();
        }
        long diff = currentTime.getTime() - startTime.getTime();
        long diffSeconds = diff / 1000 % 60;

        if (firstRun == true) {
            firstRun = false;
            sec9 = 7;
        }

        if (diffSeconds >= sec9) {
            sec9 = 9;
            startTime = cal.getTime();
            hideTime = cal.getTime();
            appear = false;
            //System.out.println("Hide");
        }

        long diff2 = currentTime.getTime() - hideTime.getTime();
        long diffSeconds2 = diff2 / 1000 % 60;

        if (diffSeconds2 >= sec2) {
            appear = true;
            //System.out.println("Appear");
        }

        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the turtle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + (World.TILE_SIZE * 3) / 2 || getX() < -(World.TILE_SIZE * 3) / 2
                || getY() > App.SCREEN_HEIGHT + (World.TILE_SIZE * 3) / 2 || getY() < -(World.TILE_SIZE * 3) / 2) {
            setX(getInitialX());
        }
    }

    public void render() {
        if (appear == true) {
            getImage().drawCentered(getX(), getY());
        }
    }

    @Override
    public float getSpeed() {
        return SPEED;
    }

    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }

    public boolean getAppear() {
        return this.appear;
    }
}
