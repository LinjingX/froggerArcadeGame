import org.newdawn.slick.Input;

public class Bike extends MovingObject{
    private static final String ASSET_PATH = "assets/bike.png";
    private static final float SPEED = 0.2f;

    public Bike(float x, float y, boolean moveRight) {
        super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD }, moveRight);
    }

    @Override
    public void update(Input input, int delta) {
        move(SPEED * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
                || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
            setX(super.getInitialX());
        }

        if(getX() <= 24){
            moveRight = !moveRight;
        }

        if(getX() >= 1000){
            moveRight = !moveRight;
        }
    }
}
