import org.newdawn.slick.Input;

public class Bulldozer extends MovingObject {
    private static final String ASSET_PATH = "assets/bulldozer.png";
    /**
     * the speed of all bulldozers
     */
    public static final float SPEED = 0.05f;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     * @param moveRight direction
     */
    public Bulldozer(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.BULLDOZER, Sprite.SOLID }, moveRight);
    }

    /**
     * update the changes made to the bulldozer
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the bulldozer has moved off the screen
        if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
                || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
            setX(getInitialX());
        }
    }

    /**
     * get the speed of the bulldozer
     * @return the speed of the bulldozer
     */
    @Override
    public float getSpeed(){
        return SPEED;
    }
    /**
     * get the direction of the bulldozer
     * @return the direction of the bulldozer
     */
    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }



}
