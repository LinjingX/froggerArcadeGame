import org.newdawn.slick.Input;

/**
 * represent the racecar in the game, it can hit the player
 */
public class Racecar extends MovingObject{
    private static final String ASSET_PATH = "assets/racecar.png";
    private static final float SPEED = 0.5f;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     * @param moveRight direction
     */
    public Racecar(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD }, moveRight);
    }


    /**
     * update the changes made to the racecar
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    @Override
    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
                || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
            setX(super.getInitialX());
        }
    }
}
