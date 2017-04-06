package tank_revolution.model.ShootablePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by antonhagermalm on 2017-03-31.
 */
public class ProjectileFactory {

    /**
     * Maybe return the projectile-object to the gameSession
     */

    public static Shootable shootSmallMissile(){
        return new SmallMissile();
    }
}
