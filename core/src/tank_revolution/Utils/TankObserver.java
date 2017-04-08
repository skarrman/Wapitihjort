package tank_revolution.Utils;

import com.badlogic.gdx.math.Vector2;
import tank_revolution.model.Tank;

/**
 * Created by antonhagermalm on 2017-04-07.
 */
public interface TankObserver {
    void actOnDeath(Tank tank);
}
