import org.newdawn.slick.Input;

public class Log extends MovingObject {
    private static final String ASSET_PATH = "assets/log.png";
    private static final float SPEED = 0.1f;
    private static final int LENGTH = 3;

    public Log(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.RIDEABLE }, moveRight);
    }

    public final float getInitialX() {
        return moveRight ? -(World.TILE_SIZE * LENGTH)/ 2
                : App.SCREEN_WIDTH + (World.TILE_SIZE * LENGTH) / 2;
    }

    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + (World.TILE_SIZE * LENGTH) / 2 || getX() < -(World.TILE_SIZE * LENGTH) / 2
                || getY() > App.SCREEN_HEIGHT + (World.TILE_SIZE) / 2 || getY() < -(World.TILE_SIZE) / 2) {
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

    public int getLength(){
        return this.LENGTH;
    }
}
