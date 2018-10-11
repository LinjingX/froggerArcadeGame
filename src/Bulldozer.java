import org.newdawn.slick.Input;

public class Bulldozer extends MovingObject {
    private static final String ASSET_PATH = "assets/bulldozer.png";
    public static final float SPEED = 0.05f;

    public Bulldozer(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.BULLDOZER }, moveRight);
    }

    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
                || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
            setX(getInitialX());
        }
    }
    @Override
    public float getSpeed(){
        return SPEED;
    }
    @Override
    public boolean getMoveRight() {
        return this.moveRight;
    }



}
