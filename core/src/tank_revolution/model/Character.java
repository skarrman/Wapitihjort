package tank_revolution.model;


/**
 * Created by antonhagermalm on 2017-03-30.
 * <p>A character is a holder of a tank. </p>
 * <p>Characters is kept between games while tanks aren't </p>
 * <p>A character is controlled of either a human or a ai </p>
 */

public abstract class Character {
    private Tank tank;
    private final String name;
    private final boolean isNPC;

    /**
     * Standard constructor for characters
     * @param name The name of the character
     * @param isNPC If the character is a npc or a player
     */
    Character(String name, boolean isNPC){
        this.name = name;
        this.isNPC = isNPC;
    }

    /**
     * used to copy a character, used in the defencing copying
     * @param character the character to copy
     */
    Character(Character character){
        this.name = character.getName();
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
    public String getName() {
        return name;
    }

    /**
     * @return whether or not the current character is a npc or not
     */
    public boolean isNPC(){
        return isNPC;
    }

    public abstract void setNewTurn();

    static public Character copy(Character characterToCopy){
        if (characterToCopy.isNPC()){
            return new Player(characterToCopy);
        }
        else {
            return new NPC(characterToCopy);
        }
    }

}
