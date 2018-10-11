import org.newdawn.slick.Input;

public class Vehicle extends Sprite {
	//private static String ASSET_PATH;
	//private static final float SPEED = 0.15f;
	
	public boolean moveRight;
	//private static String[] tags;
	
	public final float getInitialX() {
		return moveRight ? -World.TILE_SIZE / 2
						 : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
	}

	public Vehicle(String ASSET_PATH, float x, float y, String[] tags, boolean moveRight) {
		super(ASSET_PATH, x, y, tags);
		
		this.moveRight = moveRight;
	}
	/*
	@Override
	public void update(Input input, int delta) {
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
		
		// check if the vehicle has moved off the screen
		if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE / 2 || getX() < -World.TILE_SIZE / 2
		 || getY() > App.SCREEN_HEIGHT + World.TILE_SIZE / 2 || getY() < -World.TILE_SIZE / 2) {
			setX(getInitialX());
		}
	}*/
}
