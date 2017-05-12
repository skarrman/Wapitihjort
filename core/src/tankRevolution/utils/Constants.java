package tankRevolution.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by antonhagermalm on 2017-04-27.
 */
public class Constants {
    private static final float mapWidth = 150f;
    private static final float gravity = -10f;
    /** The number of edges in the approximated circle of the explosions */
    private static final int explosionSegments = 60;
    private static final float shootOffsetTank = 3f;

    public static int getExplosionSegments() {
        return explosionSegments;
    }
    public static float getMapWidth() {
        return mapWidth;
    }
    public static float getGravity(){
        return gravity;
    }
    public static float pixelsPerMeter(){
        return Gdx.graphics.getWidth() / mapWidth;
    }
    public static float getShootOffsetTank(){
        return shootOffsetTank;
    }

    public static float metersPerPixel(){
        //System.out.println(mapWidth / Gdx.graphics.getWidth());
        return mapWidth / Gdx.graphics.getWidth();
    }

    public static float getStartMenuButtonDimension(){
        return Gdx.graphics.getWidth()/5f;
    }
    public static float getMoveButtonWidth(){
        return Gdx.graphics.getWidth()/10;
    }
    public static float getMoveButtonHeight(){
        return Gdx.graphics.getHeight()-getSettingsButtonDimension();
    }

    public static Vector2 getQuickStartButtonPosition(){
        float verticalDistance = (Gdx.graphics.getHeight() - 2 * getStartMenuButtonDimension()) / 3;
        float y = 2 * verticalDistance + getStartMenuButtonDimension();
        return new Vector2(Gdx.graphics.getWidth()/5f, y);
    }

    public static Vector2 getWorldButtonPosition(){
        float verticalDistance = (Gdx.graphics.getHeight() - 2 * getStartMenuButtonDimension()) / 3;
        float y = 2 * verticalDistance + getStartMenuButtonDimension();
        return new Vector2(3*Gdx.graphics.getWidth()/5f, y);
    }

    public static Vector2 getCustomStartButtonPosition(){
        float y = (Gdx.graphics.getHeight() - 2 * getStartMenuButtonDimension()) / 3;
        return new Vector2(Gdx.graphics.getWidth()/5f, y);
    }

    public static Vector2 getHighScoreButtonPosition(){
        float y = (Gdx.graphics.getHeight() - 2 * getStartMenuButtonDimension()) / 3;
        return new Vector2(3*Gdx.graphics.getWidth()/5f, y);
    }

    public static float getSettingsButtonDimension(){
        return Gdx.graphics.getWidth()/10;
    }

    public static Vector2 getSettingsButtonPosition(){
        return new Vector2(Gdx.graphics.getWidth()-getSettingsButtonDimension(), Gdx.graphics.getHeight()-getSettingsButtonDimension());
    }

    public static Vector2 getLeftMoveButtonPosition(){
        return new Vector2(0, 0);
    }

    public static Vector2 getRightMoveButtonPosition(){
        return new Vector2(Gdx.graphics.getWidth() - getMoveButtonWidth(), 0);
    }

    public static float getPauseMenuButtonWidth(){
        return Gdx.graphics.getWidth()*0.7f;
    }

    public static float getPauseMenuButtonHeight(){
        return Gdx.graphics.getHeight()/7;
    }

    private static float getPauseMenuXValue(){
        return (Gdx.graphics.getWidth() / 2) - (getPauseMenuButtonWidth() / 2);
    }

    public static Vector2 getResumeButtonPosition(){
        return new Vector2(getPauseMenuXValue(), 5 * getPauseMenuButtonHeight());
    }

    public static Vector2 getRestartButtonPosition(){
        return new Vector2(getPauseMenuXValue(), 3 * getPauseMenuButtonHeight());
    }

    public static Vector2 getToMenuPosition(){
        return new Vector2(getPauseMenuXValue(),  getPauseMenuButtonHeight());
    }

    public static float getWeaponArrowDimension(){
        return Gdx.graphics.getWidth()/16;
    }

    public static  Vector2 getRightWeaponPosition(){
        return new Vector2(5 * Gdx.graphics.getWidth()/8 - getWeaponArrowDimension(),
                14 * Gdx.graphics.getHeight()/16);
    }

    public static  Vector2 getLeftWeaponPosition(){
     return new Vector2(3 * Gdx.graphics.getWidth()/8,14 * Gdx.graphics.getHeight()/16);
    }

}