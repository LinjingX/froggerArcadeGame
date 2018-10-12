import org.newdawn.slick.Input;

/**
 * represent the bus in the game, it can hit the player
 */
public class Bus extends Vehicle {
    private static final String ASSET_PATH = "assets/bus.png";
    private static final float SPEED = 0.15f;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     * @param moveRight direction
     */
    public Bus(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD }, moveRight);
    }

    /**
     * get the speed of the bus
     * @return the speed of the bus
     */
    @Override
    public float getSpeed(){
        return this.SPEED;
    }

    /**
     * get the direction of the bus
     * @return the direction of the bus
     */
    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }


}
