/**
 * represent the bulldozer, it can push the player
 */
public class Bulldozer extends Vehicle {
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
