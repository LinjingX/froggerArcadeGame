public class MovingObject extends Sprite {

    /**
     * get the x value where the object should be reset to
     * @return the initial x value
     */
    public float getInitialX() {
        return moveRight ? -World.TILE_SIZE / 2
                : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
    }

    /**
     * constructor
     * @param ASSET_PATH the image path
     * @param x x value
     * @param y y value
     * @param tags the label of the class
     * @param moveRight direction
     */
    public MovingObject(String ASSET_PATH, float x, float y, String[] tags, boolean moveRight) {
        super(ASSET_PATH, x, y, tags);

        this.moveRight = moveRight;
    }

}

