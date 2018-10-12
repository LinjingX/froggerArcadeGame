import org.newdawn.slick.Input;

public class Vehicle extends MovingObject {

    /**
     * constructor
     * @param ASSET_PATH image path
     * @param x x value
     * @param y y value
     * @param tags the label of the class
     * @param moveRight direction
     */
    public Vehicle(String ASSET_PATH, float x, float y, String[] tags, boolean moveRight) {
        super(ASSET_PATH, x, y, tags, moveRight);
    }

    /**
     * update the changes made to the vehicle
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    public void update(Input input, int delta) {
        move(getSpeed() * delta * (moveRight ? 1 : -1), 0);

        // check if the bulldozer has moved off the screen
        if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
                || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
            setX(getInitialX());
        }
    }
}
