import org.newdawn.slick.Input;

/**
 * represent the long log in the game, it is rideable and protect the player from water
 */
public class LongLog extends MovingObject {
    private static final String ASSET_PATH = "assets/longLog.png";
    private static final float SPEED = 0.07f;
    private static final int LENGTH = 5;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     * @param moveRight direction
     */
    public LongLog(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.RIDEABLE }, moveRight);
    }

    /**
     * get the x value where the long log should be reset to
     * @return the initial x value
     */
    public final float getInitialX() {
        return moveRight ? -(World.TILE_SIZE * LENGTH)/ 2
                : App.SCREEN_WIDTH + (World.TILE_SIZE * LENGTH / 2);
    }

    /**
     * update the changes made to the long log
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + (World.TILE_SIZE * LENGTH) / 2 || getX() < -(World.TILE_SIZE * LENGTH) / 2
                || getY() > App.SCREEN_HEIGHT + (World.TILE_SIZE) / 2 || getY() < -(World.TILE_SIZE) / 2) {
            setX(getInitialX());
        }
    }

    /**
     * get the speed of long log
     * @return the speed of long log
     */
    @Override
    public float getSpeed(){
        return SPEED;
    }

    /**
     * get the direction of long log
     * @return the direction of long log
     */
    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }

    /**
     * get the length of the long log
     * @return the length of the long log
     */
    public int getLength(){
        return this.LENGTH;
    }
}
