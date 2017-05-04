package tank_revolution.terrain;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by antonhagermalm on 2017-05-04.
 */
public interface ITerrain {
    void create();
    World getWorld();

}
