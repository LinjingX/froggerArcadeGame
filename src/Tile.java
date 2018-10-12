public class Tile extends Sprite {
	private static final String GRASS_PATH = "assets/grass.png";
	private static final String WATER_PATH = "assets/water.png";
	private static final String TREE_PATH = "assets/tree.png";
    private static final String LIFE_PATH = "assets/lives.png";
    private static final String FROG_PATH = "assets/frog.png";

	/**
	 * create a grass tile
	 * @param x x value
	 * @param y y value
	 * @return the grass tile created
	 */
	public static Tile createGrassTile(float x, float y) {
		return new Tile(GRASS_PATH, x, y);
	}
	/**
	 * create a water tile
	 * @param x x value
	 * @param y y value
	 * @return the water tile created
	 */
	public static Tile createWaterTile(float x, float y) {
		return new Tile(WATER_PATH, x, y, new String[] { Sprite.HAZARD });
	}
	/**
	 * create a tree tile
	 * @param x x value
	 * @param y y value
	 * @return the tree tile created
	 */
	public static Tile createTreeTile(float x, float y){
	    return new Tile(TREE_PATH, x, y, new String[] { Sprite.SOLID });
    }
	/**
	 * create a life tile
	 * @param x x value
	 * @param y y value
	 * @return the life tile created
	 */
    public static Tile createLifeTile(float x, float y){
        return new Tile(LIFE_PATH, x, y);
    }
	/**
	 * create a frog tile
	 * @param x x value
	 * @param y y value
	 * @return the frog tile created
	 */
    public static Tile createFrogTile(float x, float y){
        return new Tile(FROG_PATH, x, y);
    }
	private Tile(String imageSrc, float x, float y) {		
		super(imageSrc, x, y);
	}
	private Tile(String imageSrc, float x, float y, String[] tags) {		
		super(imageSrc, x, y, tags);
	}
}