package tank_revolution.model;

import tank_revolution.Utils.Id;

/**
 * Created by antonhagermalm on 2017-04-13.
 */
public class CharacterFactory {
    public static Player newPlayer(Id id){
        return new Player(id);
    }
    public static NPC newNPC(Id id, int difficulty){
        return new NPC(id, difficulty);
    }

    public static Character defenciveCopyCharacter(Character characterToCopy){
        if (!characterToCopy.isNPC()){
            return new Player((Player)characterToCopy);
        }
        else {
            return new NPC((NPC)characterToCopy);
        }
    }
}
