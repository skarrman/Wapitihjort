package tank_revolution.model;

/**
 * Created by antonhagermalm on 2017-03-30.
 */

/**
 * The abstract class for projectiles, includes the fields blastRadius..
 */
public abstract class Projectile implements Shootable{

    protected float blastRadius;

    /**
     * @return blastRadius of the projectile
     */
    protected float getBlastRadius(){
        return blastRadius;
    }
}
