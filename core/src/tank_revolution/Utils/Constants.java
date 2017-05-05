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

    public static Vector2 getNewGameButtonPosition(){
        return new Vector2(Gdx.graphics.getWidth()/5f, 3*Gdx.graphics.getHeight()/5f);
    }

    public static float getNewGameButtonDimention(){
        return Gdx.graphics.getWidth()/5f;
    }

}
