import org.lwjgl.Sys;
import org.newdawn.slick.Input;

public class Player extends Sprite {
	private static final String ASSET_PATH = "assets/frog.png";
	private static float speed;
	private static boolean moveRight;
	public boolean ride = false;
	public static int lifeNum = 3;
	
	public Player(float x, float y) {
		super(ASSET_PATH, x, y);
	}

	@Override
	public void update(Input input, int delta) {
	    //canMove = true;
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
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 	> App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}

		move(dx, dy);

        for(Sprite sprite : World.sprites){
            if(sprite.hasTag(Sprite.SOLID)) {
                if(this.collides(sprite)){
                    System.out.println("Solid tree");
                    move(-dx, -dy);
                }
            }
        }
	}
	
	@Override
	public void onCollision(Sprite other, int delta) {

        if (other.hasTag(Sprite.BULLDOZER)){
            setX(other.getX() + World.TILE_SIZE);
            setY(other.getY());
//            speed = other.getSpeed();
//            moveRight = false;
//            System.out.println(speed);
//            System.out.println(moveRight);
//            move(speed * delta * (moveRight ? 1 : -1), 0);
        }
        if(other.hasTag(Sprite.RIDEABLE) && getY() == other.getY()){
            speed = other.getSpeed();
            moveRight = other.getMoveRight();
            ride = true;
            if(other instanceof Turtle){
                if(!((Turtle) other).getAppear()){
                    if(lifeNum > 1){
                        lifeNum -= 1;
                        setX(App.SCREEN_WIDTH / 2);
                        setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
                    }else{
                        System.exit(0);
                    }
                }
            }
            move(speed * delta * (moveRight ? 1 : -1), 0);
        }
        if(this.getX() + World.TILE_SIZE / 2 > App.SCREEN_WIDTH ||
                this.getX() - World.TILE_SIZE / 2 < 0) {
            if(lifeNum > 1){
                lifeNum -= 1;
                setX(App.SCREEN_WIDTH / 2);
                setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
            }else{
                System.exit(0);
            }
        }
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
    /*
	public void onCollision(MovingObject other, int delta){
        if (other.hasTag(Sprite.BULLDOZER)){
            setX(other.getX() + World.TILE_SIZE);
            setY(other.getY());
            speed = Bulldozer.SPEED;
            moveRight = other.getMoveRight();

        }
        if(other.hasTag(Sprite.RIDEABLE)){
            speed = other.getSpeed();
            moveRight = other.getMoveRight();
        }
        move(speed * delta * (moveRight ? 1 : -1), 0);
        if(this.getX() + World.TILE_SIZE / 2 > App.SCREEN_WIDTH ||
                this.getX() - World.TILE_SIZE / 2 < 0) {
            System.exit(0);
        }
    }
	/*
	public void update(MovingObject other, int delta){
        if (other.hasTag(Sprite.BULLDOZER)){
            setX(other.getX() + World.TILE_SIZE);
            setY(other.getY());
            speed = Bulldozer.SPEED;
            moveRight = other.getMoveRight();
            move(speed * delta * (moveRight ? 1 : -1), 0);
            //if(this.getX() > App.SCREEN_WIDTH)
        }
    }*/
}
