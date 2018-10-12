import org.newdawn.slick.Input;

/**
 * represent the bike , which will hit the player
 */
public class Bike extends MovingObject{
    private static final String ASSET_PATH = "assets/bike.png";
    private static final float SPEED = 0.2f;
    private static final float CHANGE_DIRECTION_1 = 24;
    private static final float CHANGE_DIRECTION_2 = 1000;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     * @param moveRight direction
     */
    public Bike(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD }, moveRight);
    }

    /**
     * update the changes made to the bike
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

        if(getX() <= CHANGE_DIRECTION_1){
            moveRight = !moveRight;
        }

        if(getX() >= CHANGE_DIRECTION_2){
            moveRight = !moveRight;
        }
    }
}
