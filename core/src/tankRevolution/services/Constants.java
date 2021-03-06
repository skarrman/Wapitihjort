package tankRevolution.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Static class holding information about constant values used in the game.
 */
public class Constants {
    private static final float mapWidth = 150f;
    private static final float gravity = -10f;
    /** The number of edges in the approximated circle of the explosions */
    private static final int explosionSegments = 60;
    private static final float shootOffsetTank = 3f;
    private static final float tankStartPositionY = 100f;

    public static int getExplosionSegments() {
        return explosionSegments;
    }
    public static float getMapWidth() {
        return mapWidth;
    }
    public static float getGravity(){
        return gravity*2;
    }
    public static float getShootOffsetTank(){
        return shootOffsetTank;
    }

    public static float pixelsPerMeter(){
        return Gdx.graphics.getWidth() / mapWidth;
    }
    public static float metersPerPixel(){
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

    public static float getTankStartPositionY(){
        return tankStartPositionY;
    }

    public static float getPickerHeight(){
        return Gdx.graphics.getHeight()/10;
    }

    public static float getNumberOfPlayerWidth(){
        return 6 * Gdx.graphics.getWidth()/8;
    }

    public static float getNpcOrPlayerWidth(){
        return 4 * Gdx.graphics.getWidth()/24;
    }

    public static float getNpcDifficultyWidth(){
        return getNpcOrPlayerWidth()*2;
    }

    public static float getPickerY(int i){
       return  (10 - i * 2)*Gdx.graphics.getHeight()/16;
    }

    public static Vector2 getNumberOfPlayersPos(){
        return new Vector2(Gdx.graphics.getWidth()/2 - getNumberOfPlayerWidth()/2 ,12 * Gdx.graphics.getHeight()/16);
    }

    public static float getNpcOrPlayerX(){
        return Gdx.graphics.getWidth()/2 - getNpcOrPlayerWidth();
    }

    public static float getNpcDifficultyX(){
        return getNumberOfPlayersPos().x+getNumberOfPlayerWidth()-getNpcDifficultyWidth();
    }

    public static float getStartCustomGameWidth(){
        return getNumberOfPlayerWidth();
    }

    public static float getStartCustomGameHeight(){
        return getPickerHeight()*1.75f;
    }

    public static Vector2 getStartCustomGamePos(){
        return new Vector2(Gdx.graphics.getWidth()/2 - getStartCustomGameWidth()/2, Gdx.graphics.getHeight()/16);
    }

    public static Vector2 getLeftMapArrowPos(){
        return new Vector2(2 * Gdx.graphics.getWidth()/8,14 * Gdx.graphics.getHeight()/16);
    }

    public static Vector2 getRightMapArrowPos(){
        return new Vector2(6 * Gdx.graphics.getWidth()/8 - getWeaponArrowDimension(),
                14 * Gdx.graphics.getHeight()/16);
    }
}