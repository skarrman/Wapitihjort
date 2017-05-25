package tankRevolution.model.shootablePackage;

/**
 * Created by jakobwall on 2017-05-11.
 * Handling of the different kinds of ammunition
 */
@SuppressWarnings("Duplicates")
public enum AmmunitionType {
    SMALL_MISSILE("Small Missile"),
    MISSILE("Missile"),
    SMALL_ATOM_BOMB("Small Atom Bomb"),
    ATOM_BOMB("Atom Bomb");

    private final String name;

    AmmunitionType(String name){
        this.name = name;
    }

    public static AmmunitionType getNext(AmmunitionType ammunitionType){
        switch (ammunitionType){
            case SMALL_MISSILE:
                return MISSILE;
            case MISSILE:
                return SMALL_ATOM_BOMB;
            case SMALL_ATOM_BOMB:
                return ATOM_BOMB;
            case ATOM_BOMB:
                return SMALL_MISSILE;
            default:
                return SMALL_MISSILE;
        }
    }

    public static AmmunitionType getPrevious(AmmunitionType ammunitionType){
        switch (ammunitionType){
            case SMALL_MISSILE:
                return ATOM_BOMB;
            case MISSILE:
                return SMALL_MISSILE;
            case SMALL_ATOM_BOMB:
                return MISSILE;
            case ATOM_BOMB:
                return SMALL_ATOM_BOMB;
            default:
                return SMALL_MISSILE;
        }
    }

    public String getName(){
        return this.name;
    }
}
