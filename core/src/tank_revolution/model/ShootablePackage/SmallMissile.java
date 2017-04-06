package tank_revolution.model.ShootablePackage;

import com.badlogic.gdx.physics.box2d.World;
import tank_revolution.model.ShootablePackage.Projectile;

/**
 * Created by antonhagermalm on 2017-03-30.
 * <p> SmallMissile is the standard projectile, small blastradius and minor damage </p>
 * {@inheritDoc}
 *
 */
public class SmallMissile extends Projectile {

    protected SmallMissile() {
        super(10, 10);
    }

}
