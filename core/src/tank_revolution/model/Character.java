package tank_revolution.model;


import tank_revolution.Utils.Id;

/**
 * Created by antonhagermalm on 2017-03-30.
 * <p>A character is a holder of a tank. </p>
 * <p>Characters is kept between games while tanks aren't </p>
 * <p>A character is controlled of either a human or a ai </p>
 */

public abstract class Character {
    private Tank tank;
    private final Id id;
    private final boolean isNPC;

    /**
     * Standard constructor for characters
     * @param id The name of the character
     * @param isNPC If the character is a npc or a player
     */
    Character(Id id, boolean isNPC){
        this.id = id;
        this.isNPC = isNPC;
    }

    /**
     * used to copy a character, used in the defencing copying
     * @param character the character to copy
     */
    Character(Character character){
        this.id = character.getId();
        this.isNPC = character.isNPC();
    }

    /**
     * Sets the tank
     * @param tank
     */
    public void setTank(Tank tank) {
        this.tank = tank;
    }

    /**
     * gets the tank
     * @return
     */
    public Tank getTank(){
        return tank;
    }

    /**
     * gets the name
     * @return
     */
    public Id getId() {
        return id;
    }

    /**
     * @return whether or not the current character is a npc or not
     */
    public boolean isNPC(){
        return isNPC;
    }

    public abstract void setNewTurn();

    //protected abstract Character getDefenciveCopiedClone();

}
