package tankRevolution.model;

import tankRevolution.utils.Id;

/**
 * Factory class to create characters.
 */
public class CharacterFactory {
    /**
     * Creates a new player.
     * @param id The id that the new player will have.
     * @return The created character.
     */
    public static Character newPlayer(Id id){
        return new Player(id);
    }

    /**
     * Creates a new NPC.
     * @param id The ID that the new NPC will have.
     * @param difficulty The difficulty of the NPC.
     * @return The created character.
     */
    public static Character newNPC(Id id, NPCDifficulty difficulty){
        return new NPC(id, difficulty);
    }

    /**
     * Makes a defensive copy of the character.
     * @param characterToCopy The character that will be copied.
     * @return The copied character.
     */
    static Character defenciveCopyCharacter(Character characterToCopy){
        if (!characterToCopy.isNPC()){
            return new Player((Player)characterToCopy);
        }
        else {
            return new NPC((NPC)characterToCopy);
        }
    }
}
