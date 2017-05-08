package tankRevolution.model.shootablePackage;

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
