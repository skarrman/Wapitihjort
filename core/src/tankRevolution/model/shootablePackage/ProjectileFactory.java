package tankRevolution.model.shootablePackage;

/**
 * Created by antonhagermalm on 2017-03-31.
 * factory responsible for creating projectiles
 */
public class ProjectileFactory {

    /**
     * Maybe return the projectile-object to the gameSession
     */

    public static Shootable create(AmmunitionType ammunitionType) {
        switch (ammunitionType) {
            case SMALL_MISSILE:
                return createSmallMissile();
            case MISSILE:
                return createMissile();
            case SMALL_ATOM_BOMB:
                return createSmallAtomBomb();
            case ATOM_BOMB:
                return createAtomBomb();
            default:
                return createSmallMissile();
        }
    }

    private static Shootable createSmallMissile() {
        return new SmallMissile();
    }

    private static Shootable createMissile() {
        return new Missile();
    }

    private static Shootable createSmallAtomBomb() {
        return new SmallAtomBomb();
    }

    private static Shootable createAtomBomb() {
        return new AtomBomb();
    }
}
