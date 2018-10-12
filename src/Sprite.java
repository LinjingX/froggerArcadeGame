import utilities.BoundingBox;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * represent the sprite in the game
 */
public abstract class Sprite {
	// this is a defined constant to avoid typos
	/**
	 * the tag for object that can kill the player
	 */
	public final static String HAZARD = "hazard";
	/**
	 * the tag for object that player cannot move into
	 */
	public final static String SOLID = "solid";
	/**
	 * the tag for object that player can stand onto
	 */
	public final static String RIDEABLE = "rideable";
	/**
	 * the tag for bulldozer
	 */
	public final static String BULLDOZER = "bulldozer";
	/**
	 * the tag for extra life
	 */
	public final static String EXTRA = "extralife";
	/**
	 * the speed of the sprite
	 */
	public static final float SPEED = 0.0f;
	/**
	 * the direction of the sprite
	 */
	public boolean moveRight;
	
	private BoundingBox bounds;
	private Image image;
	private float x;
	private float y;
	private String[] tags;

	/**
	 * constructor
	 * @param imageSrc the image source
	 * @param x x axis value
	 * @param y xy axis value
	 */
	public Sprite(String imageSrc, float x, float y) {
		setupSprite(imageSrc, x, y);
	}
	/**
	 * constructor
	 * @param imageSrc the image source
	 * @param x x axis value
	 * @param y xy axis value
	 * @param tags the tags for this sprite
	 */
	public Sprite(String imageSrc, float x, float y, String[] tags) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
	}
	
	private void setupSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		
		bounds = new BoundingBox(image, (int)x, (int)y);
		
		tags = new String[0];		
	}

	/**
	 * Sets the x position of the sprite.
	 * @param x	 the target x position
	 */
	public final void setX(float x) { this.x = x; bounds.setX((int)x); }
	/**
	 * Sets the y position of the sprite.
	 * @param y	 the target y position
	 */
	public final void setY(float y) { this.y = y; bounds.setY((int)y); }
	/**
	 * Accesses the x position of the sprite.
	 * @return	the x position of the sprite
	 */
	public final float getX() { return x; }
	/**
	 * Accesses the y position of the sprite.
	 * @return	the y position of the sprite
	 */
	public final float getY() { return y; }

	public final Image getImage() {
		return image;
	}

	/**
	 * move the sprite
	 * @param dx change in x value
	 * @param dy change in y value
	 */
	public final void move(float dx, float dy) {
		setX(x + dx);
		setY(y + dy);
	}

	/**
	 * check if the player is on screen or not
	 * @param x x value
	 * @param y y value
	 * @return false if x and y is out of range, true is x and y is in range
	 */
	public final boolean onScreen(float x, float y) {
		return !(x + World.TILE_SIZE / 2 > App.SCREEN_WIDTH || x - World.TILE_SIZE / 2 < 0
			 || y + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT || y - World.TILE_SIZE / 2 < 0);
	}
	
	public final boolean onScreen() {
		return onScreen(getX(), getY());
	}

	/**
	 * check if the sprite collides with another
	 * @param other the sprite that collides with
	 * @return true if collides, false if not collides
	 */
	public final boolean collides(Sprite other) {
		return bounds.intersects(other.bounds);
	}

	/**
	 * update the changes made to the sprite
	 * @param input the key board input
	 * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
	 */
	public void update(Input input, int delta) { }

	/**
	 * the actions that should be done if the sprite collides with others
	 * @param other the sprite that collides with this sprite
	 * @param delta a constant used to make sure object move at the same speed no matter how fast the game is running
	 */
	public void onCollision(Sprite other, int delta) { }

	/**
	 * draw the image
	 */
	public void render() {
		image.drawCentered(x, y);
	}

	/**
	 * check if the sprite has specific tags
	 * @param tag the tag that is going to be checked
	 * @return true if the sprite has this tag
	 */
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get the speed of sprite
	 * @return the speed of sprite
	 */
	public float getSpeed(){
	    return SPEED;
    }

	/**
	 * get the direction of sprite
	 * @return the direction of sprite
	 */
    public boolean getMoveRight() {
        return this.moveRight;
    }

}
