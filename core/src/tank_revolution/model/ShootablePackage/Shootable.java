package tank_revolution.model.ShootablePackage;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public interface Shootable {

    int getBlastRadius();

    int getDamage();

    float getMissileRadius();

    float getMissileDensity();

}
