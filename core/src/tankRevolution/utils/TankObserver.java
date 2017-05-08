package tankRevolution.utils;

import tankRevolution.model.Tank;

/**
 * Created by antonhagermalm on 2017-04-07.
 */
public interface TankObserver {
    void actOnDeath(Tank tank);
}
