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
        return new Vector2(Gdx.graphics.getWidth()/5f, 3*Gdx.graphics.getHeight()/5f);
    }

    public static Vector2 getWorldButtonPosition(){
        return new Vector2(3*Gdx.graphics.getWidth()/5f, 3*Gdx.graphics.getHeight()/5f);
    }

    public static Vector2 getCustomStartButtonPosition(){
        return new Vector2(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/5f);
    }

    public static Vector2 getHighScoreButtonPosition(){
        return new Vector2(3*Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/5f);
    }





}
