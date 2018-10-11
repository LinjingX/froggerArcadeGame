public class MovingObject extends Sprite {
    //private static String ASSET_PATH;
    //private static final float SPEED = 0.15f;
    //private static String[] tags;
    //private static final float SPEED = 0.0f;
    //public boolean moveRight;

    public float getInitialX() {
        return moveRight ? -World.TILE_SIZE / 2
                : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
    }

    public MovingObject(String ASSET_PATH, float x, float y, String[] tags, boolean moveRight) {
        super(ASSET_PATH, x, y, tags);

        this.moveRight = moveRight;
    }

}

