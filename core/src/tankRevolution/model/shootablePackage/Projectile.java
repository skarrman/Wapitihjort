package tankRevolution.model.shootablePackage;

/**
 * Created by antonhagermalm on 2017-03-30.
 * A projectile shot by the tank and will damage the ground and other tanks
 */

public abstract class Projectile implements Shootable {

    /** The radius of the Projectile (should cbe renamed to projectileRadius) */
    private final float missileRadius = 0.27f;

    /** The density of the Projectile (should cbe renamed to projectileDensity) */
    private final float missileDensity = 13.71f;

    /** The radius of the destruction caused by the projectile */
    private final int blastRadius;

    /** The damage the projectile will inflict (will reduce if not a direct hit)*/
    private final int damage;

    /**
     * * The constructor for the projectile. The body of the projectile is created from the shooting vector given by the character (deltaX and deltaY) and a starting point
     * tankX and tankY. All the logic is in the projectileSetup method.
     * @param damage the damage of the projectile
     * @param blastRadius the blast radius
     */
    Projectile(int damage, int blastRadius) {
        this.blastRadius = blastRadius;
        this.damage = damage;

    }

    /**
     * @return blastRadius of the projectile
     */
    @Override
    public int getBlastRadius() {
        return blastRadius;
    }

    public float getMissileRadius() {
        return missileRadius;
    }

    /**
     *
     * @return The damage the projectile will inflict (will reduce if not a direct hit)
     */
    @Override
    public int getDamage() {
        return damage;
    }


    public float getMissileDensity() {
        return missileDensity;
    }


}
