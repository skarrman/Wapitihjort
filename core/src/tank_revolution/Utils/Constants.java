package tank_revolution.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by antonhagermalm on 2017-04-27.
 */
public class
Constants {
    private static final float mapWidth = 50f;

    public static float getMapWidth() {
        return mapWidth;
    }

    public static float getStartMenuButtonDimension(){
        return Gdx.graphics.getWidth()/5f;
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





}
