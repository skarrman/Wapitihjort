package tankRevolution.model.shootablePackage;

/**
 * Created by antonhagermalm on 2017-03-31.
 */
public class ProjectileFactory {

    /**
     * Maybe return the projectile-object to the gameSession
     */

    public static Shootable create(AmmunitionType ammunitionType){
        switch (ammunitionType){
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

    public static Shootable createSmallMissile(){
        return new SmallMissile();
    }

    public static Shootable createMissile(){
        return new Missile();
    }

    public static Shootable createSmallAtomBomb(){
        return new SmallAtomBomb();
    }

    public static Shootable createAtomBomb(){
        return new AtomBomb();
    }
}
