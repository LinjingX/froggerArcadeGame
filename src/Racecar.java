/**
 * represent the racecar in the game, it can hit the player
 */
public class Racecar extends Vehicle{
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
     * get the speed of the racecar
     * @return the speed of the racecar
     */
    @Override
    public float getSpeed(){
        return this.SPEED;
    }

    /**
     * get the direction of the racecar
     * @return the direction of the racecar
     */
    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }

}
