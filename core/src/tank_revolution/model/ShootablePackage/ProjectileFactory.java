package tank_revolution.model.ShootablePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by antonhagermalm on 2017-03-31.
 */
public class ProjectileFactory {

    private static World world;

    /**
     * Maybe return the projectile-object to the gameSession
     * @param deltaX
     * @param deltaY
     * @param tankX
     * @param tankY
     */

    public static Shootable shootSmallMissile(float deltaX, float deltaY, float tankX, float tankY){
        return new SmallMissile(deltaX, deltaY, tankX, tankY, world);
    }

    public static void setWorld(World inputWorld){
        world = inputWorld;
    }
}
