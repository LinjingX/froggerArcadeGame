import org.newdawn.slick.Input;

/**
 * represent the frog that can be controlled by the player
 */
public class Player extends Sprite {
    private static final String ASSET_PATH = "assets/frog.png";
    private static float speed;
    private static boolean moveRight;
    /**
     * a boolean to check whether the player is on rideable object
     */
    public boolean ride = false;
    /**
     * the life number of player
     */
    public static int lifeNum = 3;

    /**
     * constructor
     * @param x x axis value
     * @param y y axis value
     */
    public Player(float x, float y) {
        super(ASSET_PATH, x, y);
    }

    /**
     * update the changes made to the player's character
     * @param input the key board input
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    @Override
    public void update(Input input, int delta) {
        int dx = 0,
                dy = 0;
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            dx -= World.TILE_SIZE;
            ride = false;
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)) {
            dx += World.TILE_SIZE;
            ride = false;
        }
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            dy += World.TILE_SIZE;
            ride = false;
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            dy -= World.TILE_SIZE;
            ride = false;
        }

        // make sure the frog stays on screen
        if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 > App.SCREEN_WIDTH) {
            dx = 0;
        }
        if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
            dy = 0;
        }

        move(dx, dy);

        for (Sprite sprite : World.sprites) {
            if (sprite.hasTag(Sprite.SOLID)) {
                if (this.collides(sprite)) {
                    // if player collides with a solid object, the x and y will not change
                    move(-dx, -dy);
                }
            }
        }
    }

    /**
     * the actions that should be done if player collides with specific types of sprite
     * @param other the sprite that collides with player
     * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
     */
    @Override
    public void onCollision(Sprite other, int delta) {
        // player collide with bulldozer
        if (other.hasTag(Sprite.BULLDOZER)) {
            setX(other.getX() + World.TILE_SIZE);
            setY(other.getY());
        }

        // player step onto a rideable object
        if (other.hasTag(Sprite.RIDEABLE) && getY() == other.getY()) {
            speed = other.getSpeed();
            moveRight = other.getMoveRight();
            ride = true;
            // if the rideable object is turtle
            if (other instanceof Turtle) {
                // if turtle is currently in the water
                if (!((Turtle) other).isAppear()) {
                    if (lifeNum > 1) {
                        lifeNum -= 1;
                        setX(App.SCREEN_WIDTH / 2);
                        setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
                    } else {
                        System.exit(0);
                    }
                }
            }
            // move with the rideable object
            move(speed * delta * (moveRight ? 1 : -1), 0);
        }

        // player is colliding with other sprite and is pushed off screen
        if (this.getX() + World.TILE_SIZE / 2 > App.SCREEN_WIDTH ||
                this.getX() - World.TILE_SIZE / 2 < 0) {
            if (lifeNum > 1) {
                lifeNum -= 1;
                setX(App.SCREEN_WIDTH / 2);
                setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
            } else {
                System.exit(0);
            }
        }

        // player not on rideable object and collides with hazard object
//        if (ride == false) {
//            if (other.hasTag(Sprite.HAZARD)) {
//                if(lifeNum > 1){
//                    lifeNum -= 1;
//                    setX(App.SCREEN_WIDTH / 2);
//                    setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
//                }else{
//                    System.exit(0);
//                }
//            }
//        }
    }
}
