import org.newdawn.slick.Input;

import java.util.Calendar;
import java.util.Date;

public class ExtraLife extends MovingObject {
    private static final String ASSET_PATH = "assets/extralife.png";
    private static float speed = 0.0f;
    private static int logLength;
    private static Date extraLifeAppearTime;
    private static Date extraLifeDisappearTime;
    private static boolean extraLifeBeginAppear = false;
    int count = 0;

    public ExtraLife(float x, float y, boolean moveRight, float speed, int logLength) {
        super(ASSET_PATH, x, y, new String[] { Sprite.EXTRA }, moveRight);
        this.speed = speed;
        this.logLength = logLength;
    }

    public final float getInitialX() {
        return moveRight ? -(World.TILE_SIZE * logLength)/ 2
                : App.SCREEN_WIDTH + (World.TILE_SIZE * logLength) / 2;
    }

    @Override
    public void update(Input input, int delta) {
        Calendar cal = Calendar.getInstance();
        if (extraLifeDisappearTime == null) {
            extraLifeDisappearTime = cal.getTime();
        }

        long diff = World.currentTime.getTime() - World.startTime.getTime();
        long diffSeconds = diff / 1000 % 60;

        long diff3 = World.currentTime.getTime() - extraLifeDisappearTime.getTime();
        long diff3Seconds = diff3 / 1000 % 60;

//        if (diff3Seconds >= World.randomTime && extraLifeBeginAppear == false) {
//            System.out.println("Extra live appear");
//            extraLifeBeginAppear = true;
//            extraLifeAppearTime = cal.getTime();
//            World.startTime = cal.getTime();
//        }

        if (extraLifeBeginAppear == false) {
            if (diffSeconds >= World.randomTime) {
                System.out.println("Extra live appear");
                extraLifeBeginAppear = true;
                extraLifeAppearTime = cal.getTime();
                World.startTime = cal.getTime();
            }
        } else {
            long diff2 = World.currentTime.getTime() - extraLifeAppearTime.getTime();
            long diff2Seconds = diff2 / 1000 % 60;

            if (diff2Seconds >= 14) {
                System.out.println("Extra live disappear");
                extraLifeBeginAppear = false;
                extraLifeDisappearTime = cal.getTime();
            } else if (diffSeconds >= 2) {
                System.out.println("Move left or right");
                if(logLength == 3){
                    if(count == 0){
                        setX(getX()+World.TILE_SIZE);
                    }else if(1<= count && count <= 3){
                        setX(getX()-World.TILE_SIZE);
                    }else if(4<= count && count <= 6){
                        setX(getX()+World.TILE_SIZE);
                    }
                }else if(logLength == 5){
                    if(0<=count && count<=1) {
                        setX(getX() + World.TILE_SIZE);
                    }else if(2<=count && count<=6){
                        setX(getX() - World.TILE_SIZE);
                    }
                }
                count += 1;
                System.out.println(count);
                World.startTime = cal.getTime();
            }
        }

        move(speed * delta * (moveRight ? 1 : -1), 0);

        // check if the vehicle has moved off the screen
        if (getX() > App.SCREEN_WIDTH + (World.TILE_SIZE * logLength)/ 2 || getX() < -(World.TILE_SIZE * logLength)/ 2
                || getY() > App.SCREEN_HEIGHT + (World.TILE_SIZE) / 2 || getY() < -(World.TILE_SIZE) / 2) {
            setX(getInitialX());
        }
    }
}
