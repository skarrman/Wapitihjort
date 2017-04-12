package tank_revolution.model;

/**
 * Created by antonhagermalm on 2017-04-13.
 */
public class CharacterFactory {
    public static Player newPlayer(String name){
        return new Player(name);
    }
    public static NPC newNPC(String name, int difficulty){
        return new NPC(name, difficulty);
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
