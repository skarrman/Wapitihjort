package tankRevolution.model;

import tankRevolution.utils.Id;

/**
 * Created by antonhagermalm on 2017-04-13.
 */
public class CharacterFactory {
    public static Player newPlayer(Id id){
        return new Player(id);
    }

    public static NPC newNPC(Id id, NPCDifficulty difficulty){
        return new NPC(id, difficulty);
    }

    static Character defenciveCopyCharacter(Character characterToCopy){
        if (!characterToCopy.isNPC()){
            return new Player((Player)characterToCopy);
        }
        else {
            return new NPC((NPC)characterToCopy);
        }
    }
}
