import org.newdawn.slick.Input;

import java.util.Calendar;
import java.util.Date;

/**
 * represent the turtle , which is rideable and protect the player from water
 */
public class Turtle extends MovingObject {
    private static final String ASSET_PATH = "assets/turtles.png";
    private static final float SPEED = 0.085f;
    private static final int SIZE = 3;
    private static final int MILISECONDS_PER_SECOND = 1000;
    private static final int SECONDS_60 = 60;
    private static final int SEVEN_SECONDS = 7;
    private static final int NINE_SECONDS = 9;
    private static int time_interval = 0;
    private static final int two_seconds = 2;
    private static Date startTime;
    private static Date currentTime;
    private static Date hideTime;
    private static boolean appear = true;
    private static boolean firstRun = true;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     * @param moveRight direction
     */
    public Turtle(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[]{Sprite.RIDEABLE}, moveRight);
        // when the turtle is created, get the time for that moment
        Calendar cal = Calendar.getInstance();
        startTime = cal.getTime();
    }

    /**
     * get the x value where the turtle should be reset to
     * @return the initial x value
     */
    public final float getInitialX() {
        return moveRight ? -(World.TILE_SIZE * SIZE) / 2
                : App.SCREEN_WIDTH + (World.TILE_SIZE * SIZE) / 2;
    }

    /**
     * update the changes made to the turtle
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    @Override
    public void update(Input input, int delta) {
        Calendar cal = Calendar.getInstance();
        currentTime = cal.getTime();

        if (hideTime == null) {
            hideTime = cal.getTime();
        }

        // difference between the current time and the time when the turtle is created
        long diff = currentTime.getTime() - startTime.getTime();
        // calculate the seconds from the time difference
        long diffSeconds = diff / MILISECONDS_PER_SECOND % SECONDS_60;

        if (firstRun) {
            firstRun = false;
            time_interval = SEVEN_SECONDS;
        }

        if (diffSeconds >= time_interval) {
            time_interval = NINE_SECONDS;
            startTime = cal.getTime();
            hideTime = cal.getTime();
            appear = false;
        }

        long underwater_time = currentTime.getTime() - hideTime.getTime();
        long underwater_seconds = underwater_time / MILISECONDS_PER_SECOND % SECONDS_60;

        // 2 seconds has passed, the turtle should appear
        if (underwater_seconds >= two_seconds) {
            appear = true;
        }

        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the turtle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + (World.TILE_SIZE * SIZE) / 2 || getX() < -(World.TILE_SIZE * SIZE) / 2
                || getY() > App.SCREEN_HEIGHT + (World.TILE_SIZE) / 2 || getY() < -(World.TILE_SIZE) / 2) {
            setX(getInitialX());
        }
    }

    /**
     * render the turtle if it should appear and vise versa
     */
    public void render() {
        if (appear) {
            getImage().drawCentered(getX(), getY());
        }
    }

    /**
     * get the speed of turtle
     * @return the speed of turtle
     */
    @Override
    public float getSpeed() {
        return SPEED;
    }

    /**
     * get the direction of turtle
     * @return the direction of turtle
     */
    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }

    /**
     * get whether the turtle is currently under water or not
     * @return whether the turtle is currently under water or not
     */
    public boolean isAppear() {
        return this.appear;
    }
}
