package tankRevolution.model;

import tankRevolution.model.shootablePackage.Shootable;

import java.util.ArrayList;
import java.util.List;

/**
 * Logical model, free from the framework "LibGDX".
 */
public class TankRevolution {

    /**
     * list of the playing character
     */
    private final List<Character> characterList;

    /**
     * The projectile, will hold the projectile in air
     */
    private final List<Shootable> flyingProjectiles;

    /**
     * The index of the current playing character
     */
    private int characterTurn = 0;

    /**
     * true if the current character is a player, false if npc
     */
    private boolean isActive;

    /**
     * /**
     * Creates a new gameSession from a list of characters and gives the characters tanks and adds the tanks to the world.
     * (max four characters)
     * Note that this is the first iteration and is only made for two characters
     *
     * @param characterList the characterList
     */
    public TankRevolution(List<Character> characterList) {
        this.characterList = characterList;
        gameSessionSetup();
        initializeTanks();
        flyingProjectiles = new ArrayList<Shootable>();
    }

    private void gameSessionSetup() {
        setIsActive();
    }

    private void initializeTanks() {
        for (Character c : characterList) {
            Tank tank = new Tank();
            c.setTank(tank);
        }
    }

    public boolean isInputAllowed() {
        return (isActive && !getCurrentCharacter().isNPC());
    }

    public List<Shootable> shoot(float deltaX, float deltaY) {
        flyingProjectiles.add(getCurrentTank().shoot(deltaX, deltaY));
        isActive = false;
        return flyingProjectiles;
    }

    public Character getCurrentCharacter() {
        return characterList.get(characterTurn);
    }

    public Tank getCurrentTank() {
        return getCurrentCharacter().getTank();
    }

    public List<Shootable> getFlyingProjectiles() {
        return flyingProjectiles;
    }

    public void doNextMove() {
        setNextCharacter();
        getCurrentCharacter().setNewTurn();
        setIsActive();
    }

    public boolean NPCWillShoot() {
        return (getCurrentCharacter() instanceof NPC && flyingProjectiles.size() == 0
                && characterList.size() > 1);
    }

    public void reduceFuel() {
        Tank tank = characterList.get(characterTurn).getTank();
        tank.reduceFuel();
    }

    /**
     * @return true if nothing prevents the tank to move
     */
    public boolean tankCanMove() {
        Tank tank = characterList.get(characterTurn).getTank();
        return tank.hasFuel() && !isProjectileFlying();
    }

    /**
     * ends the turn of the current playing character and gives the turn too the next player
     */
    private void setNextCharacter() {
        characterTurn = (characterTurn + 1) % characterList.size();
    }

    /**
     * @return true if the projectile is currently in the air
     */
    public boolean isProjectileFlying() {
        return flyingProjectiles.size() > 0;
    }

    /**
     * @return the list of the characters
     */
    public List<Character> getCharacterList() {
        return characterList;
    }

    /**
     * safely removes a projectile
     */
    public void destroyProjectile(Shootable shootable) {
        flyingProjectiles.remove(shootable);
    }


    /**
     * sets that the current character is a player
     */
    private void setIsActive() {
        isActive = !getCurrentCharacter().isNPC();
    }

    /**
     * calculates the damage that will be inflicted on a tank
     *
     * @param damage      the starting damage of the projectile
     * @param distance    the distance from the tanks center to the hitdown
     * @param blastRadius the projectiles blast radius
     * @return the damage inflicted to the tank
     */
    private int calculateDamage(int damage, float distance, float blastRadius) {
        float d = blastRadius - distance;
        if (d < 0) {
            d = 0;
        }
        return (int) (d / blastRadius * damage);
    }

    public void damage(Shootable projectile, Tank tank, float distance) {
        tank.reduceHealth(calculateDamage(projectile.getDamage(), distance, projectile.getBlastRadius()));
    }

    public void destroyTank(Tank tank) {
        for (int i = 0; i < characterList.size(); i++) {
            if (characterList.get(i).getTank() == tank) {
                characterList.remove(i);
                if (characterList.size() > 0)
                    setNextCharacter();
            }
        }
    }
}
