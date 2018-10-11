import org.newdawn.slick.Input;

public class Bus extends MovingObject {
    private static final String ASSET_PATH = "assets/bus.png";
    private static final float SPEED = 0.15f;

    //private boolean moveRight;

    /*
    private final float getInitialX() {
        return moveRight ? -World.TILE_SIZE / 2
                : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
    }*/

    public Bus(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD }, moveRight);

        //this.moveRight = moveRight;
    }

    @Override
    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
                || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
            setX(getInitialX());
        }
    }


}
