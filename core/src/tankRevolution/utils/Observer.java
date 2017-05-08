package tankRevolution.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by simonkarrman on 2017-04-05.
 */
public interface Observer {

    void actOnChange(Vector2 position, int value);

}
