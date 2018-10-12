import org.newdawn.slick.Input;

import java.util.Calendar;
import java.util.Date;

/**
 * represent the extra life character in the game
 */
public class ExtraLife extends MovingObject {
    private static final String ASSET_PATH = "assets/extralife.png";
    private static final int MILISECONDS_PER_SECOND = 1000;
    private static final int SECONDS_60 = 60;
    private static final int SECONDS_14 = 14;
    private static final int SECONDS_2 = 2;
    private static final int LOG_SIZE_3 = 3;
    private static final int LOG_SIZE_5 = 5;
    private static Date extraLifeAppearTime;
    /**
     * the sharp time when the extra life disappear
     */
    public static Date extraLifeDisappearTime;
    /**
     * whether if extra life is currently on the screen
     */
    public static boolean extraLifeBeginAppear = false;
    private float speed;
    private int logLength;
    private int count = 0;

    /**
     * constructor
     * @param x x value of the log
     * @param y y value of the log
     * @param moveRight direction of the log
     * @param speed speed of the log
     * @param logLength log length of the log
     */
    public ExtraLife(float x, float y, boolean moveRight, float speed, int logLength) {
        super(ASSET_PATH, x, y, new String[]{Sprite.EXTRA}, moveRight);
        this.speed = speed;
        this.logLength = logLength;
    }
    /**
     * get the x value where the extra life should be reset to
     * @return the initial x value
     */
    public final float getInitialX() {
        return moveRight ? -(World.TILE_SIZE * logLength) / 2
                : App.SCREEN_WIDTH + (World.TILE_SIZE * logLength) / 2;
    }

    /**
     * update the changes made to the extra life
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    @Override
    public void update(Input input, int delta) {
        Calendar cal = Calendar.getInstance();
        // if extra life haven't appear and disappear before, set the disappear time to be the current time, to avoid
        // the null pointer problem
        if (extraLifeDisappearTime == null) {
            extraLifeDisappearTime = cal.getTime();
        }

        long disappear_time = World.currentTime.getTime() - World.startTime.getTime();
        long disappear_time_seconds = disappear_time / MILISECONDS_PER_SECOND % SECONDS_60;

        // extra life is not on screen
        if (!extraLifeBeginAppear) {
            // and it disappears for a time that is longer than the random time
            if (disappear_time_seconds >= World.randomTime) {
                // new extra life should be placed
                World.putExtraLife();
                extraLifeBeginAppear = true;
                extraLifeAppearTime = cal.getTime();
                World.startTime = cal.getTime();
            }
        } else {
            long appear_time = World.currentTime.getTime() - extraLifeAppearTime.getTime();
            long appear_time_seconds = appear_time / MILISECONDS_PER_SECOND % SECONDS_60;

            // extra life appears for more than 14 seconds, it should disappear
            if (appear_time_seconds >= SECONDS_14) {
                extraLifeBeginAppear = false;
                extraLifeDisappearTime = cal.getTime();
            } else if (appear_time_seconds >= SECONDS_2) {
                // jump for every 2 seconds
                if (logLength == LOG_SIZE_3) {
                    if (count == 0) {
                        setX(getX() + World.TILE_SIZE);
                    } else if (1 <= count && count <= 2) {
                        setX(getX() - World.TILE_SIZE);
                    } else if (3 <= count && count <= 4) {
                        setX(getX() + World.TILE_SIZE);
                    } else if (5 <= count && count <= 6){
                        setX(getX() - World.TILE_SIZE);
                    }
                } else if (logLength == LOG_SIZE_5) {
                    if (0 <= count && count <= 1) {
                        setX(getX() + World.TILE_SIZE);
                    } else if (2 <= count && count <= 6) {
                        setX(getX() - World.TILE_SIZE);
                    }
                }
                count += 1;
                System.out.println(count);
                // reset the start time of the period to be the current time
                World.startTime = cal.getTime();
            }
        }

        move(speed * delta * (moveRight ? 1 : -1), 0);

        // check if the extra life has moved off the screen
        if (getX() > App.SCREEN_WIDTH + (World.TILE_SIZE * logLength) / 2 || getX() < -(World.TILE_SIZE * logLength) / 2
                || getY() > App.SCREEN_HEIGHT + (World.TILE_SIZE) / 2 || getY() < -(World.TILE_SIZE) / 2) {
            setX(getInitialX());
        }
    }

    /**
     * render the extra life if it should appear and vise versa
     */
    public void render() {
        if (extraLifeBeginAppear) {
            getImage().drawCentered(getX(), getY());
        }
    }
}
